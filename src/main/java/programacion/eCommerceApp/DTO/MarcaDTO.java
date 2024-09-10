package programacion.eCommerceApp.DTO;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarcaDTO {
    private Integer id;
    private String denominacion;
    private String observaciones;
    private int estado;
}
