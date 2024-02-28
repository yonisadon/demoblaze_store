package Business_Layer;

import Pages.CartPage;
import Pages.HomePage;
import junit.framework.Assert;
import org.example.InitDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.json.JsonArray;
import javax.json.JsonException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static Business_Layer.HomeFlow.productToClickArray;


public class CartFlow extends InitDriver {

    CartPage cartPage;
    HomePage homePage;

    public CartFlow(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // פעולה לקריאת ה־JSON Array והמרתו לרשימה
    JsonArray jsonArray = JsonFile.start().dataArray("test06", "productDelete");
    List<String> desiredProducts = new ArrayList<>();
    List<String> orderedProducts = new ArrayList<>();
    List<String> productsInCart = new ArrayList<>();
    List<String> pricesInCart = new ArrayList<>();
    String totalPriceFormatted;

    public void MakingTheOrder() throws InterruptedException {
        cartPage = new CartPage(selenium.getDriver());
        homePage = new HomePage(selenium.getDriver());
        clickMe(homePage.CartButton);
        wait.until(ExpectedConditions.urlToBe(JsonFile.start().data("test02", "URLAfterClickingOnCartButton")));
        //System.out.println(productToClickArray);
        Thread.sleep(2000);
        checkIfProductsAndThePricesOfTheProductsExistsInMyCart();
        for (int runnningOnTheProdutsIOrdered = 0; runnningOnTheProdutsIOrdered < productToClickArray.size(); runnningOnTheProdutsIOrdered++) {
            orderedProducts.add(productToClickArray.getString(runnningOnTheProdutsIOrdered).trim());
        }
        softAssert.assertEquals(productsInCart.size(), orderedProducts.size(), "Number of products in cart doesn't match the number of ordered products");
        for (String orderedProduct : orderedProducts) {
            softAssert.assertTrue(productsInCart.contains(orderedProduct), "Product '" + orderedProduct + "' not found in the cart");
        }
        double totalPrice = 0;
        for (String price : pricesInCart) {
            totalPrice += Double.parseDouble(price.replaceAll("[^0-9.]", ""));
            DecimalFormat decimalFormat = new DecimalFormat("#");
            totalPriceFormatted = decimalFormat.format(totalPrice);
        }
    }

    public void FillingInDetailsInTheOrder() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(cartPage.PlaceOrderButton));
        clickMe(cartPage.PlaceOrderButton);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//button[text()='Place Order']")));
        SendKeyToField(cartPage.NameField, JsonFile.start().data("test04", "NameForInviteField"));
        SendKeyToField(cartPage.CountryField, JsonFile.start().data("test04", "CountryField"));
        SendKeyToField(cartPage.CityField, JsonFile.start().data("test04", "CityField"));
        SendKeyToField(cartPage.CardField, JsonFile.start().data("test04", "CardField"));
        SendKeyToField(cartPage.MonthField, JsonFile.start().data("test04", "MonthField"));
        SendKeyToField(cartPage.YearField, JsonFile.start().data("test04", "YearField"));
        clickMe(cartPage.PurchaseButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='lead text-muted ']")));
        String fields[] = cartPage.AlertPurchase.getText().split("\n");
        for (String field : fields) {
            String[] parts = field.split(":");
            boolean finishToFields = false;
            if (parts.length == 2) {
                String label = parts[0].trim();
                String value = parts[1].trim();
                switch (label) {
                    case "Id":
                        System.out.println("Id: " + value);
                        break;
                    case "Amount":
                        String amount[] = value.split(" USD");
                        softAssert.assertEquals(totalPriceFormatted, amount[0]);
                        break;
                    case "Card Number":
                        softAssert.assertEquals(value, JsonFile.start().data("test04", "CardField"));
                        break;
                    case "Name":
                        softAssert.assertEquals(value, JsonFile.start().data("test04", "NameForInviteField"));
                        break;
                    case "Date":
                        String today = rebuildStringToDateFormat(getTodayDate());
                        value = rebuildStringToDateFormat(value);
                        finishToFields = true;
                        softAssert.assertEquals(value, today);
                        break;
                }
            }
            if (finishToFields) {
                Thread.sleep(2000);
                clickMe(cartPage.OkButtonAfterFillingTheFields);
            }
            softAssert.assertAll();

        }
    }

    private static String rebuildStringToDateFormat(String dateStr) {
        String[] dateParts = dateStr.split("/");
        int day1 = Integer.parseInt(dateParts[0]);
        int month1 = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        return day1 + "/" + month1 + "/" + year;
    }

    private static String getTodayDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String formattedDate = today.format(formatter);
        return formattedDate;
    }

    public void deleteProductInTheCartPage() throws InterruptedException {
        readDesiredProductsFromJsonArray(jsonArray);
        for (String desiredProduct : desiredProducts) {
            //System.out.println(desiredProduct);
            for (String productInCart : productsInCart) {
                //System.out.println(productInCart);
                if (productInCart.contains(desiredProduct)) {
                    WebElement DeleteProductInCart = selenium.getDriver().findElement(By.xpath("//*[@id='tbodyid']//td[contains(text(),'"+productInCart+"')]/following-sibling::td/a[text()='Delete']"));
                    clickMe(DeleteProductInCart);
                    Thread.sleep(2000);
                }
            }
        }
    }

    private void readDesiredProductsFromJsonArray(JsonArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            try {
                desiredProducts.add(jsonArray.getString(i));
                System.out.println(jsonArray.getString(i));
            } catch (JsonException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkIfProductsAndThePricesOfTheProductsExistsInMyCart()  {
        homePage = new HomePage(selenium.getDriver());
        List<WebElement> cartItems = selenium.getDriver().findElements(By.xpath("//*[@id='tbodyid']/tr"));
        for (WebElement item : cartItems) {
            productsInCart.add(item.findElement(By.xpath("./td[2]")).getText().trim());
            pricesInCart.add(item.findElement(By.xpath("./td[3]")).getText().trim());
        }
    }
}

        //לשימוש עתידי
//        // קריאה לפונקציה שבודקת ומתאימה את המוצרים
//        matchProducts(productsInCarts, desiredProducts);
//    }
//
//    // פונקציה להתאמת המוצרים
//    private void matchProducts(List<String> products, List<String> desiredProducts) throws InterruptedException {
//        for (int i = 0; i < products.size(); i++) {
//            String product = products.get(i);
//            if (!desiredProducts.contains(product)) {
//                System.out.println(product + " " + desiredProducts);
//                products.remove(i);
//                System.out.println(products);
//                System.out.println(products.size());
//                i--;
//            }
//        }
//        checkMatchingProducts(productsInCarts, desiredProducts);
//    }
//
//    public void checkMatchingProducts(List<String> productsInCarts, List<String> desiredProducts) throws InterruptedException {
//        for (String desiredProduct : desiredProducts) {
//            System.out.println(desiredProduct);
//            for (String productInCart : productsInCarts) {
//                System.out.println(productInCart);
//                    if (productInCart.contains(desiredProduct)) {
//                            WebElement ee = selenium.getDriver().findElement(By.xpath("//*[@id='tbodyid']//td[contains(text(),'"+productInCart+"')]/following-sibling::td/a[text()='Delete']"));
//                            System.out.println(productInCart + ", " + desiredProduct);
//                            clickMe(ee);
//                            Thread.sleep(2000);
//                        }
//                    }
//                }
//            }












