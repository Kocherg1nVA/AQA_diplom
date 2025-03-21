package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private static final SelenideElement paymentDebitButton = $("button[class='button button_size_m button_" +
            "theme_alfa-on-white']");
    private static final SelenideElement paymentCreditButton = $("button[class='button button_view_extra " +
            "button_size_m button_theme_alfa-on-white']");

    public DebitPaymentPage debitPayment(){
        paymentDebitButton.click();
        return new DebitPaymentPage();
    }

    public CreditPaymentPage creditPayment(){
        paymentCreditButton.click();
        return new CreditPaymentPage();
    }
}
