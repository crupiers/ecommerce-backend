package programacion.eCommerceApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "nombre")})
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String contrasenia;
    private Rol rol;
    private LocalDate fechaNacimiento;

    @Builder.Default
    private Integer estado = 0;
    public static final int COMUN = 0;
    public static final int ELIMINADO = 1;

    @Column(name = "created_at")
    @CreatedDate
    private String createdAt;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

}
