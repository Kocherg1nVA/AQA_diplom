package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    public static Faker faker = new Faker();

    public static String getApprovedCardNumber(){
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber(){
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumber(){
        return faker.numerify("##############");
    }

    public static String getMonth(int month){
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonth(int index){
        var months = new String[]{"00", "13"};
        return months[index];
    }

    public static String getYear(int year){
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getPastYear(int pastYear){
        return LocalDate.now().minusYears(pastYear).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getOwnerName(){
        return faker.name().fullName();
    }

    public static String getOwnerNameByHyphen(){
        return faker.name().firstName() + " " + faker.name().lastName() + "-" + faker.name().lastName();
    }

    public static String getRuOwnerName(){
        var fake = new Faker(new Locale("ru_RU"));
        return fake.name().fullName();
    }

    public static String getOwnerFirstName(){
        return faker.name().firstName();
    }

    public static String getCVC(){
        return faker.numerify("###");
    }

    public static String getInvalidCVC(){
        return faker.numerify("##");
    }
}

