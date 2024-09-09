package programacion.app.service;

import org.springframework.stereotype.Service;

import programacion.app.DTO.CategoriaDTO;
import programacion.app.Mapper.CategoriaMapper;
import programacion.app.model.Categoria;
import programacion.app.repository.ICategoriaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
