package programacion.eCommerceApp.unitary.stateTransition;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import programacion.eCommerceApp.controller.response.PedidoResponse;
import programacion.eCommerceApp.model.Pedido;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IPedidoRepository;
import programacion.eCommerceApp.service.PedidoService;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
SE PRUEBA:
    1-Eliminar pedido existente.
    2-Recuperar pedido eliminado.
    3-Recuperar pedido disponible
    4-Eliminar pedido eliminadao
*/
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class) //para que JUnit5 sepa que debe usar Mockito
public class CambiarEstadoPedidoTest {

    @Mock //crea un mock falso (repositorio)
    private IPedidoRepository iPedidoRepository;

    @InjectMocks //crea una instancia reall de PedidoService y inyecta los mocks q necesita
    private PedidoService pedidoService;

    private Pedido pedidoMock;
    private Integer idPrueba;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        idPrueba = 2;
        Usuario usuario = new Usuario();
        pedidoMock = Pedido.builder()
                .id(idPrueba)
                .horaPedido("13:15")
                .fechaPedido("2/05/2024")
                .usuario(usuario)
                .detallesPedido(new ArrayList<>())
                .total(125.2)
                .estado(Pedido.COMUN)
                .build();
    }

    @Test
    public void EliminarPedidoExistente(){
        //Given
        when(iPedidoRepository.findById(idPrueba)).thenReturn(Optional.of(pedidoMock));//simulo el comportamiento del repositorio y devuelvo el Optional con el pedidoMock
        when(iPedidoRepository.save(pedidoMock)).thenReturn(pedidoMock); //simula que al guardar el pedido con save devuelva el mismo pedido

        PedidoResponse response = pedidoService.eliminar(idPrueba);

        assertNotNull(response);
        assertEquals(Pedido.ELIMINADO, response.estado());

        verify(iPedidoRepository).findById(idPrueba);
        verify(iPedidoRepository).save(pedidoMock);
    }

    @Test
    public void RecuperarPedidoEliminado(){
        //Given
        pedidoMock.setEstado(Pedido.ELIMINADO);
        when(iPedidoRepository.findById(idPrueba)).thenReturn(Optional.of(pedidoMock));
        when(iPedidoRepository.save(pedidoMock)).thenReturn(pedidoMock);

        PedidoResponse response =  pedidoService.recuperar(idPrueba);

        assertNotNull(response);
        assertEquals(Pedido.COMUN, pedidoMock.getEstado());
        assertEquals(idPrueba, response.id());

        verify(iPedidoRepository).findById(idPrueba);
        verify(iPedidoRepository).save(pedidoMock);
    }

    @Test
    public void RecuperarPedidoDisponible(){
        pedidoMock.setEstado(Pedido.COMUN);
        when(iPedidoRepository.findById(idPrueba)).thenReturn(Optional.of(pedidoMock));

        IllegalArgumentException exception= assertThrows(IllegalArgumentException.class,
                () -> pedidoService.recuperar(idPrueba));

        assertEquals("NO SE ENCONTRO EL PEDIDO CON ID: "+ idPrueba, exception.getMessage());

        verify(iPedidoRepository).findById(idPrueba);
        verify(iPedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    public void EliminarPedidoEliminado(){
        pedidoMock.setEstado(Pedido.ELIMINADO);
        when(iPedidoRepository.findById(idPrueba)).thenReturn(Optional.of(pedidoMock));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pedidoService.eliminar(idPrueba));

        assertEquals("EL PEDIDO YA FUE ELIMINADO", exception.getMessage());

        verify(iPedidoRepository).findById(idPrueba);
        verify(iPedidoRepository, never()).save(any(Pedido.class));
        //never() --> quiere decir que no se espera ninguna llamada al metodo save()
    }
}
