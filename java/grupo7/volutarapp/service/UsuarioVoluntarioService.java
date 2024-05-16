package grupo7.volutarapp.service;

import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.repository.UsuarioVoluntarioRepository;
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

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioVoluntarioService {
    private HttpServletRequest request;
    @Autowired
    private UsuarioVoluntarioRepository usuarioVoluntarioRepository;
    private PasswordEncoder encoder;
    private HttpSession session;
    private LoggedVoluntario loggedVoluntario;


    public UsuarioVoluntario getUsuarioVoluntarioByNombreUsuario(String nombreUsuario){
        return usuarioVoluntarioRepository.findByNombreUsuario(nombreUsuario);
    }


    public boolean isValidPassword(UsuarioVoluntario usuarioVoluntario,
                                   String contrasenya) {
        return (usuarioVoluntario.getContrasenya().equals(contrasenya));
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioVoluntarioRepository.findByNombreUsuario(nombreUsuario)!=null;
    }



}

