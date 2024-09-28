package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private Integer stock;
    private Integer codigoBarra;
    private Double precio;

    private int estado = 0;
    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

    public void eliminar() { this.setEstado(ELIMINADO); }
    public void recuperar() { this.setEstado(COMUN); }


    @ManyToOne
    @JoinColumn(name = "nombreColor")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "idTamanio")
    private Tamanio tamanio;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "idMarca")
    private Marca marca;
}
