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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.ITamanioRepository;
import programacion.eCommerceApp.service.TamanioService;
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CambiarEstadoTamanioTest {

    @Mock
    private ITamanioRepository tamanioRepository;

    @InjectMocks
    private TamanioService tamanioService;

    private Integer idPrueba;
    private Tamanio tamanioMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        idPrueba = 2;
        tamanioMock = Tamanio.builder()
            .id(idPrueba)
            .nombre("Tamaño de prueba")
            .estado(Tamanio.COMUN)
            .build();
    }

    @Test
    public void eliminarTamanioExistente() {
        // given
        when(tamanioRepository.findById(idPrueba)).thenReturn(Optional.of(tamanioMock));
        when(tamanioRepository.save(tamanioMock)).thenReturn(tamanioMock);

        // when
        ResponseEntity<Void> response = tamanioService.eliminar(tamanioMock.getId());

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Tamanio.ELIMINADO, tamanioMock.getEstado());

        verify(tamanioRepository, times(1)).findById(idPrueba);
        verify(tamanioRepository, times(1)).save(tamanioMock);
    }

    @Test
    public void testEliminarTamanioNoExistente() {
        // given
        when(tamanioRepository.findById(idPrueba)).thenReturn(Optional.empty());

        // when
        assertThrows(ResponseStatusException.class, () -> tamanioService.eliminar(idPrueba));
    }

    @Test
    public void testEliminarTamanioEliminado() {
        // given
        tamanioMock.setEstado(Tamanio.ELIMINADO);
        when(tamanioRepository.findById(idPrueba)).thenReturn(Optional.of(tamanioMock));

        // when
        assertThrows(ResponseStatusException.class, () -> tamanioService.eliminar(idPrueba));
    }

    @Test
    public void testRecuperarTamanioEliminado() {
        // given
        tamanioMock.setEstado(Tamanio.ELIMINADO);
        given(tamanioRepository.findById(idPrueba)).willReturn(Optional.of(tamanioMock));

        // when
        ResponseEntity<Void> responseEntity = tamanioService.recuperar(idPrueba);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Tamanio.COMUN, tamanioMock.getEstado());
        verify(tamanioRepository, times(1)).findById(idPrueba);
        verify(tamanioRepository, times(1)).save(tamanioMock);
    }

    @Test
    public void testRecuperarTamanioNoExistente() {
        // given
        when(tamanioRepository.findById(idPrueba)).thenReturn(Optional.empty());

        // when
        assertThrows(ResponseStatusException.class, () -> tamanioService.recuperar(idPrueba));
    }

    @Test
    public void testRecuperarTamanioNoEliminado() {
        // given
        when(tamanioRepository.findById(idPrueba)).thenReturn(Optional.of(tamanioMock));

        // when
        assertThrows(ResponseStatusException.class, () -> tamanioService.recuperar(idPrueba));
    }
}
