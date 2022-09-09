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
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
    }
    @BeforeEach
            void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }
    @AfterEach
    void tearsDown(){
        driver.quit();
        driver=null;
    }
    @Test
    void SuccessOrderingCard(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Хоменко Игорь");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79119994143");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector(".paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",text.trim());
    }
    @Test
    void InvalidSurnameOrderingCard(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Khomenko Igor");
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(new ByChained(By.cssSelector("[data-test-id=\"name\"]"),By.cssSelector(".input__sub"))).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",text.trim());
    }
    @Test
    void InvalidPhoneOrderingCard(){
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Хоменко Игорь");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("1");
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(new ByChained(By.cssSelector("[data-test-id=\"phone\"]"),By.cssSelector(".input__sub"))).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",text.trim());
    }
    @Test
    void InvalidCheckBoxOrderingCard(){
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Хоменко Игорь");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79119994143");
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector(".checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй",text.trim());
    }
}