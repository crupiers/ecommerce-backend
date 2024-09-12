package programacion.eCommerceApp.service;

import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.mapper.MarcaMapper;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.repository.IMarcaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service

public class MarcaService implements IMarcaService {
    private static final Logger logger = LoggerFactory.getLogger(MarcaService.class);
    @Autowired
    private IMarcaRepository modelRepository; //el repository no es instanciado nunca

    @Override
    public List<MarcaResponse> listar() {
        List<Marca> marcas = modelRepository.findByEstado(Marca.COMUN);
        return marcas.stream().map(MarcaMapper::toMarcaResponse).toList();
    }

    @Override
    public Marca buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public MarcaResponse guardar(NewMarcaRequest newMarcaRequest) {
        Marca model = MarcaMapper.toEntity(newMarcaRequest);
        Optional<Marca> marcaExistente = modelRepository.findByDenominacion(model.getDenominacion());

        if (marcaExistente.isPresent()) {
            throw new IllegalArgumentException("La marca ya está registrada.");
        }

        return MarcaMapper.toMarcaResponse(modelRepository.save(model));
    }

    @Override
    public Marca guardar(Marca model) {
        return modelRepository.save(model);
    }

    @Override
    public void eliminar(Marca model) {

        model.eliminar();
        modelRepository.save(model);
    }

    @Override
    public void recuperar(Marca model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
