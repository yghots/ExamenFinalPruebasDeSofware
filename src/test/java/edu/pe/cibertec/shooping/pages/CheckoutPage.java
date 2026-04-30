package edu.pe.cibertec.shooping.pages;

import edu.pe.cibertec.shooping.hooks.AppiumHooks;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class CheckoutPage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    private By cartTab = By.xpath("//android.widget.TextView[@text=\"Carrito\"]");

    private By emptyCartMessage = By.xpath("//android.widget.TextView[@text=\"Tu carrito está vacío\"]");
    private By deleteButton = By.xpath("//android.view.View[@content-desc=\"Eliminar\"]");
    private By proceedToPaymentButton = By.xpath("//android.view.View[android.widget.TextView[@text=\"Proceder al Pago\"]]");

    private By checkoutTitle = By.xpath("//android.widget.TextView[@text=\"Checkout\"]");
    private By direccionInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"Dirección\"]]");
    private By ciudadInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"Ciudad\"]]");
    private By codigoPostalInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"Código Postal\"]]");

    private By numeroTarjetaInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"Número de tarjeta\"]]");
    private By expiraMMYYInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"MM/YY\"]]");
    private By cvvInput = By.xpath("//android.widget.EditText[android.widget.TextView[@text=\"CVV\"]]");

    private By confirmarButton = By.xpath("//android.view.View[android.widget.TextView[@text=\"Confirmar Compra\"]]");

    private By pedidoConfirmadoText = By.xpath("//android.widget.TextView[@text=\"¡Pedido Confirmado!\"]");

    private By direccionError = By.xpath("//android.widget.TextView[@text=\"La dirección es requerida\"]");

    public CheckoutPage() {
        this.driver = AppiumHooks.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartTab)).click();
    }

    public boolean isCartEmptyMessageDisplayed() {
        return !driver.findElements(emptyCartMessage).isEmpty();
    }

    public void clearCartIfNotEmpty() {
        if (!isCartEmptyMessageDisplayed()) {
            List<WebElement> deleteButtons = driver.findElements(deleteButton);
            for (WebElement btn : deleteButtons) {
                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
                try { Thread.sleep(500); } catch (InterruptedException ignored) { }
            }
        }
    }

    public boolean isProductInCart(String productName) {
        By productLocator = By.xpath("//android.widget.TextView[@text=\"" + productName + "\"]");
        return !driver.findElements(productLocator).isEmpty();
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToPaymentButton)).click();
    }

    public void enterShippingData(String direccion, String ciudad, String codigoPostal) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutTitle));
        WebElement dir = wait.until(ExpectedConditions.elementToBeClickable(direccionInput));
        dir.clear();
        dir.sendKeys(direccion);
        WebElement ciu = wait.until(ExpectedConditions.elementToBeClickable(ciudadInput));
        ciu.clear();
        ciu.sendKeys(ciudad);
        WebElement cp = wait.until(ExpectedConditions.elementToBeClickable(codigoPostalInput));
        cp.clear();
        cp.sendKeys(codigoPostal);
    }

    public void enterOnlyCityAndPostal(String ciudad, String codigoPostal) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutTitle));
        WebElement dir = wait.until(ExpectedConditions.elementToBeClickable(direccionInput));
        dir.clear();
        WebElement ciu = wait.until(ExpectedConditions.elementToBeClickable(ciudadInput));
        ciu.clear();
        ciu.sendKeys(ciudad);
        WebElement cp = wait.until(ExpectedConditions.elementToBeClickable(codigoPostalInput));
        cp.clear();
        cp.sendKeys(codigoPostal);
    }

    public void enterCreditCardData(String numero, String exp, String cvv) {
        WebElement num = wait.until(ExpectedConditions.elementToBeClickable(numeroTarjetaInput));
        num.clear();
        num.sendKeys(numero);
        WebElement expEl = wait.until(ExpectedConditions.elementToBeClickable(expiraMMYYInput));
        expEl.clear();
        expEl.sendKeys(exp);
        WebElement cvvEl = wait.until(ExpectedConditions.elementToBeClickable(cvvInput));
        cvvEl.clear();
        cvvEl.sendKeys(cvv);
    }

    public void confirmPurchase() {
        scrollUp();
        scrollUp();
        WebElement confirm = wait.until(ExpectedConditions.presenceOfElementLocated(confirmarButton));
        wait.until(ExpectedConditions.elementToBeClickable(confirm)).click();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) { }
    }

    private void scrollUp() {
        int startX = 540;
        int startY = 1600;
        int endY = 800;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ofMillis(0),
                        PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(800),
                        PointerInput.Origin.viewport(), startX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
        try { Thread.sleep(800); } catch (InterruptedException ignored) { }
    }

    public boolean isPurchaseSuccessMessageDisplayed() {
        return !driver.findElements(pedidoConfirmadoText).isEmpty();
    }

    public boolean isDireccionErrorDisplayed() {
        return !driver.findElements(direccionError).isEmpty();
    }

    public void ensureLoggedIn() {
        ScreenPlayCatalogPage catalogPage = new ScreenPlayCatalogPage();
        if (catalogPage.isLoginScreenVisible()) {
            catalogPage.login();
        }
    }
}