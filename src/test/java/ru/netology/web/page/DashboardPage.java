package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.commands.Click;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    private ElementsCollection cards = $$(".list__item div");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.Card card) {
        String text = $("[data-test-id='" + card.getId() + "']").text();
        // TODO: перебрать все карты и найти по атрибуту data-test-id
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage selectCardTo(DataHelper.Card to) {
        $("[data-test-id='" + to.getId() + "'] [data-test-id='action-deposit']").click();
        return new TransferPage();
    }

}
