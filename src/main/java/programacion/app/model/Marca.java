package programacion.app.model;

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
        public static final int COMUN=0;
        public static final int ELIMINADO=1;

        public void asEliminar() {
             this.setEstado(1);
       }
        public void recuperar(){ this.setEstado(0);}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @NotNull
    public int getEstado() {
        return estado;
    }

    public void setEstado(@NotNull int estado) {
        this.estado = estado;
    }

    public Marca() {
    }

    public Marca(Integer id, String denominacion, String observaciones, int estado) {
        this.id = id;
        this.denominacion = denominacion;
        this.observaciones = observaciones;
        this.estado = estado;
    }
}
