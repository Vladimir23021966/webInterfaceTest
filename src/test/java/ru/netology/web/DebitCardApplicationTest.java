package ru.netology.web;


import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DebitCardApplicationTest {


    @Test
    void ShouldRightTest() {

        Selenide.open("http://localhost:9999");
        $("[name=\"name\"]").setValue("Колянников Владимир");
        $("[name=\"phone\"]").setValue("+79031234567");
        $(".checkbox__box").click();
        $("button").click();
        $(".paragraph").shouldHave(exactText("Ваша заявка успешно отправлена!" +
                " Наш менеджер свяжется с вами в ближайшее время.")).should(visible, Duration.ofSeconds(5));

    }

    @Test
    void ShouldEnglishNameTest() {
        Selenide.open("http://localhost:9999");
        $("[name=\"name\"]").setValue("Kolyannikov Vladimir");
        $("[name=\"phone\"]").setValue("+79031234567");
        $(".checkbox__box").click();
        $("button").click();
        $(".input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно." +
                " Допустимы только русские буквы, пробелы и дефисы.")).should(visible, Duration.ofSeconds(5));
    }

    @Test
    void ShouldIncorrectTelTest() {

        Selenide.open("http://localhost:9999");
        $("[name=\"name\"]").setValue("Колянников Владимир");
        $("[name=\"phone\"]").setValue("+790312345222222222222267");
        $(".checkbox__box").click();
        $("button").click();
      
        $("#root > div > form > div:nth-child(2) > span > span > span.input__sub").shouldHave(exactText("Телефон указан неверно." +
                " Должно быть 11 цифр, например, +79012345678." )).should(visible, Duration.ofSeconds(5));
    }
}
