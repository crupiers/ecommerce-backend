package programacion.eCommerceApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Data
public class Tamaño {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String denominacion, observaciones;

    @Builder.Default
    public boolean estado = true;

    public void eliminar() { this.estado=false; }
    public void recuperar() { this.estado=true; }
}
