package Business_Layer;

import Pages.CartPage;
import Pages.ContactPage;
import Pages.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class Selenium {

    public static WebDriverWait wait;
    public static SoftAssert softAssert;
    private WebDriver driver;

    public Selenium(WebDriver driver) {
        this.driver = driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
    public WebDriver getDriver() {
        return driver;
    }

    public void startSession() {
        //WebDriverManager.chromedriver().browserVersion("122.0.6261.70").setup();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        softAssert = new SoftAssert();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get(JsonFile.start().data("beforeTest", "URLOfWebsite"));
    }

    public void initAll( ) {
        new HomePage(driver);
        new ContactPage(driver);
        new CartPage(driver);
    }

    public void clickMe(WebElement element)  {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(element));
            element.click();
        } catch (AssertionError e) {
            System.out.println("Click not found: " + e.getMessage());
        }
    }

    public void SendKeyToField(WebElement element, String elementSendKey){
        element.sendKeys(elementSendKey);
    }
}
