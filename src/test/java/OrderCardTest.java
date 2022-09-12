import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearsDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void successOrderingCard() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Хоменко Игорь");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79119994143");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector(".paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    void invalidSurnameNull() {
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    void invalidSurnameOrderingCard() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Khomenko Igor");
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    @Test
    void invalidPhoneNull() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Хоменко Игорь");
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    void invalidPhoneOrderingCard() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Хоменко Игорь");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("1");
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void invalidCheckBoxOrderingCard() {
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Хоменко Игорь");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79119994143");
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector(".input_invalid")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}
