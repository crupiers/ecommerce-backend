package programacion.eCommerceApp.integration.stateTransition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;
import programacion.eCommerceApp.mapper.TamanioMapper;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.ITamanioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class CambiarEstadoTamanioTest {
    private Integer idTamanioPrueba;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ITamanioRepository iTamanioRepository;
    Tamanio tamanio;
    @BeforeEach
    public void setup() {
        iTamanioRepository.deleteAll();

        tamanio = Tamanio.builder()
                .nombre("Tamanio de prueba")
                .descripcion("Descripcion de prueba")
                .estado(Tamanio.COMUN)
                .build();

        tamanio = iTamanioRepository.save(tamanio);
        this.idTamanioPrueba = tamanio.getId();
    }

    @Test
    public void cambiarDisponibleAEliminado() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/tamanios/" + idTamanioPrueba))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Tamanio tamanioActualizado = iTamanioRepository.findById(idTamanioPrueba).get();
        assertEquals(Tamanio.ELIMINADO, tamanioActualizado.getEstado());
    }

    @Test
    public void cambiarEliminadoADisponible() throws Exception {
        tamanio.setEstado(Tamanio.ELIMINADO);
        iTamanioRepository.save(tamanio);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ecommerce/tamanios/recuperar/" + idTamanioPrueba))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Tamanio tamanioActualizado = iTamanioRepository.findById(idTamanioPrueba).get();
        assertEquals(Tamanio.COMUN, tamanioActualizado.getEstado());
    }

    @Test
    public void recuperarTamanioInexistente() throws Exception {
        Integer idInexistente = 9999;

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ecommerce/tamanios/recuperar/" + idInexistente))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Tamanio tamanioRecuperado = iTamanioRepository.findById(idInexistente).orElse(null);
        assertEquals(null, tamanioRecuperado);
    }

    @Test
    public void eliminarTamanioInexistente() throws Exception {
        Integer idInexistente = 9999;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/tamanios/" + idInexistente))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Tamanio tamanioRecuperado = iTamanioRepository.findById(idInexistente).orElse(null);
        assertEquals(null, tamanioRecuperado);
    }

    @Test
    public void eliminarTamanioYaEliminado() throws Exception {
        tamanio.setEstado(Tamanio.ELIMINADO);
        iTamanioRepository.save(tamanio);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/tamanios/" + idTamanioPrueba))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Tamanio tamanioRecuperado = iTamanioRepository.findById(idTamanioPrueba).get();
        assertEquals(Tamanio.ELIMINADO, tamanioRecuperado.getEstado());
    }
}