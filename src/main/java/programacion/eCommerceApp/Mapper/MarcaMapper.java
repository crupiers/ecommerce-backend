package programacion.eCommerceApp.Mapper;

import programacion.eCommerceApp.DTO.MarcaDTO;
import programacion.eCommerceApp.model.Marca;

public class MarcaMapper {
    public static MarcaDTO toDTO(Marca model) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(model.getId());
        dto.setDenominacion(model.getDenominacion());
        dto.setObservaciones(model.getObservaciones());
        dto.setEstado(model.getEstado());
        return dto;
    }

    public static Marca toEntity(MarcaDTO dto) {
        Marca model = new Marca();
        model.setId(dto.getId());
        model.setDenominacion(dto.getDenominacion());
        model.setObservaciones(dto.getObservaciones());
        model.setEstado(dto.getEstado());
        return model;
    }

}
