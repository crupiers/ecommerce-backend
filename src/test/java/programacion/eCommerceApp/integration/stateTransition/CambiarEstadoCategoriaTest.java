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
import programacion.eCommerceApp.mapper.CategoriaMapper;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.repository.ICategoriaRepository;

import static org.junit.Assert.assertEquals;
/*
se prueba:
-"Disponible" a "Eliminado"
-"Eliminado" a "Disponible"
- Recuperar una categoría inexistente
-Eliminar una categoría inexistente
-Eliminar una categoría que ya está eliminada


*/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class CambiarEstadoCategoriaTest {
    private Integer idCategoriaPrueba;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ICategoriaRepository iCategoriaRepository;
    Categoria categoria;
    @BeforeEach
    public void setup(){
       iCategoriaRepository.deleteAll(); //limpiar base de datos

        categoria = Categoria.builder()
                .nombre("Categoria de prueba")
                .descripcion("Descripcion de prueba")
                .estado(Categoria.COMUN)
                .build();
        categoria = iCategoriaRepository.save(categoria);
        this.idCategoriaPrueba = categoria.getId();
    }
    @Test
    public void CambiarDisponibleAEliminado() throws Exception{
        // Given
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/categorias/"+idCategoriaPrueba))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Solo status 200, no hay mensaje

        // Then
        Categoria categoriaActualizada = iCategoriaRepository.findById(idCategoriaPrueba).get();
        assertEquals(Categoria.ELIMINADO, categoriaActualizada.getEstado());
    }

    @Test
    public void CambiarEliminadoADisponible() throws Exception{
        //Elimino la categoria
        categoria.setEstado(Categoria.ELIMINADO);
        iCategoriaRepository.save(categoria);

        //Recupero la categoria
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ecommerce/categorias/recuperar/"+idCategoriaPrueba))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Solo status 200, no hay mensaje

        Categoria categoriaActualizada = iCategoriaRepository.findById(idCategoriaPrueba).get();
        assertEquals(Categoria.COMUN, categoriaActualizada.getEstado());
    }

    @Test
    public void RecuperarCategoriaInexistente() throws Exception{
        Integer idCategoriaInexistente = 9999;
        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ecommerce/categorias/recuperar/"+idCategoriaInexistente))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Espera un error 400 Bad Request
        Categoria categoriaRecuperada = iCategoriaRepository.findById(idCategoriaInexistente).orElse(null);
        assertEquals(null, categoriaRecuperada);
    }

    @Test
    public void EliminarCategoriaInexistente() throws Exception {
        Integer idCategoriaInexistente = 9999;
        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/categorias/"+idCategoriaInexistente))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Espera un error 400 Bad Request

        Categoria categoriaRecuperada = iCategoriaRepository.findById(idCategoriaInexistente).orElse(null);
        assertEquals(null, categoriaRecuperada);
    }

    @Test
    public void EliminarCategoriaEliminada() throws Exception {
        categoria.setEstado(Categoria.ELIMINADO);
        iCategoriaRepository.save(categoria);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ecommerce/categorias/"+idCategoriaPrueba))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Espera un error 400 Bad Request

        Categoria categoriaRecuperada = iCategoriaRepository.findById(idCategoriaPrueba).get();
        assertEquals(Categoria.ELIMINADO, categoriaRecuperada.getEstado());
    }
}



