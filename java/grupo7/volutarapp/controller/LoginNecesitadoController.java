package grupo7.volutarapp.controller;

import grupo7.volutarapp.model.dtos.LoginNecesitadoDto;
import grupo7.volutarapp.model.dtos.LoginVoluntarioDto;
import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import grupo7.volutarapp.service.UsuarioNecesitadoService;
import grupo7.volutarapp.service.UsuarioVoluntarioService;
import grupo7.volutarapp.util.LoggedNecesitado;
import grupo7.volutarapp.util.LoggedVoluntario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@AllArgsConstructor
@Setter
@Getter
public class LoginNecesitadoController {
    private LoggedNecesitado loggedNecesitado;/* = new LoggedNecesitado(); // Inicialización aquí*/
    @Autowired
    private UsuarioNecesitadoService usuarioNecesitadoService;
    private final HttpSession session;
    @GetMapping("/inicio-sesion-formulario-usuario-necesitado")
    public String login(Model model){
        if(this.loggedNecesitado.isLogged()){

            System.out.println("loggedNecesitado.isLogged");

            return "redirect:/home-necesitado";
        }
        System.out.println("no está logeado");

        return "inicio-sesion-formulario-usuario-necesitado";
    }
    @PostMapping("/inicio-sesion-formulario-usuario-necesitado")
    public ModelAndView handleLoginRequest(@Valid @ModelAttribute("loginNecesitadoDto") LoginNecesitadoDto loginNecesitadoDto,
                                           BindingResult bindingResult,
                                           ModelAndView modelAndView,
                                           HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            modelAndView.addObject("errorVisible", true);
            return modelAndView;
        }
        UsuarioNecesitado usuarioNecesitado = usuarioNecesitadoService.getUsuarioNecesitadoByNombreUsuario(loginNecesitadoDto.getNombreUsuario());

        if(usuarioNecesitado!= null&&usuarioNecesitadoService.isValidPassword(usuarioNecesitado,
                loginNecesitadoDto.getContrasenya())){
            LoggedNecesitado loggedNecesitado = new LoggedNecesitado();
            loggedNecesitado.setId(usuarioNecesitado.getId());
            loggedNecesitado.setNombreUsuario(usuarioNecesitado.getNombreUsuario());
            HttpSession session = request.getSession();
            session.setAttribute("loggedNecesitado", loggedNecesitado);
            System.out.println(loggedNecesitado.getNombreUsuario());
            modelAndView.setViewName("redirect:/home-necesitado");
            modelAndView.addObject("loggedIn", true);
            usuarioNecesitado = new UsuarioNecesitado();
            return modelAndView;
        }
        else {
            modelAndView.addObject("error", "Nombre de usuario o contraseña incorrectos");
            return modelAndView;
        }


    }
    @ModelAttribute
    public LoginNecesitadoDto loginVoluntarioDto(){
        return new LoginNecesitadoDto();
    }
}
