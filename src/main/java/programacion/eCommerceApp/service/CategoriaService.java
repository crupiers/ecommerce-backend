package programacion.eCommerceApp.service;

import org.springframework.http.HttpStatus;
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

    @Override
    public List<CategoriaResponse> listar() {
        List<Categoria> categorias = modelRepository.findByEstado(Categoria.COMUN);
        return categorias.stream().map(CategoriaMapper::toCategoriaResponse).toList();
    }

    @Override
    public Categoria buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
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
                return CategoriaMapper.toCategoriaResponse(modelRepository.save(categoriaExistente));
            }else{
                throw new IllegalArgumentException("La categoría ya existe");
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
            return CategoriaMapper.toCategoriaResponse(modelRepository.save(categoria));
        }
            throw new IllegalArgumentException("LA CATEGORÍA CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }

    @Override
    public void eliminar(Categoria model) {
        model.eliminar();
        modelRepository.save(model);
    }

    @Override
    public void recuperar(Categoria model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
