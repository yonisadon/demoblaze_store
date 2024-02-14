
import Business_Layer.Selenium;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseSession {

    protected WebDriver driver;

    @BeforeClass
    public void Init()  {
        Selenium selenium = new Selenium(driver);
        selenium.startSession();
        selenium.initAll();
        driver = selenium.getDriver();
    }

    @AfterClass
    public void EndSession(){
        driver.close();
        driver.quit();
    }
}
