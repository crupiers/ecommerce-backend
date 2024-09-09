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
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    @NotNull
    private int estado;
        public static final int COMUN=0;
        public static final int ELIMINADO=1;

        public void eliminar() {
             this.setEstado(1);
       }
       public void recuperar(){
            this.setEstado(0);
       }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NotNull
    public int getEstado() {
        return estado;
    }

    public void setEstado(@NotNull int estado) {
        this.estado = estado;
    }

    public Categoria() {
    }

    public Categoria(Integer id, String nombre, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }
}