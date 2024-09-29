package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewTamanioRequest;
import programacion.eCommerceApp.controller.response.TamanioResponse;
import programacion.eCommerceApp.mapper.TamanioMapper;
import programacion.eCommerceApp.model.Tamanio;
import programacion.eCommerceApp.repository.ITamanioRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TamanioService implements ITamanioService {

    @Autowired
    private ITamanioRepository modelRepository;

    @Override
    public TamanioResponse crear(NewTamanioRequest newTamanioRequest) {
        Tamanio model = TamanioMapper.toEntity(newTamanioRequest);
        Optional<Tamanio> tamanioExistente = modelRepository.findByDenominacion(model.getDenominacion());
        Optional<Tamanio> observacionesExistente = modelRepository.findByObservaciones(model.getObservaciones());

        if (tamanioExistente.isPresent()) {
            throw new IllegalArgumentException("El tamaño ya está registrado.");
        }

        if (observacionesExistente.isPresent()) {
            throw new IllegalArgumentException("Las observaciones ya están registradas.");
        }
        return TamanioMapper.toTamanioResponse(modelRepository.save(model));
    }

    @Override
    public List<TamanioResponse> listar() {
        List<Tamanio> tamanios = modelRepository.findByEstado(Tamanio.COMUN);
        return tamanios.stream().map(TamanioMapper::toTamanioResponse).toList();
    }

    @Override
    public Tamanio buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public void recuperar(Tamanio model) {
        model.recuperar();
        modelRepository.save(model);
    }

    @Override
    public void eliminar(Tamanio model) {
        model.eliminar();
        modelRepository.save(model);
    }
}
