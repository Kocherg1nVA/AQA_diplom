package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    private static final SelenideElement debitTitle = $$("h3").find(Condition.text("Оплата по карте"));
    private static final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private static final SelenideElement monthField = $("input[placeholder='08']");
    private static final SelenideElement yearField = $("input[placeholder='22']");
    private static final SelenideElement ownerField = $$(".input").find(exactText("Владелец")).$(".input__control");
    private static final SelenideElement cvcField = $("input[placeholder='999']");
    private static final SelenideElement continueButton = $$("button.button").findBy(text("Продолжить"));
    private static final ElementsCollection invalidFormatFields = $$(".input__sub");
    private static final SelenideElement successNotification = $$(".notification__content").findBy(text("Операция одобрена Банком."));
    private static final SelenideElement unSuccessNotification = $$(".notification__content").findBy(text("Ошибка! Банк отказал в проведении операции."));

    public PaymentPage() {
        debitTitle.shouldBe(visible);
    }

    public void paymentByApprovedCard(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void paymentByDeclinedCard(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getDeclinedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        unSuccessNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void emptyPaymentForm() {
        continueButton.click();
        invalidFormatFields.shouldBe(size(5));
        invalidFormatFields.get(0).shouldBe(visible);
        invalidFormatFields.get(1).shouldBe(visible);
        invalidFormatFields.get(2).shouldBe(visible);
        invalidFormatFields.get(3).shouldBe(visible);
        invalidFormatFields.get(4).shouldBe(visible);
        invalidFormatFields.shouldBe(allMatch("все поля содержат текст: Поле обязательно для заполнения",
                e -> e.getText().contains("Поле обязательно для заполнения")));
    }

    public void emptyCardNumber(int plusMonth, int plusYear) {
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(visible);
    }

    public void invalidCardNumber(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getInvalidCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    public void emptyMonthField(int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void monthFieldMoreThanTwelve(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getInvalidMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void monthFieldLessThanOne(int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getInvalidMonth(0));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void emptyYearField(int plusMonth) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void yearFieldLessThanCurrentYear(int plusMonth, int minusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getPastYear(minusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Истёк срок действия карты")).shouldBe(visible);
    }

    public void yearFieldFiveMoreThanCurrent(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void emptyOwnerField(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void ownerNameByHyphen(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerNameByHyphen());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void ruOwnerName(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getRuOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    public void emptyCVCField(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(visible);
    }

    public void invalidCVC(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getInvalidCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(CollectionCondition.size(1))
                .get(0).shouldHave(text("Неверный формат")).shouldBe(visible);
    }
}
