package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCards1from2() {
      open("http://localhost:9999");
      var loginPage = new LoginPageV1();
      var transferInfo = new DataHelper.TransferInfo(DataHelper.card2Info(), 1000);
//    var loginPage = open("http://localhost:9999", LoginPageV1.class);
      var authInfo = DataHelper.getAuthInfo();
      var verificationPage = loginPage.validLogin(authInfo);
      var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
      var dashboardPage = verificationPage.validVerify(verificationCode);

      var balanceCard1 = dashboardPage.getCardBalance(DataHelper.card1Info());
      var balanceCard2 = dashboardPage.getCardBalance(DataHelper.card2Info());

      var transferPage = dashboardPage.selectCardTo(DataHelper.card1Info());
      var transfer = transferPage.transferMoney(transferInfo);
      var balance1 = transfer.getCardBalance(DataHelper.card1Info());
      var balance2 = transfer.getCardBalance(DataHelper.card2Info());

      assertEquals(balanceCard1 + 1000,balance1);
      assertEquals(balanceCard2 - 1000,balance2);

    }

  @Test
  void shouldTransferMoneyBetweenOwnCards2from1() {
    open("http://localhost:9999");
    var loginPage = new LoginPageV1();
    var transferInfo = new DataHelper.TransferInfo(DataHelper.card1Info(), 1000);

    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    var dashboardPage = verificationPage.validVerify(verificationCode);

    var balanceCard1 = dashboardPage.getCardBalance(DataHelper.card1Info());
    var balanceCard2 = dashboardPage.getCardBalance(DataHelper.card2Info());

    var transferPage = dashboardPage.selectCardTo(DataHelper.card2Info());
    var transfer = transferPage.transferMoney(transferInfo);
    var balance1 = transfer.getCardBalance(DataHelper.card1Info());
    var balance2 = transfer.getCardBalance(DataHelper.card2Info());

    assertEquals(balanceCard1 - 1000,balance1);
    assertEquals(balanceCard2 + 1000,balance2);
    }

  @Test
  void shouldNotTransferMoneyIfBalanceWillBeNegative() {
    open("http://localhost:9999");
    var loginPage = new LoginPageV1();


    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    var dashboardPage = verificationPage.validVerify(verificationCode);

    var balanceCard1 = dashboardPage.getCardBalance(DataHelper.card1Info());
    var balanceCard2 = dashboardPage.getCardBalance(DataHelper.card2Info());
    var transferInfo = new DataHelper.TransferInfo(DataHelper.card1Info(), balanceCard1+1000);

    var transferPage = dashboardPage.selectCardTo(DataHelper.card2Info());
    var transfer = transferPage.errorWhenTransferMoney(transferInfo);
    DashboardPage dashboardPageAfterCancel = transferPage.clickCancel();

    assertEquals(balanceCard1,dashboardPageAfterCancel.getCardBalance(DataHelper.card1Info()));
    assertEquals(balanceCard2,dashboardPageAfterCancel.getCardBalance(DataHelper.card2Info()));
    }


}

