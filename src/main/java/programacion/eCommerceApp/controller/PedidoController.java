package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewPedidoRequest;
import programacion.eCommerceApp.controller.response.PedidoResponse;
import programacion.eCommerceApp.service.IPedidoService;

import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value=" http://localhost:8080")
public class PedidoController {

    @Autowired
    IPedidoService pedidosService;

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoResponse> crear(@RequestBody @Valid NewPedidoRequest newPedidoRequest) {
        return ResponseEntity.ok(pedidosService.crear(newPedidoRequest));
    }

    @GetMapping("/pedidos")
    public List<PedidoResponse> listar() {
        return pedidosService.listar();
    }

    @GetMapping("/admin/pedidos/auditoria")
    public List<PedidoResponse> listarParaAuditoria() {
        return pedidosService.listarParaAuditoria();
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidosService.buscarPorId(id));
    }

    @GetMapping("/pedidos/porUsuario/{idUsuario}")
    public List<PedidoResponse> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return pedidosService.buscarPorUsuario(idUsuario);
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<PedidoResponse> eliminar(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidosService.eliminar(id));
    }

    @PutMapping("/pedidos/recuperar/{id}")
    public ResponseEntity<PedidoResponse> recuperar(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidosService.recuperar(id));
    }

}
