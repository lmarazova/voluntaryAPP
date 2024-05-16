package grupo7.volutarapp.model.entity;

import grupo7.volutarapp.model.enums.Ubicacion;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Getter
@Setter
public abstract class UsuarioBase extends BaseEntity{

    @Column(name = "nombre_usuario",  unique = true)
    @NotNull
    @Size(min = 3, max = 15)
    private String nombreUsuario;

    @Column(name = "contrasenya")
    @NotNull
    @Size(min = 6, max = 20)
    private String contrasenya;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name="correo_electronico", nullable = false)
    @Email
    private String correoElectronico;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Ubicacion ubicacion;

}