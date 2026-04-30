package edu.pe.cibertec.shooping.steps;

import edu.pe.cibertec.shooping.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class LoginSteps {

    private LoginPage loginPage;

    public LoginSteps() {
        this.loginPage = new LoginPage();
    }

    @Given("que el usuario esta en la pantalla de login")
    public void que_el_usuario_esta_en_la_pantalla_de_login() {

        Assertions.assertTrue(loginPage.isLoginScreenDisplayed(),
                "No se encuentra en la pantalla de login");
    }

    @When("ingresa credenciales validas")
    public void ingresa_credenciales_validas() {
        loginPage.loginWithValidCredentials("user1@test.com", "password1");
    }

    @When("ingresa credenciales invalidas")
    public void ingresa_credenciales_invalidas() {
        loginPage.loginWithInvalidCredentials("invalido@test.com", "123456");
    }

    @Then("deberia acceder a la pantalla principal")
    public void deberia_acceder_a_la_pantalla_principal() {
        Assertions.assertTrue(loginPage.isHomeScreenDisplayed(),
                "No se mostró la pantalla principal (Inicio) tras login exitoso");
    }

    @Then("deberia ver un mensaje de error")
    public void deberia_ver_un_mensaje_de_error() {
        Assertions.assertTrue(loginPage.isLoginErrorMessageDisplayed(),
                "No se mostró el mensaje de error en login fallido");
    }
}