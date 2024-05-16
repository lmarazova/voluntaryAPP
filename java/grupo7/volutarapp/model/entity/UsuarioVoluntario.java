package grupo7.volutarapp.model.entity;

import grupo7.volutarapp.model.enums.Ubicacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario_voluntario")
public class UsuarioVoluntario extends UsuarioBase{

    @OneToMany(mappedBy = "usuarioVoluntario")
    private Set<UsuarioNecesitado> usuariosNecesitados = new HashSet<>();

    @OneToMany(mappedBy = "usuarioVoluntario")
    private Set<Solicitud>solicitudes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "usuario_voluntario_servicio",
            joinColumns = @JoinColumn(name = "usuario_voluntario_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private Set<ServicioEntity>serviciosOfrecidos = new HashSet<>();
}
