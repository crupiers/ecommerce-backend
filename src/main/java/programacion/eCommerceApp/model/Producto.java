package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 24)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer codigoBarra;
    @Column(nullable = false)
    private Double precio;
    @Column(nullable = false)
    @Builder.Default
    private int estado = 0;

    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "idColor")
    private Color color;
    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "idTamanio")
    private Tamanio tamanio;
    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "idMarca")
    private Marca marca;

    public void eliminar() {
        this.setEstado(ELIMINADO);
    }

    public void recuperar() {
        this.setEstado(COMUN);
    }

}
