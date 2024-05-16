package grupo7.volutarapp.controller;

import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.repository.ServicioEntityRepository;
import grupo7.volutarapp.repository.UsuarioVoluntarioRepository;
import grupo7.volutarapp.service.UsuarioVoluntarioService;
import grupo7.volutarapp.util.LoggedVoluntario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ChangeServiceVoluntarioController {

    private final UsuarioVoluntarioRepository usuarioVoluntarioRepository;
    private final UsuarioVoluntarioService usuarioVoluntarioService;
    private final ServicioEntityRepository servicioEntityRepository;

    @Autowired
    public ChangeServiceVoluntarioController(UsuarioVoluntarioRepository usuarioVoluntarioRepository, UsuarioVoluntarioService usuarioVoluntarioService, ServicioEntityRepository servicioEntityRepository) {
        this.usuarioVoluntarioRepository = usuarioVoluntarioRepository;
        this.usuarioVoluntarioService = usuarioVoluntarioService;
        this.servicioEntityRepository = servicioEntityRepository;
    }

    @PostMapping("/cambiar-habilidades")
    public String cambiarHabilidades(@RequestParam("habilidades") String[] habilidades, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedVoluntario") != null) {
            LoggedVoluntario loggedVoluntario = (LoggedVoluntario) session.getAttribute("loggedVoluntario");
            String nombreUsuario = loggedVoluntario.getNombreUsuario();
            System.out.println("Nombre de usuario conectado desde ChangeServiceController: " + nombreUsuario);
            System.out.println("Habilidades seleccionadas:");
            for (String habilidad : habilidades) {
                System.out.println(habilidad);
            }
            UsuarioVoluntario usuarioVoluntario = usuarioVoluntarioService.getUsuarioVoluntarioRepository().findByNombreUsuario(nombreUsuario);
            System.out.println(usuarioVoluntario.getNombreUsuario());
            Set<ServicioEntity> habilidadesSet = new HashSet<>();
            for (String habilidad : habilidades) {
                ServicioEntity servicio = servicioEntityRepository.findByServicio(habilidad);
                if (servicio != null) {
                    habilidadesSet.add(servicio);
                }
            }
            usuarioVoluntario.setServiciosOfrecidos(habilidadesSet);
            usuarioVoluntarioRepository.save(usuarioVoluntario);
            model.addAttribute("usuarioVoluntario", usuarioVoluntario);
        } else {
            System.out.println("NO HAY");
        }
        return "redirect:/home-voluntario";
    }
}