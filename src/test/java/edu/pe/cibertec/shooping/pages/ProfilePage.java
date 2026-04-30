package edu.pe.cibertec.shooping.pages;

import edu.pe.cibertec.shooping.hooks.AppiumHooks;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    private By perfilTab = By.xpath("//android.widget.TextView[@text=\"Perfil\"]");
    private By profileTitle = By.xpath("//android.widget.TextView[@text=\"Mi Perfil\"]");
    private By userEmail = By.xpath("//android.widget.TextView[@text=\"user1@test.com\"]");
    private By logoutButton = By.xpath("//android.view.View[@content-desc=\"Cerrar sesión\"]");
    private By loginWelcome = By.xpath("//android.widget.TextView[@text=\"Bienvenido de vuelta\"]");

    public ProfilePage() {
        this.driver = AppiumHooks.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToProfile() {
        wait.until(ExpectedConditions.elementToBeClickable(perfilTab)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileTitle));
    }

    public String getCurrentUserEmail() {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(userEmail));
        return emailElement.getText();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public boolean isLoginScreenDisplayed() {
        return !driver.findElements(loginWelcome).isEmpty();
    }
}