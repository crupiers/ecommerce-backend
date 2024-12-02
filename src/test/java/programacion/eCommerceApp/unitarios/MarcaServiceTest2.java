package programacion.eCommerceApp.unitarios;

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


@ExtendWith(MockitoExtension.class)
public class GuararMarcaServiceTest {

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
        NewMarcaRequest request = new NewMarcaRequest("Marca Nueva", "Observaciones");
        Marca marca = MarcaMapper.toEntity(request);
        //when
        when(marcaRepository.save(marca)).thenReturn(marca);

        MarcaResponse response = marcaService.crear(request);
        //then
        assertNotNull(response);
        assertEquals(request.denominacion(), response.denominacion());
        assertEquals(request.observaciones(), response.observaciones());
        verify(marcaRepository, times(1)).findByDenominacion(marca.getDenominacion());
        verify(marcaRepository, times(1)).save(marca);
    }

    @Test
    void testCrearMarcaExistenteNoEliminada() {
        NewMarcaRequest request = new NewMarcaRequest("Marca Existente", "Observaciones");
        Marca marcaExistente = MarcaMapper.toEntity(request);
        marcaExistente.setEstado(Marca.COMUN);

        when(marcaRepository.findByDenominacion(marcaExistente.getDenominacion())).thenReturn(Optional.of(marcaExistente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.crear(request);
        });
        assertNotNull(request);
        assertEquals("La marca ya existe", exception.getMessage());
        verify(marcaRepository, times(1)).findByDenominacion(marcaExistente.getDenominacion());
        verify(marcaRepository, never()).save(any(Marca.class));
    }

    @Test
    void testCrearMarcaExistenteEliminada() {
        NewMarcaRequest request = new NewMarcaRequest("Marca Eliminada", "Observaciones");
        Marca marcaExistente = MarcaMapper.toEntity(request);
        marcaExistente.setEstado(Marca.ELIMINADO);

        when(marcaRepository.findByDenominacion(marcaExistente.getDenominacion())).thenReturn(Optional.of(marcaExistente));
        when(marcaRepository.save(marcaExistente)).thenReturn(marcaExistente);

        MarcaResponse response = marcaService.crear(request);

        assertNotNull(response);
        assertEquals(request.denominacion(), response.denominacion());
        assertEquals(request.observaciones(), response.observaciones());
        assertEquals(Marca.COMUN, response.estado());
        verify(marcaRepository, times(1)).findByDenominacion(marcaExistente.getDenominacion());
        verify(marcaRepository, times(1)).save(marcaExistente);
    }
}
