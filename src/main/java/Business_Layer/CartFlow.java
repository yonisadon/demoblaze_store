package Business_Layer;

import Pages.CartPage;
import Pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.json.JsonArray;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static Business_Layer.Selenium.*;
import static Business_Layer.Selenium.softAssert;

public class CartFlow {

    public static CartFlow start() {
        CartFlow cartFlow = new CartFlow();
        return cartFlow;
    }

    List<String> orderedProducts = new ArrayList<>();
    List<String> productsInCart = new ArrayList<>();
    List<String> pricesInCart = new ArrayList<>();
    JsonArray productToClickArray;
    String totalPriceFormatted;

    public void MakingTheOrder() throws InterruptedException {
        CartPage cartPage = new CartPage(driver);
        HomePage homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, 20);
        clickMe(homePage.CartButton);
        wait.until(ExpectedConditions.urlToBe(JsonFile.start().data("test02", "URLAfterClickingOnCartButton")));
        productToClickArray = JsonFile.start().dataArray("test05", "productToClick");
        Thread.sleep(2000);
        List<WebElement> cartItems = driver.findElements(By.xpath("//*[@id='tbodyid']/tr"));
        for (WebElement item : cartItems) {
            productsInCart.add(item.findElement(By.xpath("./td[2]")).getText().trim());
            pricesInCart.add(item.findElement(By.xpath("./td[3]")).getText().trim());
        }
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
        Thread.sleep(3000);
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
                        softAssert.assertEquals(value, today);
                        break;
                }
            }
            softAssert.assertAll();
        }
    }

    private static String rebuildStringToDateFormat(String dateStr){
        String[] dateParts = dateStr.split("/");
        int day1 = Integer.parseInt(dateParts[0]);
        int month1 = Integer.parseInt(dateParts[1]);
        int year =  Integer.parseInt(dateParts[2]);
        return day1 + "/" + month1 + "/" + year;
    }

    private static String getTodayDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String formattedDate = today.format(formatter);
        return formattedDate;
    }
}
