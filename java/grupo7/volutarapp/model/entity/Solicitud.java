package grupo7.volutarapp.model.entity;

import grupo7.volutarapp.model.enums.EstadoSolicitud;
import grupo7.volutarapp.model.enums.Servicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitud")
public class Solicitud extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private Servicio servicio;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estadoSolicitud;

    @OneToOne
    private UsuarioNecesitado usuarioNecesitado;

    @ManyToOne
    private UsuarioVoluntario usuarioVoluntario;

}