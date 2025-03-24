package ru.netology.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

public class PaymentTest {
    MainPage mainPage;
    PaymentPage debitPaymentPage;

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown() {
        DBHelper.cleanDatabase();
        SelenideLogger.removeAllListeners();
    }

    @BeforeEach
    public void setUp() {
        mainPage = Selenide.open("http://localhost:8080", MainPage.class);
    }

    @Test
    @DisplayName("Должна открыться форма для заполнения покупки тура")
    public void shouldOpenPaymentPage() {
        mainPage.payment();
    }

    @Test
    @DisplayName("Заявка на покупку тура успешно оплачена картой со статусом APPROVED")
    public void shouldSuccessPay() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.paymentByApprovedCard(2, 0);
        Assertions.assertEquals("APPROVED", DBHelper.getDebitOrderStatus());
    }

    @Test
    @DisplayName("Заявка на покупку тура должна быть отклонена банком при оплате картой со статусом DECLINED")
    public void shouldDeclinePayment() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.paymentByDeclinedCard(2, 1);
        Assertions.assertEquals("DECLINED", DBHelper.getDebitOrderStatus());
    }

    @Test
    @DisplayName("При отправке пустой заявки должно появляться сообщение " +
            "под всеми полями формы 'Поле обязательно для заполнения'")
    public void shouldApperMsgsWhenSendEmptyForm() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyPaymentForm();
    }

    @Test
    @DisplayName("При отправке заявки с пустым полем номер карты должно появляться " +
            "сообщение под полем номер карты 'Поле обязательно для заполнения'")
    public void shouldApperMsgWhenCardFieldEmpty() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyCardNumber(2, 0);
    }

    @Test
    @DisplayName("При отправке заявки с полем номер карты заполненным в формате 14 цифр должно " +
            "появляться сообщение под полем номер карты 'Неверный формат'")
    public void shouldApperMsgWhenCardFieldIncorrect() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.invalidCardNumber(2, 1);
    }

    @Test
    @DisplayName("При отправке заявки с пустым полем месяц должно появляться сообщение" +
            " под полем месяц 'Поле обязательно для заполнения'")
    public void shouldApperMsgWhenMonthFieldEmpty() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyMonthField(1);
    }

    @Test
    @DisplayName("При отправке заявки, где поле месяц заполнено значением 13 должно " +
            "появляться сообщение под полем месяц 'Неверный формат'")
    public void shouldApperMSGWhenMonthFieldMoreThanTwelve() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.monthFieldMoreThanTwelve(1, 2);
    }

    @Test
    @DisplayName("При отправке заявки, где поле месяц заполнено значением 00 должно" +
            "появляться сообщение под полем месяц 'Неверный формат'")
    public void shouldApperMsgWhenMonthFieldLessThanOne() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.monthFieldLessThanOne(2);
    }

    @Test
    @DisplayName("При отправке заявки с пустым полем год должно " +
            "появляться сообщение под полем год 'Поле обязательно для заполнения'")
    public void shouldApperMsgWhenYearFieldEmpty() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyYearField(2);
    }

    @Test
    @DisplayName("При отправке заявки, где поле год заполнено значением прошлых лет должно " +
            "появляться сообщение под полем год 'Неверный формат'")
    public void shouldApperMsgWhenYearFieldPast() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.yearFieldLessThanCurrentYear(2, 3);
    }

    @Test
    @DisplayName("При отправке заявки, где поле год заполнено значением более 5 лет от текущего" +
            "должно появляться сообщение под полем год 'Неверный формат'")
    public void shouldApperMsgWhenYearFieldFuture() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.yearFieldFiveMoreThanCurrent(0, 6);
    }

    @Test
    @DisplayName("При отправке заявки с пустым полем владелец должно " +
            "появляться сообщение под полем владелец 'Поле обязательно для заполнения'")
    public void shouldApperMsgWhenOwnerFieldEmpty() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyOwnerField(2, 2);
    }

    @Test
    @DisplayName("Успешная оплата тура при заполнении фамилии владельца карты через дефис")
    public void shouldSuccessPayWhenOwnerNameByHyphen() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.ownerNameByHyphen(2, 3);
    }

    @Test
    @DisplayName("При отправке заявки, где поле владелец заполнено на кириллице должно" +
            "появляться сообщение под полем владелец 'Неверный формат'")
    public void shouldApperMsgWhenOwnerNameRu() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.ruOwnerName(2, 4);
    }

    @Test
    @DisplayName("При отправке заявки с пустым полем CVC/CVV должно появляться " +
            "сообщение под полем CVC/CVV 'Поле обязательно для заполнения'")
    public void shouldApperMsgWhenCVCFieldEmpty() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyCVCField(1, 1);
    }

    @Test
    @DisplayName("При отправке заявки, где поле CVC/CVV заполнено в формате 2 цифры должно " +
            "появляться сообщение под полем CVC/CVV 'Неверный формат'")
    public void shouldApperMsgWhenCVCFieldIncorrect() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.invalidCVC(1, 2);
    }
}
