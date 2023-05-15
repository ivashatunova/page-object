package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.LoginPageV2;
import ru.netology.web.page.LoginPageV3;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
      open("http://localhost:9999");
      var loginPage = new LoginPageV1();
      var transferInfo = new DataHelper.TransferInfo(DataHelper.card2Info(), 1000);
//    var loginPage = open("http://localhost:9999", LoginPageV1.class);
      var authInfo = DataHelper.getAuthInfo();
      var verificationPage = loginPage.validLogin(authInfo);
      var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
      var dashboardPage = verificationPage.validVerify(verificationCode);
      var transferPage = dashboardPage.selectCardTo(DataHelper.card1Info());
      var transfer = transferPage.transferMoney(transferInfo);
      var balance1 = transfer.getCardBalance(DataHelper.card1Info());
      var balance2 = transfer.getCardBalance(DataHelper.card2Info());
      assertEquals(5000,balance1);
      assertEquals(5000,balance2);

    }

  @Test
  void shouldTransferMoneyBetweenOwnCardsV2() {
    open("http://localhost:9999");
    var loginPage = new LoginPageV2();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
  }

  @Test
  void shouldTransferMoneyBetweenOwnCardsV3() {
    var loginPage = open("http://localhost:9999", LoginPageV3.class);
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
  }

}

