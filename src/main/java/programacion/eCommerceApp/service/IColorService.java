package programacion.eCommerceApp.service;

import org.springframework.http.ResponseEntity;
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import java.util.List;

public interface IColorService {
    List<ColorResponse> listar();
    ResponseEntity<ColorResponse> buscarPorId(Integer id);
    ResponseEntity<ColorResponse> buscarPorNombre (String nombre);
    ColorResponse crear(NewColorRequest newColorRequest);
    ColorResponse actualizar(NewColorRequest newColorRequest, Integer id);
    ResponseEntity<Void> eliminar(Integer id);
    ResponseEntity<Void> recuperar (Integer id);
    List<ColorResponse> listarParaAuditoria();
}
