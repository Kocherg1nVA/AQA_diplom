package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPaymentPage {

    private static final SelenideElement creditTitle = $$("h3").find(Condition.text("Кредит по данным карты"));
    private static final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private static final SelenideElement monthField = $("input[placeholder='08']");
    private static final SelenideElement yearField = $("input[placeholder='22']");
    private static final SelenideElement ownerField = $$(".input").find(exactText("Владелец")).$(".input__control");
    private static final SelenideElement continueButton = $$("button").find(text("Продолжить"));


    public CreditPaymentPage() {
        creditTitle.shouldBe(visible);
    }

}
