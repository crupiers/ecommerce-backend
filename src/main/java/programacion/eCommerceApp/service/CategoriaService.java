package programacion.eCommerceApp.service;

import org.springframework.stereotype.Service;

import programacion.eCommerceApp.DTO.CategoriaDTO;
import programacion.eCommerceApp.Mapper.CategoriaMapper;
import programacion.eCommerceApp.model.Categoria;
import programacion.eCommerceApp.repository.ICategoriaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service

public class CategoriaService implements ICategoriaService {
    private static final Logger logger = LoggerFactory.getLogger(CategoriaService.class);
    @Autowired
    private ICategoriaRepository modelRepository;

    @Override
    public List<CategoriaDTO> listar() {
        List<Categoria> Categorias = modelRepository.findByEstado(Categoria.COMUN);
        return Categorias.stream().map(CategoriaMapper::toDTO).toList();
    }

    @Override
    public Categoria buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public CategoriaDTO guardar(CategoriaDTO modelDTO) {
        Optional<Categoria> categoriaExistente = modelRepository.findByNombre(modelDTO.getNombre());

        if (categoriaExistente.isPresent()) {
            System.out.println("CATEGORIA EXISTENTE");
            throw new IllegalArgumentException("La categoría ya está registrada.");
        }

        Categoria model = CategoriaMapper.toEntity(modelDTO);
        return CategoriaMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Categoria guardar(Categoria model) {

        return modelRepository.save(model);
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
