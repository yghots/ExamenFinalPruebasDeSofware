@login
Feature: Inicio de sesion

  Scenario: Login exitoso
    Given que el usuario esta en la pantalla de login
    When ingresa credenciales validas
    Then deberia acceder a la pantalla principal

  Scenario: Login fallido
    Given que el usuario esta en la pantalla de login
    When ingresa credenciales invalidas
    Then deberia ver un mensaje de error