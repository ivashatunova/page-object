package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement cardFrom = $("[data-test-id=from] input");
    private SelenideElement cardTo = $("[data-test-id=to] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorPopup = $("[data-test-id=error-notification]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public TransferPage() {
        amountField.shouldBe(visible);
        cardFrom.shouldBe(visible);
        cardTo.shouldBe(visible);
    }

    public DashboardPage transferMoney(DataHelper.TransferInfo transferInfo) {
        String stringAmount = String.valueOf(transferInfo.getAmount());
        amountField.clear();
        amountField.setValue(stringAmount);
        cardFrom.clear();
        cardFrom.setValue(transferInfo.getFrom().getNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public TransferPage errorWhenTransferMoney(DataHelper.TransferInfo transferInfo) {
        String stringAmount = String.valueOf(transferInfo.getAmount());
        amountField.clear();
        amountField.setValue(stringAmount);
        cardFrom.clear();
        cardFrom.setValue(transferInfo.getFrom().getNumber());
        transferButton.click();
        errorPopup.shouldBe(visible);
        return this;
    }

    public DashboardPage clickCancel() {
        cancelButton.click();
        return new DashboardPage();
    }

}
