package programacion.eCommerceApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import programacion.eCommerceApp.controller.request.NewColorRequest;
import programacion.eCommerceApp.controller.response.ColorResponse;
import programacion.eCommerceApp.mapper.ColorMapper;
import programacion.eCommerceApp.model.Color;
import programacion.eCommerceApp.repository.IColorRepository;
import java.util.List;
import java.util.Optional;

@Service //declaro que la clase es un service

public class ColorService implements IColorService {

    @Autowired
    private IColorRepository modelRepository;

    @Override
    public List<ColorResponse> listar() {
        List<Color> colores = modelRepository.findByEstado(Color.COMUN);
        
        return colores.stream().map(ColorMapper::toColorResponse).toList();
    }

    @Override
    public Color buscarPorId(Integer id) {

        return modelRepository.findById(id).orElse(null);
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
            return ColorMapper.toColorResponse(modelRepository.save(color));
        }
            throw new IllegalArgumentException("EL COLOR CON ID '"+id+"' QUE SE QUIERE ACTUALIZAR NO EXISTE");
    }

    @Override
    public void eliminar(Color model) {
        model.eliminar();
        modelRepository.save(model);        //con "save" realizamos tal tarea, el repository traduce la orden a SQL
    }

    @Override
    public void recuperar(Color model) {
        model.recuperar();
        modelRepository.save(model);
    }
}
