package edu.pe.cibertec.shooping.steps;

import edu.pe.cibertec.shooping.pages.LoginPage;
import edu.pe.cibertec.shooping.pages.ProfilePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class LogoutSteps {

    private ProfilePage profilePage;
    private LoginPage loginPage;

    public LogoutSteps() {
        this.profilePage = new ProfilePage();
        this.loginPage = new LoginPage();
    }



    @When("hace clic en el menu de usuario")
    public void hace_clic_en_el_menu_de_usuario() {

        if (loginPage.isLoginScreenDisplayed()) {
            loginPage.loginWithValidCredentials("user1@test.com", "password1");
        }
        profilePage.goToProfile();
    }

    @And("hace clic en cerrar sesion")
    public void hace_clic_en_cerrar_sesion() {
        String email = profilePage.getCurrentUserEmail();
        Assertions.assertEquals("user1@test.com", email,
                "El email del usuario no coincide con la sesión activa");
        profilePage.clickLogout();
    }

    @Then("deberia regresar a la pantalla de login")
    public void deberia_regresar_a_la_pantalla_de_login() {
        Assertions.assertTrue(profilePage.isLoginScreenDisplayed(),
                "No se regresó a la pantalla de login después del logout");
    }
}