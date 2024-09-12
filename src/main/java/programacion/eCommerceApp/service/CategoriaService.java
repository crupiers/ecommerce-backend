package programacion.eCommerceApp.service;

import org.springframework.stereotype.Service;
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
        Optional<Categoria> categoriaExistente = modelRepository.findByNombre(model.getNombre());

        if (categoriaExistente.isPresent()) {
            throw new IllegalArgumentException("La categoría ya está registrada.");
        }

        return CategoriaMapper.toCategoriaResponse(modelRepository.save(model));
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
