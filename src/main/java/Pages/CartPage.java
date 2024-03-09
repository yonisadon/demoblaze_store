package Pages;

import org.example.InitDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends InitDriver {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy (xpath = "//p[@class='lead text-muted ']")
    public  WebElement AlertPurchase;
    @FindBy(xpath = "//div//a[text()='Add to cart']")
    public  WebElement AddToCartButton;
    @FindBy(xpath = "//div//button[text()='Place Order']")
    public  WebElement PlaceOrderButton;
    @FindBy(xpath = "//div//input[@id='name']")
    public  WebElement NameField;
    @FindBy(xpath = "//div//input[@id='country']")
    public  WebElement CountryField;
    @FindBy(xpath = "//div//input[@id='city']")
    public  WebElement CityField;
    @FindBy(xpath = "//div//input[@id='card']")
    public  WebElement CardField;
    @FindBy(xpath = "//div//input[@id='month']")
    public  WebElement MonthField;
    @FindBy(xpath = "//div//input[@id='year']")
    public  WebElement YearField;
    @FindBy(xpath = "//div//button[text()='Purchase']")
    public WebElement PurchaseButton;
    @FindBy(xpath = "//Button[text()='OK']")
    public WebElement OkButtonAfterFillingTheFields;
    @FindBy(xpath = "//*[@id='orderModal']/div/div/div[3]/button[1]")
    public WebElement CloseButtonAfterClickingOk;
    @FindBy(id = "totalp")
    public WebElement TotalPriceInTheCartPage;
}
