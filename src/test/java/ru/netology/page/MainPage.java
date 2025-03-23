package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private static final SelenideElement paymentDebitButton = $$("button.button")
            .findBy(Condition.text("Купить"));
    private static final SelenideElement paymentCreditButton = $$("button.button")
            .findBy(Condition.text("Купить в кредит"));

    public PaymentPage payment() {
        paymentDebitButton.click();
        return new PaymentPage();
    }

    public CreditPaymentPage creditPayment() {
        paymentCreditButton.click();
        return new CreditPaymentPage();
    }
}
