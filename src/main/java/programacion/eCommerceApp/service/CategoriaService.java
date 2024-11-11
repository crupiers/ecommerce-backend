package programacion.eCommerceApp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewCategoriaRequest;
import programacion.eCommerceApp.controller.response.CategoriaResponse;
import programacion.eCommerceApp.mapper.CategoriaMapper;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    private ICategoriaRepository modelRepository;
    private static final String mensajeIdNoEncontrado="NO SE ENCONTRÓ LA CATEGORÍA CON ID: ";

    @Override
    public List<CategoriaResponse> listar() {
        List<Categoria> categorias = modelRepository.findByEstado(Categoria.COMUN);
        return categorias.stream().map(CategoriaMapper::toCategoriaResponse).toList();
    }

    @Override
    public ResponseEntity<CategoriaResponse> buscarPorId(Integer id) {
        Categoria model = modelRepository.findById(id).orElse(null);
        if(model == null || model.getEstado() == Categoria.ELIMINADO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensajeIdNoEncontrado+id);
        }
        CategoriaResponse categoriaResponse = CategoriaMapper.toCategoriaResponse(model);
        return ResponseEntity.ok(categoriaResponse);
    }

    @Override
    public CategoriaResponse crear(NewCategoriaRequest newCategoriaRequest) {
        Categoria model = CategoriaMapper.toEntity(newCategoriaRequest);
        Optional<Categoria> categoriaOptional = modelRepository.findByNombre(model.getNombre());

        if (categoriaOptional.isPresent()) {
            Categoria categoriaExistente = categoriaOptional.get();
            if(categoriaExistente.getEstado() == Categoria.ELIMINADO){
                categoriaExistente.recuperar();
                categoriaExistente.setNombre(model.getNombre());
                categoriaExistente.setDescripcion((model.getDescripcion()));
                return CategoriaMapper.toCategoriaResponse(modelRepository.save(categoriaExistente));
            }else{
                throw new IllegalArgumentException("NO SE PUDO CREAR, ESA CATEGORÍA YA EXISTE");
            }
        }
        return CategoriaMapper.toCategoriaResponse(modelRepository.save(model));

    }

    @Override
    public CategoriaResponse actualizar(NewCategoriaRequest newCategoriaRequest, Integer id) {
        Categoria model = CategoriaMapper.toEntity(newCategoriaRequest);
        Optional<Categoria> categoriaOptional = modelRepository.findById(id); //busco en base al id de la ruta

        if (categoriaOptional.isPresent()){
            if(categoriaOptional.get().getEstado()==Categoria.ELIMINADO){
                throw new IllegalArgumentException("LA CATEGORÍA CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADA");
            }
            Categoria categoria = categoriaOptional.get();
            categoria.setNombre(model.getNombre());
            categoria.setDescripcion(model.getDescripcion());
            return CategoriaMapper.toCategoriaResponse(modelRepository.save(categoria));
        }
            throw new IllegalArgumentException("LA CATEGORÍA CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }

    @Override
    public ResponseEntity<Void> eliminar(Integer id) {
        Categoria model = modelRepository.findById(id).orElse(null);
        if (model == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensajeIdNoEncontrado+id);
        }
        model.eliminar();
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> recuperar(Integer id) {
        Categoria model = modelRepository.findById(id).orElse(null);
        if (model == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensajeIdNoEncontrado+id);
        }
        model.recuperar();
        modelRepository.save(model);
        return ResponseEntity.ok().build(); // Respuesta vacía con estado 200 OK
    }
}
