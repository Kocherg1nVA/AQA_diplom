package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitPaymentPage {

    private static final SelenideElement debitTitle = $$("h3").find(Condition.text("Оплата по карте"));
    private static final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private static final SelenideElement monthField = $("input[placeholder='08']");
    private static final SelenideElement yearField = $("input[placeholder='22']");
    private static final SelenideElement ownerField = $$(".input").find(exactText("Владелец")).$(".input__control");
    private static final SelenideElement cvcField = $("input[placeholder='999']");



    public DebitPaymentPage() {
        debitTitle.shouldBe(visible);
    }


}
