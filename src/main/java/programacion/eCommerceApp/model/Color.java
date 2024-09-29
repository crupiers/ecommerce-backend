package programacion.eCommerceApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity //etiqueta para que la bbdd lo registre en tabla
@Data //etiqueta de lombok para getters y setters (y alguna cosa más q no googlé)
@Builder //patron de diseño para crear objetos de esta clase (no tengo idea de como funciona)
@ToString //nos devuelve el objeto con sus parametros
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //genera números automáticamente
    private Integer id;
    private String nombre;
    @Builder.Default
    private int estado = 0;
    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

    public void eliminar(){this.setEstado(ELIMINADO);}
    public void recuperar(){
        this.setEstado((COMUN));
    }

}
