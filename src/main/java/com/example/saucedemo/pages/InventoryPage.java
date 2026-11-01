package com.example.saucedemo.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage {
    private final WebDriver driver;
    private final By pageTitle = By.cssSelector(".title");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBe(pageTitle, "Products"));
    }

    public String getTitle() {
        return driver.findElement(pageTitle).getText();
    }
}