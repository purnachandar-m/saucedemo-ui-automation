package com.example.saucedemo.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hooks {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    @Before
    public void startBrowser() {
        DRIVER.set(new ChromeDriver());
    }

    @After
    public void stopBrowser() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }

    public static WebDriver driver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("The browser has not been started for this scenario.");
        }
        return driver;
    }
}