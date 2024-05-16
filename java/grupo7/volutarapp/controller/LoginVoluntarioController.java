package grupo7.volutarapp.controller;

import grupo7.volutarapp.model.dtos.LoginVoluntarioDto;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.service.UsuarioVoluntarioService;
import grupo7.volutarapp.util.LoggedVoluntario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@Setter
@Getter
public class LoginVoluntarioController {
    private LoggedVoluntario loggedVoluntario;/* = new LoggedVoluntario(); // Inicialización aquí*/
    @Autowired
    private UsuarioVoluntarioService usuarioVoluntarioService;
    private final HttpSession session;
    @GetMapping("/inicio-sesion-formulario-usuario-voluntario")
    public String login(Model model){
        if(this.loggedVoluntario.isLogged()){

            System.out.println("loggedVoluntario.isLogged");

            return "redirect:/home-voluntario";
        }
        System.out.println("no está logeado");

        return "inicio-sesion-formulario-usuario-voluntario";
    }
    @PostMapping("/inicio-sesion-formulario-usuario-voluntario")
    public ModelAndView handleLoginRequest(@Valid @ModelAttribute("loginVoluntarioDto")LoginVoluntarioDto loginVoluntarioDto,
                                           BindingResult bindingResult,
                                           ModelAndView modelAndView,
                                           HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            modelAndView.addObject("errorVisible", true);
            return modelAndView;
        }
        UsuarioVoluntario usuarioVoluntario = usuarioVoluntarioService.getUsuarioVoluntarioByNombreUsuario(loginVoluntarioDto.getNombreUsuario());

        if(usuarioVoluntario!= null&&usuarioVoluntarioService.isValidPassword(usuarioVoluntario,
                                                         loginVoluntarioDto.getContrasenya())){
            LoggedVoluntario loggedVoluntario = new LoggedVoluntario();
            loggedVoluntario.setId(usuarioVoluntario.getId());
            loggedVoluntario.setNombreUsuario(usuarioVoluntario.getNombreUsuario());
            HttpSession session = request.getSession();
            session.setAttribute("loggedVoluntario", loggedVoluntario);
            System.out.println(loggedVoluntario.getNombreUsuario());
            modelAndView.setViewName("redirect:/home-voluntario");
            modelAndView.addObject("loggedIn", true);
            usuarioVoluntario = new UsuarioVoluntario();
            return modelAndView;
        }
        else {
            System.out.println("ERRORRRRR");
            modelAndView.addObject("error", "Nombre de usuario o contraseña incorrectos");
            return modelAndView;
        }


    }
    @ModelAttribute
    public LoginVoluntarioDto loginVoluntarioDto(){
        return new LoginVoluntarioDto();
    }
}
