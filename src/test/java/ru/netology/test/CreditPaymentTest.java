package ru.netology.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.page.CreditPaymentPage;
import ru.netology.page.MainPage;

public class CreditPaymentTest {
    MainPage mainPage;
    CreditPaymentPage creditPaymentPage;

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown(){
        DBHelper.cleanDatabase();
        SelenideLogger.removeAllListeners();
    }

    @BeforeEach
    public void setUp(){
        mainPage = Selenide.open("http://localhost:8080", MainPage.class);
    }

    @Test
    @DisplayName("Должна открыться форма для заполнения покупки тура в кредит")
    public void shouldOpenCreditPaymentPage(){
        mainPage.creditPayment();
    }

    @Test
    @DisplayName("Заявка на покупку тура успешно оплачена картой со статусом APPROVED")
    public void shouldSuccessCreditPayment(){
        creditPaymentPage = mainPage.creditPayment();
        creditPaymentPage.creditPaymentByApprovedCard(3, 1);
        Assertions.assertEquals("APPROVED", DBHelper.getCreditOrderStatus());
    }

    @Test
    @DisplayName("Заявка на покупку тура должна быть отклонена банком при оплате картой со статусом DECLINED")
    public void shouldDeclineCreditPayment(){
        creditPaymentPage = mainPage.creditPayment();
        creditPaymentPage.creditPaymentByDeclinedCard(1, 2);
        Assertions.assertEquals("DECLINED", DBHelper.getCreditOrderStatus());
    }

    @Test
    @DisplayName("При отправке пустой заявки под всеми полями формы должно " +
            "появиться сообщение 'Поле обязательно для заполнения'")
    public void shouldApperMsgsWhenSendEmptyForm(){
        creditPaymentPage = mainPage.creditPayment();
        creditPaymentPage.emptyCreditPaymentForm();
    }

    @Test
    @DisplayName("При отправке заявки, где поле владелец заполнено только именем " +
            "должно появляться сообщение под полем владелец 'Неверный форма'")
    public void shouldApperMsgWhenSetOnlyFirstNameOwner(){
        creditPaymentPage = mainPage.creditPayment();
        creditPaymentPage.creditPaymentWithOnlyFistNameOwner(1, 1);
    }
}
