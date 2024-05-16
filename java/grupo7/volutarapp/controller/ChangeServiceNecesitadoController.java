package grupo7.volutarapp.controller;

import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.repository.ServicioEntityRepository;
import grupo7.volutarapp.repository.UsuarioNecesitadoRepository;
import grupo7.volutarapp.repository.UsuarioVoluntarioRepository;
import grupo7.volutarapp.service.UsuarioNecesitadoService;
import grupo7.volutarapp.service.UsuarioVoluntarioService;
import grupo7.volutarapp.util.LoggedNecesitado;
import grupo7.volutarapp.util.LoggedVoluntario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;
@Controller
public class ChangeServiceNecesitadoController {
    private final UsuarioNecesitadoRepository usuarioNecesitadoRepository;
    private final UsuarioNecesitadoService usuarioNecesitadoService;
    private final ServicioEntityRepository servicioEntityRepository;
    @Autowired
    public ChangeServiceNecesitadoController(UsuarioNecesitadoRepository usuarioNecesitadoRepository, UsuarioNecesitadoService usuarioNecesitadoService, ServicioEntityRepository servicioEntityRepository) {
        this.usuarioNecesitadoRepository = usuarioNecesitadoRepository;
        this.usuarioNecesitadoService = usuarioNecesitadoService;
        this.servicioEntityRepository = servicioEntityRepository;
    }


    @PostMapping("/cambiar-habilidad-necesitado")
    public String cambiarHabilidad(@RequestParam("habilidad") String habilidadSeleccionada, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedNecesitado") != null) {
            LoggedNecesitado loggedNecesitado = (LoggedNecesitado) session.getAttribute("loggedNecesitado");
            String nombreUsuario = loggedNecesitado.getNombreUsuario();
            UsuarioNecesitado usuarioNecesitado = usuarioNecesitadoService.getUsuarioNecesitadoRepository().findByNombreUsuario(nombreUsuario);
            ServicioEntity servicio = servicioEntityRepository.findByServicio(habilidadSeleccionada);
            if (servicio != null) {
                usuarioNecesitado.setNecesidad(servicio);
                usuarioNecesitadoRepository.save(usuarioNecesitado);
                model.addAttribute("usuarioNecesitado", usuarioNecesitado);
            }
        }
        return "redirect:/home-necesitado";
    }

}
