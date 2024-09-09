package programacion.eCommerceApp.Mapper;

import programacion.ejemplo.DTO.CategoriaDTO;
import programacion.ejemplo.model.Categoria;

public class CategoriaMapper {
    
    public static CategoriaDTO toDTO(Categoria model) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(model.getId());
     
        dto.setEstado(model.getEstado());
        return dto;
    }

    public static Categoria toEntity(CategoriaDTO dto) {
        Categoria model = new Categoria();
        model.setId(dto.getId());
        model.setEstado(dto.getEstado());
        return model;
    }

}
