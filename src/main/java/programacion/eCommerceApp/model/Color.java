package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity //etiqueta para que la bbdd lo registre en tabla
@Data //etiqueta de lombok para getters y setters (y alguna cosa más q no googlé)
@Builder //patron de diseño para crear objetos de esta clase (no tengo idea de como funciona)
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //genera números automáticamente
    private Integer id;
    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 24)
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
