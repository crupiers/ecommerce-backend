package programacion.eCommerceApp.unitary.stateTransition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.repository.IMarcaRepository;
import programacion.eCommerceApp.service.MarcaService;

@ExtendWith(MockitoExtension.class)
public class CambiarEstadoMarcaTest {

    @Mock
    private IMarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    private Integer idPrueba;
    private Marca marcaMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        idPrueba = 2;
        marcaMock = Marca.builder()
            .id(idPrueba)
            .nombre("Marca de prueba")
            .estado(Marca.COMUN)
            .build();
    }

    @Test
    public void eliminarMarcaExistente() {
        // given
        when(marcaRepository.findById(idPrueba)).thenReturn(Optional.of(marcaMock));
        when(marcaRepository.save(marcaMock)).thenReturn(marcaMock);

        // when
        ResponseEntity<Void> response = marcaService.eliminar(marcaMock.getId());

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Marca.ELIMINADO, marcaMock.getEstado());

        verify(marcaRepository, times(1)).findById(idPrueba);
        verify(marcaRepository, times(1)).save(marcaMock);
    }

    @Test
    public void testEliminarMarcaNoExistente() {
        // given
        when(marcaRepository.findById(idPrueba)).thenReturn(Optional.empty());

        // when
        assertThrows(ResponseStatusException.class, () -> marcaService.eliminar(idPrueba));
    }

    @Test
    public void testEliminarMarcaELIMINADO() {
        // given
        marcaMock.setEstado(Marca.ELIMINADO);
        when(marcaRepository.findById(idPrueba)).thenReturn(Optional.of(marcaMock));

        // when
        assertThrows(ResponseStatusException.class, () -> marcaService.eliminar(idPrueba));
    }

    @Test
    public void testRecuperarMarcaELIMINADO() {
        // given
        marcaMock.setEstado(Marca.ELIMINADO);
        given(marcaRepository.findById(idPrueba)).willReturn(Optional.of(marcaMock));

        // when
        ResponseEntity<Void> responseEntity = marcaService.recuperar(idPrueba);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Marca.COMUN, marcaMock.getEstado());
        verify(marcaRepository, times(1)).findById(idPrueba);
        verify(marcaRepository, times(1)).save(marcaMock);
    }

    @Test
    public void testRecuperarMarcaNoExistente() {
        // given
        when(marcaRepository.findById(idPrueba)).thenReturn(Optional.empty());

        // when
        assertThrows(ResponseStatusException.class, () -> marcaService.recuperar(idPrueba));
    }

    @Test
    public void testRecuperarMarcaNoELIMINADO() {
        // given
        when(marcaRepository.findById(idPrueba)).thenReturn(Optional.of(marcaMock));

        // when
        assertThrows(ResponseStatusException.class, () -> marcaService.recuperar(idPrueba));
    }
}
