package programacion.eCommerceApp.unitary.limitValues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.mapper.MarcaMapper;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.repository.IMarcaRepository;
import programacion.eCommerceApp.service.MarcaService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*esta clase verifica que cuando se cree una marca nueva, se guarde correctamente y que no se guarde si ya existe. 
Lanza una IllegalArgumentException.
*/
@ExtendWith(MockitoExtension.class)
public class GuardarMarcaEstadoTest {

    @Mock
    private IMarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearMarcaNueva() {
        //given
        NewMarcaRequest request = new NewMarcaRequest("Marca Nueva", "descripcion");
        Marca marca = MarcaMapper.toEntity(request);
        //when
        when(marcaRepository.save(marca)).thenReturn(marca);

        MarcaResponse response = marcaService.crear(request);
        //then
        assertNotNull(response);
        assertEquals(request.nombre(), response.nombre());
        assertEquals(request.descripcion(), response.descripcion());
        verify(marcaRepository, times(1)).findByNombre(marca.getNombre());
        verify(marcaRepository, times(1)).save(marca);
    }

    @Test
    void testCrearMarcaExistenteNoEliminada() {
        NewMarcaRequest request = new NewMarcaRequest("Marca Existente", "descripcion");
        Marca marcaExistente = MarcaMapper.toEntity(request);
        marcaExistente.setEstado(Marca.COMUN);

        when(marcaRepository.findByNombre(marcaExistente.getNombre())).thenReturn(Optional.of(marcaExistente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.crear(request);
        });
        assertNotNull(request);
        assertEquals("La marca ya existe", exception.getMessage());
        verify(marcaRepository, times(1)).findByNombre(marcaExistente.getNombre());
        verify(marcaRepository, never()).save(any(Marca.class));
    }

    @Test
    void testCrearMarcaExistenteEliminada() {
        NewMarcaRequest request = new NewMarcaRequest("Marca Eliminada", "descripcion");
        Marca marcaExistente = MarcaMapper.toEntity(request);
        marcaExistente.setEstado(Marca.ELIMINADO);

        when(marcaRepository.findByNombre(marcaExistente.getNombre())).thenReturn(Optional.of(marcaExistente));
        when(marcaRepository.save(marcaExistente)).thenReturn(marcaExistente);

        MarcaResponse response = marcaService.crear(request);
        
        assertNotNull(response, "La response de la creación de la marca no debe ser nula");
        assertEquals(request.nombre(), response.nombre(), "La denominación de la marca debe ser la misma que la de la request");
        assertEquals(request.descripcion(), response.descripcion(), "Las descripcion de la marca deben ser las mismas que las de la request");
        assertEquals(Marca.COMUN, response.estado(), "La marca debe estar creada");
        verify(marcaRepository, times(1)).findByNombre(marcaExistente.getNombre());
        verify(marcaRepository, times(1)).save(marcaExistente);
    }
}
