package edu.pe.cibertec.shooping.steps;

import edu.pe.cibertec.shooping.pages.CheckoutPage;
import edu.pe.cibertec.shooping.pages.ScreenPlayCatalogPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class CheckoutSteps {

    private CheckoutPage checkoutPage;
    private ScreenPlayCatalogPage catalogPage;

    public CheckoutSteps() {
        this.checkoutPage = new CheckoutPage();
        this.catalogPage = new ScreenPlayCatalogPage();
    }

    @Given("que el usuario tiene productos en el carrito")
    public void que_el_usuario_tiene_productos_en_el_carrito() {
        checkoutPage.ensureLoggedIn();
        catalogPage.addProductToCart("Laptop HP Pavilion");
        checkoutPage.goToCart();
        Assertions.assertTrue(checkoutPage.isProductInCart("Laptop HP Pavilion"),
                "El producto no está en el carrito");
    }

    @Given("que el usuario tiene el carrito vacio")
    public void que_el_usuario_tiene_el_carrito_vacio() {
        checkoutPage.ensureLoggedIn();
        checkoutPage.goToCart();
        checkoutPage.clearCartIfNotEmpty();
        Assertions.assertTrue(checkoutPage.isCartEmptyMessageDisplayed(),
                "El carrito no está vacío después de la limpieza");
    }

    @When("procede al checkout")
    public void procede_al_checkout() {
        checkoutPage.proceedToCheckout();
    }

    @When("intenta proceder al checkout")
    public void intenta_proceder_al_checkout() {
        try {
            checkoutPage.proceedToCheckout();
        } catch (Exception e) {
            // Esperado si no hay botón (carrito vacío)
        }
    }

    @And("ingresa los datos de envio")
    public void ingresa_los_datos_de_envio() {
        checkoutPage.enterShippingData("Jr. Los Olivos 456", "Lima", "15037");
        // Después de dirección, rellenar tarjeta
        checkoutPage.enterCreditCardData("4111111111111111", "12/28", "123");
    }

    @And("omite la direccion de envio")
    public void omite_la_direccion_de_envio() {
        checkoutPage.enterOnlyCityAndPostal("Lima", "15037");
        // Incluso si falta dirección, rellenamos tarjeta para probar el error
        checkoutPage.enterCreditCardData("4111111111111111", "12/28", "123");
    }

    @And("confirma la compra")
    public void confirma_la_compra() {
        checkoutPage.confirmPurchase();
    }

    @Then("deberia ver el mensaje de compra exitosa")
    public void deberia_ver_el_mensaje_de_compra_exitosa() {
        Assertions.assertTrue(checkoutPage.isPurchaseSuccessMessageDisplayed(),
                "No se mostró el mensaje de compra exitosa");
    }

    @Then("deberia ver mensaje de carrito vacio")
    public void deberia_ver_mensaje_de_carrito_vacio() {
        Assertions.assertTrue(checkoutPage.isCartEmptyMessageDisplayed(),
                "No se mostró el mensaje de carrito vacío");
    }

    @Then("deberia ver un mensaje de error de direccion requerida")
    public void deberia_ver_un_mensaje_de_error_de_direccion_requerida() {
        Assertions.assertTrue(checkoutPage.isDireccionErrorDisplayed(),
                "No se mostró el error de dirección requerida");
    }
}