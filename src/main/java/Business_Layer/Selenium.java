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

public class Selenium {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static SoftAssert softAssert;

    public static void startSession() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        softAssert = new SoftAssert();
        driver.manage().window().maximize();
        driver.get(JsonFile.start().data("beforeTest", "URLOfWebsite"));
    }

    public static void initAll( ) {
        new HomePage(driver);
        new ContactPage(driver);
        new CartPage(driver);
        //new Selenium();
    }

    public static void clickMe(WebElement element)  {
        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfAllElements(element));
            element.click();
        } catch (AssertionError e) {
            System.out.println("Click not found: " + e.getMessage());
        }
    }

    public static void SendKeyToField(WebElement element, String elementSendKey){
        wait = new WebDriverWait(driver, 10);
        element.sendKeys(elementSendKey);
    }
}
