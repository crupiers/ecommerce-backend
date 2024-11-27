package programacion.eCommerceApp.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.model.Marca;

@SpringBootTest
public class MarcaServiceTest {
    
    @Autowired
    private MarcaService marcaService;
    
    @MockBean
    private MarcaRepository marcaRepository;

    @Test
public void testGuardarMarca() {
    // Crear un objeto Marca y establecer su nombre
    Marca marca = new Marca();
    marca.setDenominacion("Nueva Marca");

    NewMarcaRequest newMarcaRequest = new NewMarcaRequest("marca", "observaciones");

    // Configurar el comportamiento del mock del repositorio
    when(marcaRepository.save(any(Marca.class))).thenReturn(marca);

    // Llamar al método que se está probando
    newMarcaRequest resultado = marcaService.crear(newMarcaRequest);

    // Verificar que el resultado no sea nulo
    assertNotNull(resultado);

    // Verificar que el nombre de la marca guardada sea el esperado
    assertEquals("Nueva Marca", resultado.getDenominacion());
}
 
}
