package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    private String horaPedido = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    @Builder.Default
    private String fechaPedido = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    @Builder.Default
    private Double total = 0.0;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    //(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idDetallesPedido")
    private List<DetallePedido> detallesPedido;



    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_at")
    @CreatedDate
    private String createdAt;

    @Column(name = "updated_by", nullable = true)
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private String updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private String deletedAt;

    public Double calcularTotal() { //PARA CALCULAR EL TOTAL DE UN PEDIDO CON EL SUBTOTAL DE DETALLE PEDIDO
        return detallesPedido.stream()
            .mapToDouble(DetallePedido::getSubtotal)
            .sum();
    }

    @Builder.Default
    private int estado = 0;

    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

    public void eliminar() {
        this.setEstado(ELIMINADO);
        this.setDeletedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));
    }
    public void recuperar() { this.setEstado(COMUN); }
}
