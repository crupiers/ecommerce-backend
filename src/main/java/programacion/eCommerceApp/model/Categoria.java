package programacion.eCommerceApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Builder.Default
    private int estado = 0;

    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

    public void eliminar() { this.setEstado(ELIMINADO); }
    public void recuperar() { this.setEstado(COMUN); }

}