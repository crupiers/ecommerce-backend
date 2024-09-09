package programacion.ejemplo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String denominacion;
    private String observaciones;
    @NotNull
    private int estado;
    public static final int COMUN=0; //ASÍ LO HACE EL PROFE
    public static final int ELIMINADO=1;
    public void eliminar() {
          this.setEstado(1);
       }

}
