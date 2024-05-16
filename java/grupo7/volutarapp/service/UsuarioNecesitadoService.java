package grupo7.volutarapp.service;

import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.model.enums.EstadoSolicitud;
import grupo7.volutarapp.model.enums.Ubicacion;
import grupo7.volutarapp.repository.UsuarioNecesitadoRepository;
import grupo7.volutarapp.repository.UsuarioVoluntarioRepository;
import grupo7.volutarapp.util.LoggedNecesitado;
import grupo7.volutarapp.util.LoggedVoluntario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioNecesitadoService {
    private HttpServletRequest request;
    @Autowired
    private UsuarioNecesitadoRepository usuarioNecesitadoRepository;
    private PasswordEncoder encoder;
    private HttpSession session;
    private LoggedNecesitado loggedNecesitado;


    public UsuarioNecesitado getUsuarioNecesitadoByNombreUsuario(String nombreUsuario){
        return usuarioNecesitadoRepository.findByNombreUsuario(nombreUsuario);
    }


    public boolean isValidPassword(UsuarioNecesitado usuarioNecesitado,
                                   String contrasenya) {
        return (usuarioNecesitado.getContrasenya().equals(contrasenya));
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioNecesitadoRepository.findByNombreUsuario(nombreUsuario)!=null;
    }

    public UsuarioNecesitado buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioNecesitadoRepository.findByNombreUsuario(nombreUsuario);
    }

    /*public Set<UsuarioNecesitado>buscarPorNecesidad(ServicioEntity servicio, Ubicacion ubicacion) {
        return usuarioNecesitadoRepository.findByNecesidadAndUbicacionAndSolicitudNotNull(servicio, ubicacion);
    }*/
    public Set<UsuarioNecesitado>buscarPorNecesidad(ServicioEntity servicio, Ubicacion ubicacion) {
        return usuarioNecesitadoRepository.findByNecesidadAndUbicacionAndSolicitudNotNull(servicio, ubicacion).stream()
                .filter(usuarioNecesitado -> usuarioNecesitado.getSolicitud().getEstadoSolicitud()==EstadoSolicitud.PENDIENTE)
                .collect(Collectors.toSet());
    }
    public List<UsuarioNecesitado> buscarPorIds(List<Long> usuariosSeleccionados) {
        return usuarioNecesitadoRepository.findAllById(usuariosSeleccionados);
    }
}

