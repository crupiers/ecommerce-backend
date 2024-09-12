package programacion.eCommerceApp.model;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Entity //etiqueta para que la bbdd lo registre en tabla
@Data //etiqueta de lombok para getters y setters (y alguna cosa más q no googlé)
@Builder //patron de diseño para crear objetos de esta clase (no tengo idea de como funciona)
@ToString //nos devuelve el objeto con sus parametros

public class Color {

    @Id //hago el nombre del color como primary key, no necesariamente el id es un número
    private String nombre;
    @Builder.Default
    private boolean visible = true; //por defecto aparece el color visible

    public void eliminar() {
        this.visible=false;
    }

    public void recuperar(){
        this.visible=true;
    }

}
