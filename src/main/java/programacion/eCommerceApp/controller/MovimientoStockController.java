package programacion.eCommerceApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import programacion.eCommerceApp.controller.request.NewMovimientoStockRequest;
import programacion.eCommerceApp.controller.response.MovimientoStockResponse;
import programacion.eCommerceApp.service.IMovimientoStockService;
import programacion.eCommerceApp.service.MovimientoStockService;

import java.util.List;

@RestController
@RequestMapping("/ecommerce")
@CrossOrigin(value = " http://localhost:8080")
public class MovimientoStockController {
    @Autowired
    private IMovimientoStockService service;

    @PostMapping("/admin/movimientoStock/{productoId}")
    public MovimientoStockResponse crear(@PathVariable Integer productoId, @RequestBody NewMovimientoStockRequest newMovimientoStockRequest){
        return service.crear(productoId, newMovimientoStockRequest);
    }

    @GetMapping("/admin/movimientoStock/auditoria")
    public List<MovimientoStockResponse> listarParaAuditoria(){
        return service.listarParaAuditoria();
    }

}
