package edu.pe.cibertec.shooping.pages;

import edu.pe.cibertec.shooping.hooks.AppiumHooks;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    private By welcomeText = By.xpath("//android.widget.TextView[@text=\"Bienvenido de vuelta\"]");
    private By emailInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"Email\"]]");
    private By passwordInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"Contraseña\"]]");
    private By loginButton = By.xpath("//android.view.View[android.widget.TextView[@text=\"Iniciar Sesión\"]]");
    private By loginErrorMessage = By.xpath("//android.widget.TextView[@text=\"Email no registrado\"]");

    private By homeTab = By.xpath("//android.widget.TextView[@text=\"Inicio\"]");

    public LoginPage() {
        this.driver = AppiumHooks.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isLoginScreenDisplayed() {
        return !driver.findElements(welcomeText).isEmpty();
    }

    public boolean isHomeScreenDisplayed() {
        return !driver.findElements(homeTab).isEmpty();
    }

    public boolean isLoginErrorMessageDisplayed() {
        return !driver.findElements(loginErrorMessage).isEmpty();
    }

    public void loginWithValidCredentials(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText));
        fillCredentials(email, password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeTab));
    }

    public void loginWithInvalidCredentials(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText));
        fillCredentials(email, password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginErrorMessage));
    }

    private void fillCredentials(String email, String password) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        emailField.clear();
        emailField.sendKeys(email);
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        passwordField.clear();
        passwordField.sendKeys(password);
    }
}