package grupo7.volutarapp.controller;

import ch.qos.logback.core.model.ImplicitModel;
import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.entity.Solicitud;
import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.model.enums.EstadoSolicitud;
import grupo7.volutarapp.model.enums.Ubicacion;
import grupo7.volutarapp.repository.SolicitudRepository;
import grupo7.volutarapp.repository.UsuarioNecesitadoRepository;
import grupo7.volutarapp.repository.UsuarioVoluntarioRepository;
import grupo7.volutarapp.service.SolicitudService;
import grupo7.volutarapp.service.UsuarioNecesitadoService;
import grupo7.volutarapp.service.UsuarioVoluntarioService;
import grupo7.volutarapp.util.LoggedNecesitado;
import grupo7.volutarapp.util.LoggedVoluntario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeController extends BaseController{
    @Autowired
    private UsuarioVoluntarioService usuarioVoluntarioService;
    @Autowired
    private UsuarioNecesitadoService usuarioNecesitadoService;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private UsuarioVoluntarioRepository usuarioVoluntarioRepository;
    @Autowired
    private UsuarioNecesitadoRepository usuarioNecesitadoRepository;


    @GetMapping("home-voluntario")
    public String mostrarHomeVoluntario(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session!= null && session.getAttribute("loggedVoluntario")!=null){
            LoggedVoluntario loggedVoluntario = (LoggedVoluntario) session.getAttribute("loggedVoluntario");
            String nombreUsuario = loggedVoluntario.getNombreUsuario();
            UsuarioVoluntario usuarioVoluntario = usuarioVoluntarioService.getUsuarioVoluntarioRepository().findByNombreUsuario(nombreUsuario);
            String nombre = usuarioVoluntario.getNombre();
            String apellido = usuarioVoluntario.getApellido();
            String correoElectronico = usuarioVoluntario.getCorreoElectronico();
            String ubicacion = String.valueOf(usuarioVoluntario.getUbicacion());
            Set<ServicioEntity>serviciosOfrecidos = usuarioVoluntario.getServiciosOfrecidos();
            List<String>nombresServicios = serviciosOfrecidos.stream()
                            .map(ServicioEntity::getServicio)
                                    .toList();
            /*para que aparezcan los usuarios necesitados correspondientes*/
            Set<UsuarioNecesitado>usuariosNececitadosDisponibles = new HashSet<>();
            for(ServicioEntity servicio: serviciosOfrecidos){
                usuariosNececitadosDisponibles.addAll(usuarioNecesitadoService.buscarPorNecesidad(servicio, Ubicacion.valueOf(ubicacion)));
            }
            /*hasta aquí*/
            Set<Solicitud> solicitudesEnProgreso = usuarioVoluntario.getSolicitudes().stream()
                    .filter(solicitud -> solicitud.getEstadoSolicitud() == EstadoSolicitud.EN_PROGRESO)
                    .collect(Collectors.toSet());

            // Pasar las solicitudes en progreso a la vista
            model.addAttribute("solicitudesEnProgreso", solicitudesEnProgreso);
            model.addAttribute("nombreUsuario", nombreUsuario);
            model.addAttribute("nombre", nombre);
            model.addAttribute("apellido", apellido);
            model.addAttribute("correoElectronico", correoElectronico);
            model.addAttribute("ubicacion", ubicacion);
            model.addAttribute("nombresServicios", nombresServicios);
            /*eso es para que le añade a la vista los usuarios Necesitados disponibles*/
            model.addAttribute("usuariosNecesitadosDisponibles", usuariosNececitadosDisponibles);
            return "home-voluntario";
        }else{
            return "redirect:/inicio-sesion-formulario-usuario-voluntario";
        }
    }

    @GetMapping("home-necesitado")
    public String mostrarHomeNecesitado(@RequestParam(name = "solicitudId", required = false) Long solicitudId,HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session!= null && session.getAttribute("loggedNecesitado")!=null){
            LoggedNecesitado loggedNecesitado = (LoggedNecesitado) session.getAttribute("loggedNecesitado");
            String nombreUsuario = loggedNecesitado.getNombreUsuario();
            UsuarioNecesitado usuarioNecesitado= usuarioNecesitadoService.getUsuarioNecesitadoRepository().findByNombreUsuario(nombreUsuario);
            String nombre = usuarioNecesitado.getNombre();
            String apellido = usuarioNecesitado.getApellido();
            String correoElectronico = usuarioNecesitado.getCorreoElectronico();
            String ubicacion = String.valueOf(usuarioNecesitado.getUbicacion());
            ServicioEntity necesidad = usuarioNecesitado.getNecesidad();
            /*Set<ServicioEntity>serviciosOfrecidos = usuarioVoluntario.getServiciosOfrecidos();
            List<String>nombresServicios = serviciosOfrecidos.stream()
                    .map(ServicioEntity::getServicio)
                    .toList();*/

            model.addAttribute("nombreUsuario", nombreUsuario);
            model.addAttribute("nombre", nombre);
            model.addAttribute("apellido", apellido);
            model.addAttribute("correoElectronico", correoElectronico);
            model.addAttribute("ubicacion", ubicacion);
            model.addAttribute("necesidad", necesidad);
            // Obtener la solicitud asociada al usuario necesitado actual
            Solicitud solicitud = solicitudRepository.findByUsuarioNecesitado(usuarioNecesitado);

            // Verificar si se encontró una solicitud
            if (solicitud != null) {
                // Agregar la solicitud al modelo
                model.addAttribute("solicitud", solicitud);
                if (solicitud.getEstadoSolicitud().equals(EstadoSolicitud.PENDIENTE)) {
                    // Si la solicitud está pendiente, agregar un atributo al modelo indicando que hay una solicitud pendiente
                    model.addAttribute("solicitudPendiente", true);
                }
                else if(solicitud.getEstadoSolicitud().equals(EstadoSolicitud.EN_PROGRESO)){
                    model.addAttribute("solicitudEnProgreso", true);
                }
            } /*else {
                // Si no se encontró una solicitud, puedes manejar este caso según tus requisitos
                // Por ejemplo, mostrar un mensaje indicando que no hay solicitud disponible
                model.addAttribute("mensaje", "No hay una solicitud asociada a este usuario");
            }*/
            return "home-necesitado";
        }else{
            return "redirect:/inicio-sesion-formulario-usuario-necesitado";
        }
    }

    @PostMapping("/guardar-selecciones")
    public String guardarSelecciones(@RequestParam("usuariosSeleccionados") List<Long> usuariosSeleccionados, HttpServletRequest request) {
        // Obtener el usuario voluntario actual

        HttpSession session = request.getSession(false);
        if(session!= null && session.getAttribute("loggedVoluntario")!=null) {
            LoggedVoluntario loggedVoluntario = (LoggedVoluntario) session.getAttribute("loggedVoluntario");
            String nombreUsuario = loggedVoluntario.getNombreUsuario();

            // Obtener los usuarios necesitados seleccionados por sus IDs
            List<UsuarioNecesitado> usuariosNecesitadosSeleccionados = usuarioNecesitadoService.buscarPorIds(usuariosSeleccionados);
            UsuarioVoluntario usuarioVoluntario = usuarioVoluntarioRepository.findByNombreUsuario(loggedVoluntario.getNombreUsuario());
            /*usuarioVoluntario.getUsuariosNecesitados().addAll(usuariosNecesitadosSeleccionados);*/
            for (UsuarioNecesitado usuarioNecesitado : usuariosNecesitadosSeleccionados) {
                usuarioNecesitado.setUsuarioVoluntario(usuarioVoluntario);
                Solicitud solicitud = usuarioNecesitado.getSolicitud();
                if(solicitud!=null){
                    solicitud.setEstadoSolicitud(EstadoSolicitud.EN_PROGRESO);
                    solicitud.setUsuarioVoluntario(usuarioVoluntario); // Asignar el voluntario a la solicitud

                }
            }
            usuarioVoluntario.getUsuariosNecesitados().addAll(usuariosNecesitadosSeleccionados);

            usuarioVoluntarioRepository.save(usuarioVoluntario);
            /*Set<UsuarioNecesitado> conjuntoUsuariosNecesitados = new HashSet<>(usuariosNecesitadosSeleccionados);*/
            // Asignar los usuarios necesitados seleccionados al voluntario
            /*usuarioVoluntario.setUsuariosNecesitados(conjuntoUsuariosNecesitados);*/



            return "redirect:/home-voluntario"; }
        else{
            return "redirect:/inicio-sesion-formulario-usuario-necesitado";
        }
    }
    @PostMapping("/marcar-como-resuelta")
    public String marcarComoResuelta(@RequestParam(name = "solicitudId") Long solicitudId, @RequestParam(name = "solicitudResuelta", required = false) boolean solicitudResuelta, Model model) {
        // Buscar la solicitud por su ID
         Solicitud solicitud = solicitudRepository.findSolicitudById(solicitudId);
         System.out.println(" ");


        if (solicitud != null && solicitudResuelta) {
            // Establecer el estado de la solicitud como completada
            solicitud.setEstadoSolicitud(EstadoSolicitud.COMPLETADA);

            // Obtener el usuario necesitado y el usuario voluntario asociados a la solicitud
            UsuarioNecesitado usuarioNecesitado = solicitud.getUsuarioNecesitado();
            UsuarioVoluntario usuarioVoluntario = solicitud.getUsuarioVoluntario();

            // Eliminar la referencia a la solicitud en el usuario necesitado y en el usuario voluntario
            usuarioNecesitado.setSolicitud(null);
            usuarioVoluntario.getSolicitudes().remove(solicitud);
            solicitud.setUsuarioNecesitado(null);
            solicitud.setUsuarioVoluntario(null);
            solicitudRepository.save(solicitud);
            model.addAttribute("mensajeConfirmacion", "¡Tu necesidad ha sido cumplida correctamente!");

            // Eliminar el usuario necesitado y el usuario voluntario de la base de datos
            usuarioNecesitadoRepository.save(usuarioNecesitado);
            usuarioVoluntarioRepository.save(usuarioVoluntario);

            // Eliminar la solicitud de la base de datos
            /*solicitudRepository.delete(solicitud);*/

            model.addAttribute("mensajeConfirmacion", true);

            // Redirigir a la página home-necesitado.html
            return "redirect:/home-necesitado";
        } else {
            // Manejo si la solicitud no se encuentra o no se marca como resuelta
            return "redirect:/home-necesitado";
        }
    }

    private String obtenerNombreUsuarioActual(HttpSession session) {
        return (String) session.getAttribute("nombreUsuario");
    }

}




