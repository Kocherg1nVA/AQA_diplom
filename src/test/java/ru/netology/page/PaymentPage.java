package ru.netology.page;

import io.qameta.allure.Step;
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

    @Step("Оплата тура с карты со статусом APPROVED")
    public void paymentByApprovedCard(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    @Step("Оплата тура с карты со статусом DECLINED")
    public void paymentByDeclinedCard(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getDeclinedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        unSuccessNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    @Step("Отправить пустую заявку на приобретение тура")
    public void emptyForm() {
        continueButton.click();
        invalidFormatFields.shouldBe(size(5));
        invalidFormatFields.get(0).shouldBe(visible);
        invalidFormatFields.get(1).shouldBe(visible);
        invalidFormatFields.get(2).shouldBe(visible);
        invalidFormatFields.get(3).shouldBe(visible);
        invalidFormatFields.get(4).shouldBe(visible);
        invalidFormatFields.shouldBe(allMatch("все элементы содержат текст: Поле обязательно для заполнения",
                e -> e.getText().contains("Поле обязательно для заполнения")));
    }

    @Step("Поле номер карты оставить не заполненным")
    public void emptyCardNumber(int plusMonth, int plusYear) {
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(visible);
    }

    @Step("Поле номер карты заполнить в формате 14 цифр")
    public void invalidCardNumber(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getInvalidCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Step("Поле месяц оставить не заполненным")
    public void emptyMonthField(int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Step("В поле месяц уставить значение '13'")
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

    @Step("В поле месяц уставить значение '00'")
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

    @Step("Поле год оставить не заполненным")
    public void emptyYearField(int plusMonth) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        ownerField.setValue(DataHelper.getOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Step("Поле год заполнить значением прошлых лет")
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

    @Step("В поле год установить значение более 5 лет от текущего")
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

    @Step("Поле владелец оставить не заполненным")
    public void emptyOwnerField(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0)
                .shouldHave(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Step("Поле владелец заполнить фаилией через дефис")
    public void ownerNameByHyphen(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerNameByHyphen());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    @Step("Поле владелец заполнить символами на кириллице")
    public void ruOwnerName(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getRuOwnerName());
        cvcField.setValue(DataHelper.getCVC());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Неверный формат")).shouldBe(visible);
    }

    @Step("Поле CVC/CVV оставить не заполненным")
    public void emptyCVCField(int plusMonth, int plusYear) {
        cardNumberField.setValue(DataHelper.getApprovedCardNumber());
        monthField.setValue(DataHelper.getMonth(plusMonth));
        yearField.setValue(DataHelper.getYear(plusYear));
        ownerField.setValue(DataHelper.getOwnerName());
        continueButton.click();
        invalidFormatFields.shouldBe(size(1)).get(0).shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(visible);
    }

    @Step("Поле CVC/CVV заполнить в формате - 2 цифры")
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
