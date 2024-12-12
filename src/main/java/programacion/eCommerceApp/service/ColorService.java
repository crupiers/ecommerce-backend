package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.mapper.ColorMapper;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.repository.IColorRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ColorService implements IColorService {

    @Autowired
    private IColorRepository modelRepository;
    private static final String mensajeIdNoEncontrado="NO SE ENCONTRÓ EL COLOR CON ID: ";

    @Override
    public List<ColorResponse> listar() {
        List<Color> colores = modelRepository.findByEstado(Color.COMUN);
        return colores.stream().map(ColorMapper::toColorResponse).toList();
    }

    @Override
    public ResponseEntity<ColorResponse> buscarPorId(Integer id) {
        Color model = modelRepository.findById(id).orElse(null);
        if(model==null || model.getEstado() == Color.ELIMINADO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensajeIdNoEncontrado+id);
        }
        ColorResponse colorResponse = ColorMapper.toColorResponse(model);
        return ResponseEntity.ok(colorResponse);
    }

    @Override
    public ResponseEntity<ColorResponse> buscarPorNombre(String nombre) {
        Color model = modelRepository.findByNombre(nombre).orElse(null);
        if(model==null || model.getEstado() == Color.ELIMINADO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NO SE ENCONTRÓ EL COLOR CON NOMBRE: "+nombre);
        }
        ColorResponse colorResponse = ColorMapper.toColorResponse(model);
        return ResponseEntity.ok(colorResponse);
    }

    @Override
    public ColorResponse crear(NewColorRequest newColorRequest) {
        Color model = ColorMapper.toEntity(newColorRequest); //de peticion a color
        Optional<Color> colorOptional = modelRepository.findByNombre(model.getNombre());

        if(colorOptional.isPresent()){
            Color colorExistente = colorOptional.get();
            if(colorExistente.getEstado()==Color.ELIMINADO){
                colorExistente.recuperar();
                colorExistente.setNombre(model.getNombre());
                colorExistente.setDescripcion(model.getDescripcion());
                return ColorMapper.toColorResponse(modelRepository.save(colorExistente));
            }else {
                throw new IllegalArgumentException("EL COLOR CON NOMBRE '"+newColorRequest.nombre()+"' YA EXISTE");
            }
        }

        return ColorMapper.toColorResponse(modelRepository.save(model));
    }

    @Override
    public ColorResponse actualizar(NewColorRequest newColorRequest, Integer id){
        Color model = ColorMapper.toEntity(newColorRequest);
        Optional<Color> colorOptional = modelRepository.findById(id);

        if(colorOptional.isPresent()){
            if(colorOptional.get().getEstado()==Color.ELIMINADO){
                throw new IllegalArgumentException("EL COLOR CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR ESTÁ ELIMINADO");
            }
            Color color = colorOptional.get();
            color.setNombre(model.getNombre());
            color.setDescripcion((model.getDescripcion()));
            return ColorMapper.toColorResponse(modelRepository.save(color));
        }

        throw new IllegalArgumentException("EL COLOR CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }

    @Override
    public ResponseEntity<Void> eliminar(Integer id) {
        Color model = modelRepository.findById(id).orElse(null);
        if(model==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensajeIdNoEncontrado+id);
        }
        model.eliminar();
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> recuperar(Integer id) {
        Color model = modelRepository.findById(id).orElse(null);
        if(model==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensajeIdNoEncontrado+id);
        }
        model.recuperar();
        modelRepository.save(model);
        return ResponseEntity.ok().build();
    }

    public List<ColorResponse> listarParaAuditoria() {
        List<Color> colores = modelRepository.findAll();
        return colores.stream().map(ColorMapper::toColorResponse).toList();
    }
}
