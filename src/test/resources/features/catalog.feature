@catalogo
Feature: Catalogo de productos

  Scenario: Ver lista de productos
    Given que el usuario esta logueado en la aplicacion
    When navega al catalogo de productos
    Then deberia ver la lista de productos disponibles

  Scenario: Buscar producto por nombre
    Given que el usuario esta en el catalogo
    When busca el producto "Laptop"
    Then deberia ver productos que contengan "Laptop"

  Scenario: Filtrar productos por categoria Electronica
    Given que el usuario esta en el catalogo
    When filtra por la categoria "Electrónica"
    Then deberia ver solo productos de la categoria "Electrónica"