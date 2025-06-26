package programacion.eCommerceApp.integration.stateTransition;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.repository.IColorRepository;

import static org.junit.Assert.assertEquals;

/*
se prueba:
-"Disponible" a "Eliminado"
-"Eliminado" a "Disponible"
- Recuperar una color inexistente
-Eliminar una color inexistente
-Eliminar una color que ya está eliminada

*/
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class CambiarEstadoColorTest {

    private Integer idColorPrueba;
    @Autowired
    private IColorRepository iColorRepository;

    @Autowired
    private MockMvc mockMvc;

    Color color;

    @BeforeEach
    public void setup() {
        iColorRepository.deleteAll(); // Limpiar base de datos

        color = Color.builder()
                .nombre("Rojo")
                .descripcion("--")
                .estado(Color.COMUN)
                .build();

        Color colorActualizado = iColorRepository.save(color);
        this.idColorPrueba = colorActualizado.getId();
    }
    @Test
    public void CambiarDisponibleAEliminado() throws Exception {
        // Given
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/colores/" + idColorPrueba))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Solo status 200, no hay mensaje

        Color colorActualizado = iColorRepository.findById(idColorPrueba).get();
        assertEquals(Color.ELIMINADO, colorActualizado.getEstado());
    }

    @Test
    public void CambiarEliminadoADisponible() throws Exception {
        // Given
        color.setEstado(Color.ELIMINADO);
        iColorRepository.save(color);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ecommerce/colores/recuperar/" + idColorPrueba))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Solo status 200, no hay mensaje

        Color colorActualizado = iColorRepository.findById(idColorPrueba).get();
        assertEquals(Color.COMUN, colorActualizado.getEstado());
    }

    @Test
    public void RecuperarColorInexistente() throws Exception {
        // Given
        Integer idColorINexistente = 9999;
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ecommerce/colores/recuperar/" + idColorINexistente))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Color colorActualizado = iColorRepository.findById(idColorINexistente).orElse(null);
        assertEquals(null, colorActualizado);
    }

    @Test
    public void EliminarColorInexistente() throws Exception {
        // Given
        Integer idColorINexistente = 9999;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/colores/" + idColorINexistente))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Color colorActualizado = iColorRepository.findById(idColorINexistente).orElse(null);
        assertEquals(null, colorActualizado);
    }

    @Test
    public void EliminarColorYaEliminado() throws Exception {
        // Given
        color.setEstado(Color.ELIMINADO);
        iColorRepository.save(color);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/colores/" + idColorPrueba))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Color colorActualizado = iColorRepository.findById(idColorPrueba).get();
        assertEquals(Color.ELIMINADO, colorActualizado.getEstado());
    }

}

