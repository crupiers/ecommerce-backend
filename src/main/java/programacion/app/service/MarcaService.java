package programacion.app.service;

import org.springframework.stereotype.Service;

import programacion.app.DTO.MarcaDTO;
import programacion.app.Mapper.MarcaMapper;
import programacion.app.model.Categoria;
import programacion.app.model.Marca;
import programacion.app.repository.IMarcaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service

public class MarcaService implements IMarcaService {
    private static final Logger logger = LoggerFactory.getLogger(MarcaService.class);
    @Autowired
    private IMarcaRepository modelRepository;

    @Override
    public List<MarcaDTO> listar() {
        List<Marca> marcas = modelRepository.findByEstado(Marca.COMUN);
        return marcas.stream().map(MarcaMapper::toDTO).toList();
    }

    @Override
    public Marca buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public MarcaDTO guardar(MarcaDTO modelDTO) {
        Optional<Marca> marcaExistente = modelRepository.findByDenominacion(modelDTO.getDenominacion());

        if (marcaExistente.isPresent()) {
            System.out.println("MARCA EXISTENTE-------------------------------");
            throw new IllegalArgumentException("La marca ya está registrada.");
        }

        Marca model = MarcaMapper.toEntity(modelDTO);
        return MarcaMapper.toDTO(modelRepository.save(model));
    }
    @Override
    public Marca guardar(Marca model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Marca model) {

        model.asEliminar();
        modelRepository.save(model);
    }

    @Override
    public void recuperar(Marca model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
