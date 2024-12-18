package programacion.eCommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewMovimientoStockRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.service.IMovimientoStockService;
import programacion.eCommerceApp.service.MovimientoStockService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value = " http://localhost:8080")
@Validated
public class MovimientoStockController {
    @Autowired
    private IMovimientoStockService service;

    @PostMapping("/admin/movimientoStock/{productoId}")
    public ResponseEntity<MovimientoStockResponse> crear(@PathVariable Integer productoId, @Valid @RequestBody NewMovimientoStockRequest newMovimientoStockRequest){
        MovimientoStockResponse response = service.crear(productoId, newMovimientoStockRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/movimientoStock/auditoria")
    public List<MovimientoStockResponse> listarParaAuditoria(){
        return service.listarParaAuditoria();
    }

}
