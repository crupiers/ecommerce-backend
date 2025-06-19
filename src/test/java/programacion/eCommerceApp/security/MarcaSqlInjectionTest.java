package programacion.eCommerceApp.security;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.repository.IMarcaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@SpringBootTest
@AutoConfigureMockMvc
public class MarcaSqlInjectionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMarcaRepository marcaRepository;

    @Before
    public void setUp() {
        //marcaRepository.deleteAll();
        marcaRepository.save(Marca.builder()
                .nombre("Kappa")
                .descripcion("Marca deportiva")
                .estado(Marca.COMUN)
                .build());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // Add this annotation
    public void testInyeccionSQLAlCrearMarca() throws Exception {
        String payload = """
        {
            "nombre": "Kappa' OR '1'='1",
            "descripcion": "Intento de inyeccion SQL"
        }
    """;

        mockMvc.perform(post("/ecommerce/admin/marcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
                //.andExpect(jsonPath("$.nombre").value("Kappa' OR '1'='1"));
    }

    @Test
    public void testNoDevuelveMarcaExistenteConInyeccion() throws Exception {
        // Suponemos que el servicio no debería encontrar "Kappa" si le mandan "Kappa' OR '1'='1"
        Optional<Marca> resultado = marcaRepository.findByNombre("Kappa' OR '1'='1");
        assertTrue(resultado.isEmpty(), "No debería encontrar ninguna marca");
    }
}
