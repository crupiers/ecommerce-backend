package programacion.eCommerceApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String denominacion;
    private String observaciones;
    @NotNull
    private int estado;
        public static final int COMUN=0;
        public static final int ELIMINADO=1;

        public void asEliminar() {
             this.setEstado(1);
       }
        public void recuperar(){ this.setEstado(0);}

}
