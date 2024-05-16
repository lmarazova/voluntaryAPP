package grupo7.volutarapp.controller;

import grupo7.volutarapp.model.dtos.RegistroUsuarioNecesitadoForm;
import grupo7.volutarapp.model.dtos.RegistroUsuarioVoluntarioForm;
import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.model.enums.Servicio;
import grupo7.volutarapp.model.enums.Ubicacion;
import grupo7.volutarapp.repository.ServicioEntityRepository;
import grupo7.volutarapp.repository.UsuarioNecesitadoRepository;
import grupo7.volutarapp.repository.UsuarioVoluntarioRepository;
import grupo7.volutarapp.service.ServicioEntityService;
import grupo7.volutarapp.service.UsuarioNecesitadoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Transactional

public class RegisterNecesitadoController {
    @Autowired
    private UsuarioNecesitadoService usuarioNecesitadoService;
    @Autowired
    private ServicioEntityRepository servicioEntityRepository;
    @Autowired
    private ServicioEntityService servicioEntityService;
    @Autowired
    private UsuarioNecesitadoRepository usuarioNecesitadoRepository;

    @GetMapping("/registrar-usuario-necesitado")
    public String mostrarFormularioRegistroNecesitado(Model model){
        RegistroUsuarioNecesitadoForm registroUsuarioNecesitadoForm = new RegistroUsuarioNecesitadoForm();
        List<String>ubicacionesLegibles = new ArrayList<>();
        for(Ubicacion ubicacion: Ubicacion.values()){
            ubicacionesLegibles.add(ubicacion.toString());

        }
        model.addAttribute("ubicaciones", ubicacionesLegibles);

        List<String>habilidadesLegibles = new ArrayList<>();
        for(Servicio servicio: Servicio.values()){
            habilidadesLegibles.add(servicio.toString());
        }
        model.addAttribute("habilidades", habilidadesLegibles);
        model.addAttribute("registroUsuarioNecesitadoForm", registroUsuarioNecesitadoForm);
        return "registrar-usuario-necesitado";
    }
    @GetMapping("/verificar-nombre-usuario-necesitado")
    public ResponseEntity<?> verificarNombreUsuario(@RequestParam String nombreUsuario) {
        boolean existe = usuarioNecesitadoService.existeNombreUsuario(nombreUsuario);
        return ResponseEntity.ok(Collections.singletonMap("existe", existe));
    }
    @PostMapping("/registrar-usuario-necesitado")
    public ModelAndView handleRegisterRequest(@Valid @ModelAttribute("usuarioNecesitadoRegisterForm")RegistroUsuarioNecesitadoForm registroUsuarioNecesitadoForm,
                                              @RequestParam(name = "habilidades", required = false)String[]habilidades,
                                              BindingResult bindingResult,
                                              ModelAndView modelAndView,
                                              RedirectAttributes redirectAttributes){
        boolean errorRegistro = false;
        boolean registroExitoso = false;

        if(bindingResult.hasErrors()||!registroUsuarioNecesitadoForm.lasContrasenyasCoinciden()){
            modelAndView.addObject("registroUsuarioNecesitadoForm", registroUsuarioNecesitadoForm); // Esto es importante para mostrar los valores ingresados por el usuario en el formulario
            modelAndView.addObject("errorVisible", true);
            modelAndView.addObject("errorMessage", "Las contraseñas no coinciden!");
            System.out.println("ERRORS IN BINDINGRESULT: " + bindingResult.getAllErrors());

            errorRegistro = true;
            modelAndView.addObject("errorRegistro", true);
            return modelAndView;
        }
        UsuarioNecesitado usuarioNecesitado = new UsuarioNecesitado();
        usuarioNecesitado.setNombreUsuario(registroUsuarioNecesitadoForm.getNombreUsuario());
        usuarioNecesitado.setContrasenya(registroUsuarioNecesitadoForm.getContrasenya());
        usuarioNecesitado.setNombre(registroUsuarioNecesitadoForm.getNombre());
        usuarioNecesitado.setApellido(registroUsuarioNecesitadoForm.getApellido());
        usuarioNecesitado.setCorreoElectronico(registroUsuarioNecesitadoForm.getCorreoElectronico());
        usuarioNecesitado.setUbicacion(Ubicacion.valueOf(registroUsuarioNecesitadoForm.getUbicacion()));

        String nombreServicio = registroUsuarioNecesitadoForm.getNecesidad();
        ServicioEntity servicioEntity = servicioEntityRepository.findByServicio(nombreServicio);
        usuarioNecesitado.setNecesidad(servicioEntity);

        usuarioNecesitadoRepository.save(usuarioNecesitado);
        registroExitoso = true;
        redirectAttributes.addFlashAttribute("registroExitoso", true);
        redirectAttributes.addFlashAttribute("mensajeExitoso", "Usuario se ha registrado correctamente");
        modelAndView.addObject("usuarioNecesitado", usuarioNecesitado);
        return new ModelAndView("redirect:/inicio-sesion-formulario-usuario-necesitado");
    }

}

