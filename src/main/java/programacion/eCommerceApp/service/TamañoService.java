package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewTamañoRequest;
import programacion.eCommerceApp.controller.response.TamañoResponse;
import programacion.eCommerceApp.mapper.TamañoMapper;
import programacion.eCommerceApp.model.Tamaño;
import programacion.eCommerceApp.repository.ITamañoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TamañoService implements ITamañoService {

    @Autowired
    private ITamañoRepository modelRepository;

    @Override
    public TamañoResponse crear(NewTamañoRequest newTamañoRequest) {
        Tamaño model = TamañoMapper.toEntity(newTamañoRequest);
        Optional<Tamaño> tamañoExistente = modelRepository.findByDenominacion(model.getDenominacion());
        Optional<Tamaño> observacionesExistente = modelRepository.findByObservaciones(model.getObservaciones());

        if (tamañoExistente.isPresent()) {
            throw new IllegalArgumentException("El tamaño ya está registrado.");
        }

        if (observacionesExistente.isPresent()) {
            throw new IllegalArgumentException("Las observaciones ya están registradas.");
        }
        return TamañoMapper.toTamañoResponse(modelRepository.save(model));
    }

    @Override
    public List<TamañoResponse> listar() {
        List<Tamaño> tamaños = modelRepository.findByEstado(true);
        return tamaños.stream().map(TamañoMapper::toTamañoResponse).toList();
    }

    @Override
    public Tamaño buscarPorId(Integer id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public void recuperar(Tamaño model) {
        model.recuperar();
        modelRepository.save(model);
    }

    @Override
    public void eliminar(Tamaño model) {
        model.eliminar();
        modelRepository.save(model);
    }
}
