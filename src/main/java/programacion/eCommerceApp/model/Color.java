package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //genera números automáticamente
    private Integer id;
    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 24)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
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
