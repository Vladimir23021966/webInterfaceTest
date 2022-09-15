package ru.netology.web;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class DebitCardApplicationTest {

    @BeforeEach
    public void setUp() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    void shouldВеSendingApplicationAfterEnteringValidValues() {

        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Колянников Владимир");
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79031234567");
        $("[data-test-id=\"agreement\"]").click();
        $("button").should(visible, Duration.ofSeconds(5)).click();
        $("[data-test-id=\"order-success\"]").shouldHave(exactText("Ваша заявка успешно отправлена!" +
                " Наш менеджер свяжется с вами в ближайшее время."));

    }

    @Test
    void shouldВеNonSubmissionApplicationAfterEnteringInvalidName() {

        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Kolyannikov Vladimir");
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79031234567");
        $("[data-test-id=\"agreement\"]").click();
        $("button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и " +
                "Фамилия указаные неверно." +
                " Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldВеNonSubmissionApplicationAfterEnteringInvalidTel() {

        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Колянников Владимир");
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+790312345222222222222267");
        $("[data-test-id=\"agreement\"]").click();
        $("button").click();
        $("[data-test-id=\"phone\"].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно." +
                " Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldВеNonSubmissionApplicationAfterEnteringEmptyFieldTel() {

        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Колянников Владимир");
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("");
        $("[data-test-id=\"agreement\"]").click();
        $("button").click();
        $("[data-test-id=\"phone\"].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для " +
                "заполнения"));

    }

    @Test
    void shouldВеNonSubmissionApplicationAfterEnteringEmptyFieldName() {

        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("");
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79031234567");
        $("[data-test-id=\"agreement\"]").click();
        $("button").click();
        $("[data-test-id=\"name\"].input_invalid .input__sub").shouldHave(exactText("Поле" +
                " обязательно для заполнения"));
    }

    @Test
    void shouldВеNonSubmissionApplicationAfterEnteringEmptyСheckbox() {

        $("[data-test-id=\"name\"] [name=\"name\"]").setValue("Колянников Владимир");
        $("[data-test-id=\"phone\"] [name=\"phone\"]").setValue("+79031234567");
        $("button").click();
        $("[data-test-id=agreement].input_invalid").shouldHave(visible, exactText("Я соглашаюсь" +
                " с условиями обработки и использования моих персональных данных и разрешаю сделать " +
                "запрос в бюро кредитных историй"));
    }
}
