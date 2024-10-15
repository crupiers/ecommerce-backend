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
        Optional<Tamanio> tamanioOptional = modelRepository.findByNombre(model.getNombre());

        if (tamanioOptional.isPresent()) {
            Tamanio tamanioExistente = tamanioOptional.get();
            if (tamanioExistente.getEstado() == Tamanio.ELIMINADO) {
                tamanioExistente.recuperar();
                tamanioExistente.setNombre(model.getNombre());
                tamanioExistente.setDescripcion(model.getDescripcion());

                return TamanioMapper.toTamanioResponse(modelRepository.save(tamanioExistente));
            } else {
                throw new IllegalArgumentException("El tamaño ya existe");
            }
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

    @Override
    public TamanioResponse actualizar(NewTamanioRequest newTamanioRequest, Integer id) {
        Tamanio model = TamanioMapper.toEntity(newTamanioRequest);
        Optional<Tamanio> tamanioOptional=modelRepository.findById(id);

        if(tamanioOptional.isPresent()){
            if(tamanioOptional.get().getEstado()==Tamanio.ELIMINADO){
                throw new IllegalArgumentException("EL TAMAÑO CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADO");
            }
            Tamanio tamanio = tamanioOptional.get();
            tamanio.setNombre(model.getNombre());
            tamanio.setDescripcion(model.getDescripcion());
            return TamanioMapper.toTamanioResponse(modelRepository.save(tamanio));
        }
        throw new IllegalArgumentException("EL TAMAÑO CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }
}
