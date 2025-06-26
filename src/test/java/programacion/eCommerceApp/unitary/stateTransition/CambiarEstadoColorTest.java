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

import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.repository.IColorRepository;
import programacion.eCommerceApp.service.ColorService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CambiarEstadoColorTest {

    @Mock
    private IColorRepository colorRepository;

    @InjectMocks
    private ColorService colorService;

    private Integer idPrueba;
    private Color colorMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        idPrueba = 2;
        colorMock = Color.builder()
            .id(idPrueba)
            .nombre("Color de prueba")
            .estado(Color.COMUN)
            .build();
    }

    @Test
    public void eliminarColorExistente() {
        // given
        when(colorRepository.findById(idPrueba)).thenReturn(Optional.of(colorMock));
        when(colorRepository.save(colorMock)).thenReturn(colorMock);

        // when
        ResponseEntity<Void> response = colorService.eliminar(colorMock.getId());

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Color.ELIMINADO, colorMock.getEstado());

        verify(colorRepository, times(1)).findById(idPrueba);
        verify(colorRepository, times(1)).save(colorMock);
    }

    @Test
    public void testEliminarColorNoExistente() {
        // given
        when(colorRepository.findById(idPrueba)).thenReturn(Optional.empty());

        // when
        assertThrows(ResponseStatusException.class, () -> colorService.eliminar(idPrueba));
    }

    @Test
    public void testEliminarColorEliminado() {
        // given
        colorMock.setEstado(Color.ELIMINADO);
        when(colorRepository.findById(idPrueba)).thenReturn(Optional.of(colorMock));

        // when
        assertThrows(ResponseStatusException.class, () -> colorService.eliminar(idPrueba));
    }

    @Test
    public void testRecuperarColorEliminado() {
        // given
        colorMock.setEstado(Color.ELIMINADO);
        given(colorRepository.findById(idPrueba)).willReturn(Optional.of(colorMock));

        // when
        ResponseEntity<Void> responseEntity = colorService.recuperar(idPrueba);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Color.COMUN, colorMock.getEstado());
        verify(colorRepository, times(1)).findById(idPrueba);
        verify(colorRepository, times(1)).save(colorMock);
    }

    @Test
    public void testRecuperarColorNoExistente() {
        // given
        when(colorRepository.findById(idPrueba)).thenReturn(Optional.empty());

        // when
        assertThrows(ResponseStatusException.class, () -> colorService.recuperar(idPrueba));
    }

    @Test
    public void testRecuperarColorNoEliminado() {
        // given
        when(colorRepository.findById(idPrueba)).thenReturn(Optional.of(colorMock));

        // when
        assertThrows(ResponseStatusException.class, () -> colorService.recuperar(idPrueba));
    }
}
