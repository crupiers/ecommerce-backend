package programacion.app.Mapper;

import programacion.app.DTO.CategoriaDTO;
import programacion.app.model.Categoria;

public class CategoriaMapper {
    
    public static CategoriaDTO toDTO(Categoria model) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(model.getId());
        dto.setNombre(model.getNombre());
        dto.setEstado(model.getEstado());
        return dto;
    }

    public static Categoria toEntity(CategoriaDTO dto) {
        Categoria model = new Categoria();
        model.setId(dto.getId());
        model.setNombre(dto.getNombre());
        model.setEstado(dto.getEstado());
        return model;
    }

}
