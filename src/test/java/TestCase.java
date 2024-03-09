import Business_Layer.CartFlow;
import Business_Layer.HomeFlow;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static Business_Layer.Selenium.softAssert;
import static org.testng.FileAssert.fail;
public class TestCase extends BaseSession {

    HomeFlow homeFlow;
    CartFlow cartFlow;
    @Test(priority = 1)
    @Description("Click the buttons and check if products exists")
    public void CheckIfAllProductsExistInWebsite() throws InterruptedException {
        try {
            homeFlow = new HomeFlow(driver);
            homeFlow.AllProductsExistInWebsite();
            System.out.println("passed1");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }

    @Test(priority = 2)
    @Description("Click the button and Check if page or popup message is correct")
    public void CheckTheTransitionBetweenTheButtonAndThePageOrPopUpPanel() throws InterruptedException {
        try {
            homeFlow = new HomeFlow(driver);
            homeFlow.clickButton("HomeButton");
            homeFlow.clickButton("ContactButton");
            homeFlow.clickButton("AboutUsButton");
            homeFlow.clickButton("CartButton");
            homeFlow.clickButton("LogInButton");
            homeFlow.clickButton("SignUpButton");
            System.out.println("passed2");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }

    @Test(priority = 3)
    @Description("Click the button, filling fields and check massage alert")
    public void FillingPopUpPanelAndSendMassage() throws InterruptedException {
        try {
            homeFlow = new HomeFlow(driver);
            homeFlow.clickAndFillingInTheFields();
            System.out.println("passed3");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }

    @Test(priority = 4)
    @Description("Start ordering a product, Making the order")
    public void OrderingProductsAndCheckingTheDataInTheCart() throws InterruptedException {
        try {
            homeFlow = new HomeFlow(driver);
            cartFlow = new CartFlow(driver);
            homeFlow.StartOrderingAProduct();
            cartFlow.MakingTheOrder();
            cartFlow.FillingInDetailsInTheOrder();
            System.out.println("passed4");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }
    @Test(priority = 5)
    @Description("Ordering products deleting the requested products and checking the data in the cart")
    public void OrderingProductsDeletingTheRequestedProductsAndCheckingTheDataInTheCart() throws InterruptedException {
        try {
            homeFlow = new HomeFlow(driver);
            cartFlow = new CartFlow(driver);
            homeFlow.StartOrderingAProduct();
            cartFlow.checkIfProductsAndThePricesOfTheProductsExistsInMyCart();
            cartFlow.deleteDesiredProductsInCart();
            System.out.println("passed5");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }
}
