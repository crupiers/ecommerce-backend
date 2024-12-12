package programacion.eCommerceApp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.controller.response.CategoriaResponse;

public interface ICategoriaService {
    List<CategoriaResponse> listar();
    ResponseEntity<CategoriaResponse> buscarPorId(Integer id);
    CategoriaResponse crear(NewCategoriaRequest newCategoriaRequest);
    CategoriaResponse actualizar(NewCategoriaRequest newCategoriaRequest, Integer id);
    ResponseEntity<Void> eliminar(Integer id);
    ResponseEntity<Void> recuperar(Integer id);
    List<CategoriaResponse> listarParaAuditoria();
}
