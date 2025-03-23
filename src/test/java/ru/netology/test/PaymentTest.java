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
    @DisplayName("Открывается форма для заполнения покупки тура")
    public void successOpenDebitPaymentPage() {
        mainPage.payment();
    }

    @Test
    @DisplayName("Успешная оплата тура с карты со статусом APPROVED")
    public void approvedPaymentByDebitCart() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.paymentByApprovedCard(2, 0);
        Assertions.assertEquals("APPROVED", DBHelper.getDebitOrderStatus());
    }

    @Test
    @DisplayName("Заявка на покупку тура отклонена банком при оплате картой со статусом DECLINED")
    public void declinedPaymentByDebitCart() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.paymentByDeclinedCard(2, 1);
        Assertions.assertEquals("DECLINED", DBHelper.getCreditOrderStatus());
    }

    @Test
    @DisplayName("Появляется сообщение под всеми полями формы 'Поле обязательно для заполнения'")
    public void emptyFieldsShouldContainNotificationMsg() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyForm();
    }

    @Test
    @DisplayName("Появляется сообщение под полем номер карты 'Поле обязательно для заполнения'")
    public void emptyCardFieldShouldContainNotificationMsg() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyCardNumber(2, 0);
    }

    @Test
    @DisplayName("Появляется сообщение под полем номер карты 'Неверный формат'")
    public void invalidCardNumber() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.invalidCardNumber(2, 1);
    }

    @Test
    @DisplayName("Появляется сообщение под полем месяц 'Поле обязательно для заполнения'")
    public void emptyMonthField() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyMonthField(1);
    }

    @Test
    @DisplayName("Появляется сообщение под полем месяц 'Неверный формат'")
    public void monthFieldMoreThanTwelve() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.monthFieldMoreThanTwelve(1, 2);
    }

    @Test
    @DisplayName("Появляется сообщение под полем месяц 'Неверный формат'")
    public void monthFieldLessThanOne() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.monthFieldLessThanOne(2);
    }

    @Test
    @DisplayName("Появляется сообщение под полем год 'Поле обязательно для заполнения'")
    public void emptyYearField() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyYearField(2);
    }

    @Test
    @DisplayName("Появляется сообщение под полем год 'Неверный формат'")
    public void pastYearField() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.yearFieldLessThanCurrentYear(2, 3);
    }

    @Test
    @DisplayName("Появляется сообщение под полем год 'Неверный формат'")
    public void futureYearField() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.yearFieldFiveMoreThanCurrent(0, 6);
    }

    @Test
    @DisplayName("Появляется сообщение под полем владелец 'Поле обязательно для заполнения'")
    public void emptyOwnerField() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyOwnerField(2, 2);
    }

    @Test
    @DisplayName("Успешная оплата тура при заполнении фамилии владельца карты рез дефис")
    public void ownerNameByHyphen() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.ownerNameByHyphen(2, 3);
    }

    @Test
    @DisplayName("Появляется сообщение под полем владелец 'Неверный формат'")
    public void ruOwnerName() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.ruOwnerName(2, 4);
    }

    @Test
    @DisplayName("Появляется сообщение под полем CVC/CVV 'Поле обязательно для заполнения'")
    public void emptyCVCField() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.emptyCVCField(1, 1);
    }

    @Test
    @DisplayName("Появляется сообщение под полем CVC/CVV 'Неверный формат'")
    public void invalidCVC() {
        debitPaymentPage = mainPage.payment();
        debitPaymentPage.invalidCVC(1, 2);
    }
}
