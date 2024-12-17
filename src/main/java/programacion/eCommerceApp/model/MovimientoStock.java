package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MovimientoStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer cantidad;
    @Column
    private String motivo;
    @Column
    private String tipoMovimiento;

    public static final String entrada = "ENTRADA";
    public static final String salida = "SALIDA";

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Builder.Default
    private String horaPedido = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    @Builder.Default
    private String fechaPedido = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
}

