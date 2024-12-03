package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewPedidoRequest;
import programacion.eCommerceApp.controller.response.PedidoResponse;
import programacion.eCommerceApp.mapper.PedidoMapper;
import programacion.eCommerceApp.model.DetallePedido;
import programacion.eCommerceApp.model.Pedido;
import programacion.eCommerceApp.model.Usuario;
import programacion.eCommerceApp.repository.IDetallePedidoRepository;
import programacion.eCommerceApp.repository.IPedidoRepository;
import programacion.eCommerceApp.repository.IUsuarioRepository;

import java.util.List;

@Service
public class PedidoService implements IPedidoService {

    @Autowired
    IDetallePedidoRepository detallePedidoRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IPedidoRepository pedidoRepository;

    public PedidoResponse crear(NewPedidoRequest newPedidoRequest) {

        List<DetallePedido> detallesPedido = newPedidoRequest.idDetallesPedido().stream().map(idDetallePedido -> {
            return detallePedidoRepository.findById(idDetallePedido)
                .orElseThrow(() -> new IllegalArgumentException("Detalle pedido no encontrado con ID: " + idDetallePedido));
        }).toList();

        Usuario usuario = usuarioRepository.findById(newPedidoRequest.idUsuario())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + newPedidoRequest.idUsuario()));

        Pedido pedido = PedidoMapper.toEntity(newPedidoRequest, detallesPedido, usuario);
        pedido.setTotal(pedido.calcularTotal());


        return PedidoMapper.toPedidoResponse(pedidoRepository.save(pedido));
    }

    public List<PedidoResponse> listar() {
        List<Pedido> pedidos = pedidoRepository.findByEstado(Pedido.COMUN);
        return pedidos.stream().map(PedidoMapper::toPedidoResponse).toList();
    }

    public PedidoResponse buscarPorId(Integer id) {
        Pedido model = pedidoRepository.findById(id).orElse(null);
        if(model == null || model.getEstado() == Pedido.ELIMINADO){
            throw new IllegalArgumentException("NO SE ENCONTRÓ EL PEDIDO CON ID: "+id);
        }
        return PedidoMapper.toPedidoResponse(model);
    }

    public List<PedidoResponse> buscarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario));
        List<Pedido> pedidos = pedidoRepository.findAllByUsuario(usuario);
        return pedidos.stream().map(PedidoMapper::toPedidoResponse).toList();
    }

}