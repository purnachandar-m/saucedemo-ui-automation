package com.example.saucedemo.steps;

import com.example.saucedemo.pages.InventoryPage;
import com.example.saucedemo.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginSteps {
    @Given("the SauceDemo login page is open")
    public void openLoginPage() {
        new LoginPage(Hooks.driver()).open();
    }

    @When("I log in with username {string} and password {string}")
    public void logIn(String username, String password) {
        new LoginPage(Hooks.driver()).login(username, password);
    }

    @Then("the products page is displayed")
    public void verifyProductsPage() {
        Assert.assertEquals(new InventoryPage(Hooks.driver()).getTitle(), "Products");
    }

    @Then("the login error contains {string}")
    public void verifyLoginError(String expectedMessage) {
        Assert.assertTrue(new LoginPage(Hooks.driver()).getErrorMessage().contains(expectedMessage));
    }

    @Given("I am logged in as {string}")
    public void logInAs(String username) {
        WebDriver driver = Hooks.driver();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(username, "secret_sauce");
        new InventoryPage(driver);
    }

    @When("I add {string} to the cart")
    public void addItemToCart(String productName) {
        WebDriver driver = Hooks.driver();
        String productId = productName.toLowerCase(Locale.ROOT).replace(' ', '-');
        By addButton = By.cssSelector("button[data-test='add-to-cart-" + productId + "']");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(addButton)).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBe(By.className("shopping_cart_badge"), "1"));
    }

    @Then("the cart contains {string}")
    public void verifyCartContains(String productName) {
        WebDriver driver = Hooks.driver();
        driver.findElement(By.className("shopping_cart_link")).click();
        By cartProductNames = By.cssSelector(
                "[data-test='inventory-item'] [data-test='inventory-item-name']");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(cartProductNames));

        boolean itemIsInCart = driver.findElements(cartProductNames).stream()
                .map(WebElement::getText)
                .anyMatch(productName::equals);
        Assert.assertTrue(itemIsInCart, "Expected cart to contain " + productName);
    }
}