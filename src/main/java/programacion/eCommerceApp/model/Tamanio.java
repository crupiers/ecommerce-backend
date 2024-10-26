package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Tamanio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String nombre;
    private String descripcion;
    @Builder.Default
    private int estado = 0;
    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

    public void eliminar(){this.setEstado(ELIMINADO);}
    public void recuperar(){
        this.setEstado((COMUN));
    }

}
