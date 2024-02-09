package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactPage {

    public ContactPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "recipient-email")
    public WebElement EmailFiled;
    @FindBy(xpath = "//*[@id='recipient-name']")
    public WebElement NameFiled;
    @FindBy(xpath = "//div[@class='form-group']//textarea[@id='message-text']")
    public WebElement messageTextFiled;
    @FindBy(xpath = "//div[@class='modal-footer']//button[text()='Send message']")
    public WebElement SendMessageButton;
}
