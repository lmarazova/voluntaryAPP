package grupo7.volutarapp.controller;

import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.entity.Solicitud;
import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import grupo7.volutarapp.model.enums.EstadoSolicitud;
import grupo7.volutarapp.model.enums.Servicio;
import grupo7.volutarapp.repository.SolicitudRepository;
import grupo7.volutarapp.service.UsuarioNecesitadoService;
import grupo7.volutarapp.util.LoggedNecesitado;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UsuarioNecesitadoService usuarioNecesitadoService;
    @Autowired
    private SolicitudRepository solicitudRepository;

    @GetMapping("/crear")
    public ModelAndView mostrarFormularioSolicitud(){
        ModelAndView modelAndView = new ModelAndView("formulario-solicitud");
        modelAndView.addObject("solicitud", new Solicitud());
        return modelAndView;
    }
   @PostMapping("/crear")
    public String crearSolicitud(Solicitud solicitud, Model model, HttpSession session){
        ServicioEntity servicio = (ServicioEntity) model.getAttribute("necesidad");
        UsuarioNecesitado usuarioNecesitado = obtenerUsuarioActual(session);
        solicitud.setUsuarioNecesitado(usuarioNecesitado);
        solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE);

        Servicio servicioAsignado = Servicio.valueOf(usuarioNecesitado.getNecesidad().getServicio());
        solicitud.setServicio(servicioAsignado);

        Solicitud solicitudExistente = solicitudRepository.findByUsuarioNecesitado(usuarioNecesitado);


       /*if (solicitudExistente != null && solicitudExistente.getServicio() == servicioAsignado) {
           return "redirect:/home-necesitado"; // Ya hay una solicitud existente para la misma necesidad
       } else {
           Solicitud solicitudGuardada = solicitudRepository.save(solicitud);
           Long solicitudId = solicitudGuardada.getId();
           return "redirect:/home-necesitado?solicitudId=" + solicitudId;
       }*/
       if (solicitudExistente != null) {
           solicitudExistente.setServicio(solicitud.getServicio());
           // Actualiza otros campos según sea necesario
           solicitudRepository.save(solicitudExistente);
           Long solicitudId = solicitudExistente.getId();
           return "redirect:/home-necesitado?solicitudId=" + solicitudId;
       } else {
           // Si no existe una solicitud, crea una nueva
           solicitudRepository.save(solicitud);
           Long solicitudId = solicitud.getId();
           return "redirect:/home-necesitado?solicitudId=" + solicitudId;
       }
    }

    private UsuarioNecesitado obtenerUsuarioActual(HttpSession session){
        LoggedNecesitado loggedNecesitado = (LoggedNecesitado) session.getAttribute("loggedNecesitado");
        String nombreUsuario = loggedNecesitado.getNombreUsuario();
        return usuarioNecesitadoService.buscarPorNombreUsuario(nombreUsuario);
    }

}
