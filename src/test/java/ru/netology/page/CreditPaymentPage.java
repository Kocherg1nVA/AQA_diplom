package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.CollectionCondition.size;

public class CreditPaymentPage {

    private static final SelenideElement creditTitle = $$("h3").find(Condition.text("Кредит по данным карты"));
    private static final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private static final SelenideElement monthField = $("input[placeholder='08']");
    private static final SelenideElement yearField = $("input[placeholder='22']");
    private static final SelenideElement ownerField = $$(".input").find(exactText("Владелец")).$(".input__control");
    private static final SelenideElement cvcField = $("input[placeholder='999']");
    private static final SelenideElement continueButton = $$("button.button").findBy(text("Продолжить"));
    private static final ElementsCollection invalidFormatFields = $$(".input__sub");
    private static final SelenideElement successNotification = $$(".notification__content").findBy(text("Операция одобрена Банком."));
    private static final SelenideElement unSuccessNotification = $$(".notification__content").findBy(text("Ошибка! Банк отказал в проведении операции."));


    public CreditPaymentPage() {
        creditTitle.shouldBe(visible);
    }

    public void creditPaymentByApprovedCard(int plusMonth, int plusYear){
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void creditPaymentByDeclinedCard(int plusMonth, int plusYear){
        cardNumberField.setValue(DataHelper.getDeclinedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        unSuccessNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void emptyCreditPaymentForm(){
        continueButton.click();
        invalidFormatFields.shouldBe(size(5)).shouldBe(allMatch("все поля содержат текст: " +
                "Поле обязательно для заполнения", e -> e.getText().contains("Поле обязательно для заполнения")));

    }

    public void creditPaymentWithOnlyFistNameOwner(int plusMonth, int plusYear){
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerFirstName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Неверный формат"));
    }

}
