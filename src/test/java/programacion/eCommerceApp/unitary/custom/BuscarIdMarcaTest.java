package programacion.eCommerceApp.unitary.custom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.mapper.MarcaMapper;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.repository.IMarcaRepository;
import programacion.eCommerceApp.service.MarcaService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BuscarIdMarcaTest {

    @Mock
    private IMarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    private Integer idPrueba;
    private Marca marcaMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        idPrueba = 1;
        marcaMock = Marca.builder()
            .id(idPrueba)
            .nombre("Marca de prueba")
            .estado(Marca.COMUN)
            .build();
    }

    @Test
    void testBuscarPorIdEncontradoyNoNull() {
        // given
        given(marcaRepository.findById(idPrueba)).willReturn(Optional.of(marcaMock));
        MarcaResponse marcaResponse = MarcaMapper.toMarcaResponse(marcaMock);

        // when
        ResponseEntity<MarcaResponse> responseEntity = marcaService.buscarPorId(idPrueba);

        // then
        MarcaResponse marcaBuscada = responseEntity.getBody();
        assertNotNull(marcaBuscada);

        assertAll("marca",
            () -> assertEquals(marcaResponse.id(), marcaBuscada.id()),
            () -> assertEquals(marcaResponse.nombre(), marcaBuscada.nombre())
        );

        verify(marcaRepository, times(1)).findById(idPrueba);
    }

    @Test
    void testBuscarPorIdEncontradoEliminado() {
        // given
        marcaMock.setEstado(Marca.ELIMINADO);
        given(marcaRepository.findById(idPrueba)).willReturn(Optional.of(marcaMock));

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.buscarPorId(idPrueba);
        });

        assertEquals("NO SE ENCONTRÓ LA MARCA CON ID: 1", exception.getMessage());
        verify(marcaRepository, times(1)).findById(idPrueba);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        // given
        int idPrueba = 40;
        given(marcaRepository.findById(idPrueba)).willReturn(Optional.empty());

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            marcaService.buscarPorId(idPrueba);
        });

        assertEquals("NO SE ENCONTRÓ LA MARCA CON ID: 40", exception.getMessage());
        verify(marcaRepository, times(1)).findById(idPrueba);
    }
}
