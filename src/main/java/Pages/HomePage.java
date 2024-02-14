package Pages;

import org.example.InitDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends InitDriver {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //Buttons for filtering products
    @FindBy(xpath = "//div//a[@id='itemc'][text()='Phones']")
    public WebElement PhonesButton;
    @FindBy(xpath = "//div//a[@id='itemc'][text()='Laptops']")
    public WebElement LaptopsButton;
    @FindBy(xpath = "//div//a[@id='itemc'][text()='Monitors']")
    public WebElement MonitorsButton;
    //Buttons
    @FindBy(xpath = "//div//li[@class='nav-item active']")
    public  WebElement HomeButton;
    @FindBy(xpath = "//a[@class='nav-link'][text()='Contact']")
    public  WebElement ContactButton;
    @FindBy(xpath = "//a[@class='nav-link'][text()='About us']")
    public  WebElement AboutUsButton;
    @FindBy(xpath = "//a[@class='nav-link'][text()='Cart']")
    public  WebElement CartButton;
    @FindBy(xpath = "//a[@class='nav-link'][text()='Log in']")
    public  WebElement LogInButton;
    @FindBy(xpath = "//a[@class='nav-link'][text()='Sign up']")
    public  WebElement SignUpButton;
}
