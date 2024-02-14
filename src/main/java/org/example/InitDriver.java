package org.example;

import Business_Layer.Selenium;
import org.openqa.selenium.WebDriver;

public class InitDriver extends Selenium{

    protected Selenium selenium;

    public InitDriver(WebDriver driver) {
        //
        super(driver);
        //
        selenium = new Selenium(driver);
    }
}
