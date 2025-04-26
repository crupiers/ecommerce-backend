package programacion.eCommerceApp.integration.stateTransition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.repository.IMarcaRepository;

import static org.junit.Assert.assertEquals;

/*
se prueba:
-"Disponible" a "Eliminado"
-"Eliminado" a "Disponible"
- Recuperar una marca inexistente
-Eliminar una marca inexistente
-Eliminar una marca que ya está eliminada


*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class CambiarEstadoMarcaTest {
    private Integer idMarcaPrueba;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private IMarcaRepository iMarcaRepository;

    Marca marca;

    @BeforeEach
    public void setup() {
        iMarcaRepository.deleteAll(); //limpiar base de datos

        marca = Marca.builder()
                .nombre("Marca de prueba")
                .descripcion("Descripcion de prueba")
                .estado(Marca.COMUN)
                .build();
        marca = iMarcaRepository.save(marca);
        this.idMarcaPrueba = marca.getId();
    }
    @Test
    public void CambiarDisponibleAEliminado() throws Exception {
        // Given
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/marcas/" + idMarcaPrueba))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Solo status 200, no hay mensaje

        Marca marcaActualizada = iMarcaRepository.findById(idMarcaPrueba).get();
        assertEquals(Marca.ELIMINADO, marcaActualizada.getEstado());
    }

    @Test
    public void CambiarEliminadoADisponible() throws Exception {
        // Given
        marca.setEstado(Marca.ELIMINADO);
        iMarcaRepository.save(marca);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ecommerce/marcas/recuperar/" + idMarcaPrueba))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Solo status 200, no hay mensaje

        // Then
        Marca marcaActualizada = iMarcaRepository.findById(idMarcaPrueba).get();
        assertEquals(Marca.COMUN, marcaActualizada.getEstado());
    }

    @Test
    public void RecuperarMarcaInexistente() throws Exception {
        // Given
        Integer idMarcaInexistente = 9999;

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ecommerce/marcas/recuperar/" + idMarcaInexistente))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Then
        Marca marcaActualizada = iMarcaRepository.findById(idMarcaInexistente).orElse(null);
        assertEquals(null, marcaActualizada);
    }

    @Test
    public void EliminarMarcaInexistente() throws Exception {
        // Given
        Integer idMarcaInexistente = 9999;

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/marcas/" + idMarcaInexistente))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Then
        Marca marcaActualizada = iMarcaRepository.findById(idMarcaInexistente).orElse(null);
        assertEquals(null, marcaActualizada);
    }

    @Test
    public void EliminarMarcaEliminada() throws Exception {
        // Given
        marca.setEstado(Marca.ELIMINADO);
        iMarcaRepository.save(marca);

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/marcas/" + idMarcaPrueba))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Then
        Marca marcaActualizada = iMarcaRepository.findById(idMarcaPrueba).orElse(null);
        assertEquals(Marca.ELIMINADO, marcaActualizada.getEstado());
    }


}


