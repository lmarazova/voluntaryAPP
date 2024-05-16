package grupo7.volutarapp.model.entity;

import grupo7.volutarapp.model.enums.Servicio;
import grupo7.volutarapp.model.enums.Ubicacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario_necesitado")
public class UsuarioNecesitado extends UsuarioBase{

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private ServicioEntity necesidad;

    @OneToOne(mappedBy = "usuarioNecesitado")
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "usuario_voluntario_id")
    private UsuarioVoluntario usuarioVoluntario;




}
