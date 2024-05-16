package grupo7.volutarapp.model.entity;

import grupo7.volutarapp.model.enums.Servicio;
import jakarta.persistence.*;
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
@Table(name = "servicio")
public class ServicioEntity extends BaseEntity{
    @Column(name = "servicio")
    private String servicio;

    @OneToMany(mappedBy ="necesidad")
    private Set<UsuarioNecesitado>usuarioNecesitado = new HashSet<>();

    @ManyToMany(mappedBy = "serviciosOfrecidos")
    private Set<UsuarioVoluntario>usuariosVoluntarios = new HashSet<>();
}