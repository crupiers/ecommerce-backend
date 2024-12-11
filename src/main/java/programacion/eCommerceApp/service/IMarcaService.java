package programacion.eCommerceApp.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;

public interface IMarcaService {
    List<MarcaResponse> listar();
    ResponseEntity<MarcaResponse> buscarPorId(Integer id);
    MarcaResponse crear(NewMarcaRequest newMarcaRequest);
    ResponseEntity<Void> eliminar(Integer id);
    ResponseEntity<Void> recuperar(Integer id);
    MarcaResponse actualizar(NewMarcaRequest newMarcaRequest, Integer id);
    List<MarcaResponse> listarParaAuditoria();
}
