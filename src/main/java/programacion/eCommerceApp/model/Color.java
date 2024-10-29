package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity //etiqueta para que la bbdd lo registre en tabla
@Data //etiqueta de lombok para getters y setters (y alguna cosa más q no googlé)
@Builder //patron de diseño para crear objetos de esta clase (no tengo idea de como funciona)
@ToString //nos devuelve el objeto con sus parametros
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //genera números automáticamente
    private Integer id;
    private String nombre;
    @Builder.Default
    private int estado = 0;
    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

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

    public void eliminar() {
        this.setEstado(ELIMINADO);
        this.setDeletedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));
    }
    public void recuperar() { this.setEstado(COMUN); }

}
