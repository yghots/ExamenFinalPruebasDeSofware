package edu.pe.cibertec.shooping.pages;

import edu.pe.cibertec.shooping.hooks.AppiumHooks;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class ScreenPlayCatalogPage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    // --- Localizadores del Login ---
    private By emailInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"Email\"]]");
    private By passwordInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"Contraseña\"]]");
    private By loginButton = By.xpath("//android.view.View[android.widget.TextView[@text=\"Iniciar Sesión\"]]");
    private By welcomeText = By.xpath("//android.widget.TextView[@text=\"Bienvenido de vuelta\"]");

    // --- Localizadores del Catálogo ---
    private By searchInput = By.xpath("//android.widget.EditText");
    private By allFilter = By.xpath("//android.view.View[android.widget.TextView[@text=\"Todos\"]]");
    private By categoriasTab = By.xpath("//android.widget.TextView[@text=\"Categorías\"]");
    private By electronicaCard = By.xpath("//android.view.View[android.widget.TextView[@text=\"Electrónica\"]]");
    private By productScroll = By.xpath("//android.view.View[@scrollable=\"true\"]");

    // Credenciales de prueba
    private static final String TEST_EMAIL = "user1@test.com";
    private static final String TEST_PASSWORD = "password1";

    public ScreenPlayCatalogPage() {
        this.driver = AppiumHooks.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- MÉTODOS DE LOGIN ----------
    public boolean isLoginScreenVisible() {
        return !driver.findElements(welcomeText).isEmpty();
    }

    public void login() {
        login(TEST_EMAIL, TEST_PASSWORD);
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText));

        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        passwordField.clear();
        passwordField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.TextView[@text=\"Inicio\"]")));
    }

    // ---------- MÉTODOS DEL CATÁLOGO ----------
    public void navigateToCatalog() {
        wait.until(ExpectedConditions.elementToBeClickable(categoriasTab)).click();

        boolean allFilterPresent = !driver.findElements(allFilter).isEmpty();
        if (!allFilterPresent) {
            wait.until(ExpectedConditions.elementToBeClickable(electronicaCard)).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(allFilter)).click();
    }

    public void searchProduct(String productName) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.clear();
        input.sendKeys(productName);
        driver.executeScript("mobile: performEditorAction", Map.of("action", "search"));
        // Pequeña pausa para que carguen los resultados
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void filterByCategory(String category) {
        By categoryFilter = By.xpath(
                "//android.view.View[android.widget.TextView[@text=\"" + category + "\"]]");
        wait.until(ExpectedConditions.elementToBeClickable(categoryFilter)).click();
    }

    public boolean isProductListDisplayed() {
        List<WebElement> products = driver.findElements(
                By.xpath("//android.view.View[@scrollable=\"true\"]//android.widget.TextView")
        );
        return !products.isEmpty();
    }

    public boolean isProductContainingTextVisible(String partialText) {
        // Buscar cualquier TextView (excepto los que están dentro de un EditText) que contenga el texto,
        // o cualquier elemento con content-desc que lo contenga.
        By productWithText = By.xpath(
                "//android.widget.TextView[contains(@text, \"" + partialText + "\") and not(ancestor::android.widget.EditText)]"
                        + " | //*[contains(@content-desc, \"" + partialText + "\")]"
        );
        return !driver.findElements(productWithText).isEmpty();
    }

    public boolean isCategoryFilterActive(String category) {
        By categoryFilter = By.xpath(
                "//android.view.View[android.widget.TextView[@text=\"" + category + "\"]]");
        WebElement filter = driver.findElement(categoryFilter);
        String checked = filter.getAttribute("checked");
        return "true".equals(checked);
    }
    public void addProductToCart(String productName) {
        // Asegurar que estamos en catálogo y con filtro "Todos"
        navigateToCatalog();
        // Buscar el botón "Agregar al carrito" asociado al producto
        By addToCartButton = By.xpath(
                "//android.widget.TextView[@text=\"" + productName + "\"]/following-sibling::android.view.View[android.widget.Button]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }
}