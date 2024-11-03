package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 32)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    @Builder.Default
    private int estado = 0;

    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

    public void eliminar(){
        this.setEstado(ELIMINADO);
    }

    public void recuperar(){
        this.setEstado((COMUN));
    }

}
