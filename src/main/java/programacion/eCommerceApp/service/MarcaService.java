package programacion.eCommerceApp.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewMarcaRequest;
import programacion.eCommerceApp.controller.response.MarcaResponse;
import programacion.eCommerceApp.mapper.MarcaMapper;
import programacion.eCommerceApp.model.Marca;
import programacion.eCommerceApp.repository.IMarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class MarcaService implements IMarcaService {

    @Autowired
    private IMarcaRepository modelRepository; //el repository no es instanciado nunca
    private static final String MENSAJE_ID_NOENCONTRADO = "NO SE ENCONTRÓ LA MARCA CON ID: ";

    @Override
    public List<MarcaResponse> listar() {
        List<Marca> marcas = modelRepository.findByEstado(Marca.COMUN);
        return marcas.stream().map(MarcaMapper::toMarcaResponse).toList();
    }

    @Override
    public ResponseEntity<MarcaResponse> buscarPorId(final Integer id) {
        Marca model = modelRepository.findById(id).orElse(null);
        if (model == null || model.getEstado() == Marca.ELIMINADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENSAJE_ID_NOENCONTRADO + id);
        }
        MarcaResponse marcaResponse = MarcaMapper.toMarcaResponse(model);
        return ResponseEntity.ok(marcaResponse);
    }

    @Override
    public MarcaResponse crear(final NewMarcaRequest newMarcaRequest) {
        Marca model = MarcaMapper.toEntity(newMarcaRequest);
        Optional<Marca> marcaOptional = modelRepository.findByNombre(model.getNombre());

        if (marcaOptional.isPresent()) {
            Marca marcaExistente = marcaOptional.get();
            if (marcaExistente.getEstado() == Marca.ELIMINADO) {
                marcaExistente.recuperar();
                marcaExistente.setNombre(model.getNombre());
                marcaExistente.setDescripcion(model.getDescripcion());
                return MarcaMapper.toMarcaResponse(modelRepository.save(marcaExistente));
            } else {
                throw new IllegalArgumentException("La marca ya existe");
            }
        }
        return MarcaMapper.toMarcaResponse(modelRepository.save(model));
    }

    @Override
    public MarcaResponse actualizar(final NewMarcaRequest newMarcaRequest, final Integer id) {
        Marca model = MarcaMapper.toEntity(newMarcaRequest);
        Optional<Marca> marcaOptional = modelRepository.findById(id);
        if (marcaOptional.isPresent()) {
            if (marcaOptional.get().getEstado() == Marca.ELIMINADO) {
                throw new IllegalArgumentException("LA MARCA CON ID '"
                        + id
                        + "' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADA");
            }
            Marca marca = marcaOptional.get();
            marca.setNombre(model.getNombre());
            marca.setDescripcion(model.getDescripcion());
            return MarcaMapper.toMarcaResponse(modelRepository.save(marca));
        }
        throw new IllegalArgumentException("LA MARCA CON ID '"
                + id
                + "' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }

    @Override
    public ResponseEntity<Void> eliminar(final Integer id) {
        Marca model = modelRepository.findById(id).orElse(null);
        if (model == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENSAJE_ID_NOENCONTRADO + id);
        }
        model.eliminar();
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> recuperar(final Integer id) {
        Marca model = modelRepository.findById(id).orElse(null);
        if (model == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MENSAJE_ID_NOENCONTRADO + id);
        }
        model.recuperar();
        modelRepository.save(model);
        return ResponseEntity.ok().build(); // Respuesta vacía con estado 200 OK
    }

}
