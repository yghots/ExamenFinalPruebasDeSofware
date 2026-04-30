package edu.pe.cibertec.shooping.steps;

import edu.pe.cibertec.shooping.hooks.AppiumHooks;
import edu.pe.cibertec.shooping.pages.ScreenPlayCatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

public class CatalogSteps {

    private ScreenPlayCatalogPage catalogPage;

    public CatalogSteps() {
        this.catalogPage = new ScreenPlayCatalogPage();
    }

    @Given("que el usuario esta logueado en la aplicacion")
    public void que_el_usuario_esta_logueado() {
        if (catalogPage.isLoginScreenVisible()) {
            catalogPage.login();
        }
        boolean inicioVisible = AppiumHooks.getDriver()
                .findElement(By.xpath("//android.widget.TextView[@text=\"Inicio\"]"))
                .isDisplayed();
        Assertions.assertTrue(inicioVisible, "El usuario no ha iniciado sesión correctamente");
    }

    @Given("que el usuario esta en el catalogo")
    public void que_el_usuario_esta_en_el_catalogo() {
        // Si la app reinició, login automático antes de ir al catálogo
        if (catalogPage.isLoginScreenVisible()) {
            catalogPage.login();
        }
        catalogPage.navigateToCatalog();
    }

    @When("navega al catalogo de productos")
    public void navega_al_catalogo_de_productos() {
        // Este paso no necesita login porque es llamado después del Given
        catalogPage.navigateToCatalog();
    }

    @When("busca el producto {string}")
    public void busca_el_producto(String producto) {
        catalogPage.searchProduct(producto);
    }

    @When("filtra por la categoria {string}")
    public void filtra_por_la_categoria(String categoria) {
        catalogPage.filterByCategory(categoria);
    }

    @Then("deberia ver la lista de productos disponibles")
    public void deberia_ver_la_lista_de_productos_disponibles() {
        Assertions.assertTrue(catalogPage.isProductListDisplayed(),
                "La lista de productos no se muestra");
    }

    @Then("deberia ver productos que contengan {string}")
    public void deberia_ver_productos_que_contengan(String texto) {
        Assertions.assertTrue(catalogPage.isProductContainingTextVisible(texto),
                "No se encontró ningún producto que contenga: " + texto);
    }

    @Then("deberia ver solo productos de la categoria {string}")
    public void deberia_ver_solo_productos_de_la_categoria(String categoria) {
        Assertions.assertTrue(catalogPage.isCategoryFilterActive(categoria),
                "El filtro de categoría '" + categoria + "' no está activo");
        Assertions.assertTrue(catalogPage.isProductListDisplayed(),
                "No hay productos visibles para la categoría " + categoria);
    }
}