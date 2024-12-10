package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Builder.Default
    private String horaPedido = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    @Builder.Default
    private String fechaPedido = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
}

