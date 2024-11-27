Feature: Creación de producto

  Scenario: Crear producto con precio negativo
    Given un producto con precio -50.0
    When intento crear el producto
    Then el sistema debe devolver un error indicando que el precio debe ser mayor que cero

  Scenario: Crear producto con precio mayor a cero
    Given un producto con precio 1.0
    When intento crear el producto
    Then el producto debe ser creado exitosamente
