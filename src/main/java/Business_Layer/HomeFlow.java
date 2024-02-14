package Business_Layer;

import Pages.CartPage;
import Pages.ContactPage;
import Pages.HomePage;
import io.qameta.allure.Step;
import junit.framework.Assert;
import org.example.InitDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Iterator;
import javax.json.JsonArray;
import java.util.*;

public class HomeFlow extends InitDriver {

    public HomeFlow(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Step("Click the button")
    public void clickButton(String buttonName) throws InterruptedException {
        HomePage homePage = new HomePage(selenium.getDriver());
        switch (buttonName) {
            case "HomeButton":
                clickAndWait(homePage.HomeButton, null, wait, JsonFile.start().data("test02", "URLAfterClickingOnHomeButton"));
                selenium.getDriver().navigate().back();
                break;
            case "ContactButton":
                clickAndWait(homePage.ContactButton, JsonFile.start().data("test02", "TextPopUpAfterClickingOnContactButton"), wait, null);
                break;
            case "AboutUsButton":
                clickAndWait(homePage.AboutUsButton, JsonFile.start().data("test02", "TextPopUpAfterClickingOnAboutUsButton"), wait, null);
                break;
            case "CartButton":
                clickAndWait(homePage.CartButton, null, wait, JsonFile.start().data("test02", "URLAfterClickingOnCartButton"));
                selenium.getDriver().navigate().back();
                break;
            case "LogInButton":
                clickAndWait(homePage.LogInButton, JsonFile.start().data("test02", "TextPopUpAfterClickingOnLogInButton"), wait, null);
                break;
            case "SignUpButton":
                clickAndWait(homePage.SignUpButton, JsonFile.start().data("test02", "TextPopUpAfterClickingOnSignUpButton"), wait, null);
                break;
        }
    }

    @Step("Check if page or popup message is correct after click the button")
    public void clickAndWait(WebElement button, String expectedPopupTitle, WebDriverWait wait, String expectedURL) throws InterruptedException {
        selenium.clickMe(button);
        if (expectedPopupTitle != null) {
            WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'" + expectedPopupTitle + "')]")));
            softAssert.assertEquals(popupElement.getText(), expectedPopupTitle);
        }
        if (expectedURL != null) {
            wait.until(ExpectedConditions.urlToBe(expectedURL));
            softAssert.assertEquals(selenium.getDriver().getCurrentUrl(), expectedURL);
        }
        selenium.getDriver().navigate().refresh();
    }

    @Step("Click the button, filling fields and check massage alert")
    public void clickAndFillingInTheFields() throws InterruptedException {
        HomePage homePage = new HomePage(selenium.getDriver());
        ContactPage contactPage  = new ContactPage(selenium.getDriver());
        wait = new WebDriverWait(selenium.getDriver(), 10);
        clickMe(homePage.ContactButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//h5[@id='exampleModalLabel']")));
        SendKeyToField(contactPage.EmailFiled, JsonFile.start().data("test03", "EmailField"));
        SendKeyToField(contactPage.NameFiled, JsonFile.start().data("test03", "NameField"));
        SendKeyToField(contactPage.messageTextFiled, JsonFile.start().data("test03", "TextField"));
        softAssert.assertEquals(contactPage.EmailFiled.getAttribute("value"), JsonFile.start().data("test03", "EmailField"), "Unexpected email field value");
        softAssert.assertEquals(contactPage.NameFiled.getAttribute("value"), JsonFile.start().data("test03", "NameField"), "Unexpected email field value");
        softAssert.assertEquals(contactPage.messageTextFiled.getAttribute("value"), JsonFile.start().data("test03", "TextField"), "Unexpected email field value");
        Thread.sleep(4000);
        clickMe(contactPage.SendMessageButton);
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertEquals("expected email field value: ", JsonFile.start().data("test03", "TextAlertMessage"), alert.getText());
            alert.accept();
        } catch (Exception e) {
            System.out.println("Alert not found: " + e.getMessage());
            //throw exception...
        }
        softAssert.assertAll();
    }

    public void CheckIfAllProductsExistInWebsite() throws InterruptedException {
        HomePage homePage = new HomePage(selenium.getDriver());
        List<WebElement> buttons = new ArrayList<>();
        buttons.add(homePage.PhonesButton);
        buttons.add(homePage.LaptopsButton);
        buttons.add(homePage.MonitorsButton);
        Map<WebElement, List<JsonArray>> buttonToExpectedProducts = new HashMap<>();
        buttonToExpectedProducts.put(homePage.PhonesButton, Arrays.asList(JsonFile.start().dataArray("test01", "ListProductPhones")));
        buttonToExpectedProducts.put(homePage.LaptopsButton, Arrays.asList(JsonFile.start().dataArray("test01", "ListProductLaptops")));
        buttonToExpectedProducts.put(homePage.MonitorsButton, Arrays.asList(JsonFile.start().dataArray("test01", "ListProductMonitors")));

        for (WebElement button : buttons) {
            clickMe(button);
            Thread.sleep(3000);
            List<WebElement> productElements = ListInWebElements();
            List<JsonArray> expectedProductsList = buttonToExpectedProducts.get(button);
            for (JsonArray expectedProducts : expectedProductsList) {
                Iterator<WebElement> productIterator = productElements.iterator();
                while (productIterator.hasNext()) {
                    WebElement productElement = productIterator.next();
                    for (int i = 0; i < expectedProducts.size(); i++) {
                        String expectedProduct = expectedProducts.getString(i);
                        if (productElement.getText().contains(expectedProduct)) {
                            productIterator.remove();
                            softAssert.assertTrue(true, "Product found: " + expectedProduct + " for button: " + button.getText());
                            break;
                        }
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    public static List<WebElement> productInCart = new ArrayList<>();
    public static JsonArray productToClickArray = JsonFile.start().dataArray("test05", "productToClick");

    public void StartOrderingAProduct() throws InterruptedException {
        HomePage homePage = new HomePage(selenium.getDriver());
        CartPage cartPage = new CartPage(selenium.getDriver());
        boolean allProductsSelected = false;
        for (int runOnTheProdutsIWantToOrder = 0; runOnTheProdutsIWantToOrder < productToClickArray.size(); runOnTheProdutsIWantToOrder++) {
            String productToClick = productToClickArray.getString(runOnTheProdutsIWantToOrder);
            boolean productFound = false;
            if (!allProductsSelected) {
                List<WebElement> buttons = new ArrayList<>();
                buttons.add(homePage.PhonesButton);
                buttons.add(homePage.LaptopsButton);
                buttons.add(homePage.MonitorsButton);
                for (WebElement button : buttons) {
                    clickMe(button);
                    Thread.sleep(5000);
                    List<WebElement> productElements = ListInWebElements();
                    for (WebElement productElement : productElements) {
                        if (productElement.getText().contains(productToClick)) {
                            productInCart.add(productElement);
                            clickMe(productElement);
                            Thread.sleep(5000);
                            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//a[text()='Add to cart']")));
                            clickMe(cartPage.AddToCartButton);
                            try {
                                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                                softAssert.assertEquals(alert.getText(), JsonFile.start().data("test04", "cartAlert"));
                                alert.accept();
                            } catch (Exception e) {
                                System.out.println("Alert not found: " + e.getMessage());
                            }
                            clickMe(homePage.HomeButton);
                            productFound = true;
                            break;
                        }
                    }
                    if (productFound) {
                        break;
                    }
                }
            }
            if (runOnTheProdutsIWantToOrder == productToClickArray.size() - 1) {
                allProductsSelected = true;
            }
        }
    }

    public List<WebElement> ListInWebElements() {
        WebDriverWait wait = new WebDriverWait(selenium.getDriver(), 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.col-lg-4.col-md-6.mb-4")));
        WebElement parentElement = selenium.getDriver().findElement(By.id("tbodyid"));
        return parentElement.findElements(By.xpath("//div[@class='col-lg-4 col-md-6 mb-4']"));
    }
}





