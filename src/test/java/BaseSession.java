
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static Business_Layer.Selenium.*;

public class BaseSession {

    @BeforeClass
    public void Init()  {
        startSession();
        initAll();
    }

    @AfterClass
    public void EndSession(){
        driver.close();
        driver.quit();
    }
}
