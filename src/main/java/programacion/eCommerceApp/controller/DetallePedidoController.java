package programacion.eCommerceApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewDetallePedidoRequest;
import programacion.eCommerceApp.controller.response.DetallePedidoResponse;
import programacion.eCommerceApp.service.IDetallePedidoService;

import java.util.List;

@RestController
@RequestMapping("/ecommerce")
public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detallePedidoService;

    @PostMapping("/detallesPedidos")
    public ResponseEntity<DetallePedidoResponse> crear(@RequestBody @Valid NewDetallePedidoRequest newDetallePedidoRequest) {
        return ResponseEntity.ok(detallePedidoService.crear(newDetallePedidoRequest));
    }

    @GetMapping("/detallesPedidos")
    public List<DetallePedidoResponse> listar() {
        return detallePedidoService.listar();
    }

    @GetMapping("/detallesPedidos/{id}")
    public ResponseEntity<DetallePedidoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detallePedidoService.buscarPorId(id));
    }

}
