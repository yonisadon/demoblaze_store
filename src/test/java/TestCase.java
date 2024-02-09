import Business_Layer.CartFlow;
import Business_Layer.HomeFlow;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import static org.testng.FileAssert.fail;
public class TestCase extends BaseSession {

    @Test(priority = 1)
    @Description("Click the buttons and check if products exists")
    public void Test01() throws InterruptedException {
        try {
            HomeFlow.start().CheckIfAllProductsExistInWebsite();
            System.out.println("passed1");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }

    @Test(priority = 2)
    @Description("Click the button and Check if page or popup message is correct")
    public void Test02() throws InterruptedException {
        try {
            HomeFlow.start().clickButton("HomeButton");
            HomeFlow.start().clickButton("ContactButton");
            HomeFlow.start().clickButton("AboutUsButton");
            HomeFlow.start().clickButton("CartButton");
            HomeFlow.start().clickButton("LogInButton");
            HomeFlow.start().clickButton("SignUpButton");
            System.out.println("passed2");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }

    @Test(priority = 3)
    @Description("Click the button, filling fields and check massage alert")
    public void Test03() throws InterruptedException {
        try {
            HomeFlow.start().clickAndFillingInTheFields();
            System.out.println("passed3");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }

    @Test(priority = 4)
    @Description("Start ordering a product, Making the order")
    public void Test04() throws InterruptedException {
        try {
            HomeFlow.start().StartOrderingAProduct();
            CartFlow.start().MakingTheOrder();
            System.out.println("passed4");
        } catch (AssertionError failed) {
            fail("Test Failed! - See details..." + failed);
        }
    }
}
