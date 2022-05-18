package ru.netology.web;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CardOrderTest {
    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSendForm() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петрова Дарья");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79333333333");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=button]")).click(); //send
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= 'order-success']")).getText().trim();
        assertEquals(expectedText, actualText);

    }
//negative tests

    @Test
    void shouldNotSendInvalidName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Petrova Darya");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79333333333");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=button]")).click(); //send
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= name] span.input__sub")).getText().trim();
        assertEquals(expectedText, actualText);

    }
    @Test
    void shouldNotSendEmptySpace() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79333333333");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=button]")).click(); //send
        String expectedText = "Поле обязательно для заполнения";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= name] span.input__sub")).getText().trim();
        assertEquals(expectedText, actualText);

    }

    @Test
    void shouldNotSendWithoutPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петрова Дарья");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=button]")).click(); //send
        String expectedText = "Поле обязательно для заполнения";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= phone] span.input__sub")).getText().trim();
        assertEquals(expectedText, actualText);

    }
    @Test
    void shouldNotSendInvalidPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петрова Дарья");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79333333");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=button]")).click(); //send
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= phone] span.input__sub")).getText().trim();
        assertEquals(expectedText, actualText);

    }
    @Test
    void shouldNotSendInvalidLongPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петрова Дарья");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7933333354645");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=button]")).click(); //send
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= phone] span.input__sub")).getText().trim();
        assertEquals(expectedText, actualText);

    }

    @Test
    void shouldNotSendInvalidWithoutPlus() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петрова Дарья");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89333333470");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("[type=button]")).click(); //send
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        String actualText = driver.findElement(By.cssSelector("[data-test-id= phone] span.input__sub")).getText().trim();
        assertEquals(expectedText, actualText);

    }
    @Test
    void shouldNotSendInvalidWithoutCheckbox() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петрова Дарья");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79333333470");
        driver.findElement(By.cssSelector("[type=button]")).click(); //send
        String expectedText = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actualText = driver.findElement(By.cssSelector("[data-test-id= agreement].input_invalid.checkbox")).getText().trim();
        assertEquals(expectedText, actualText);

    }


}
