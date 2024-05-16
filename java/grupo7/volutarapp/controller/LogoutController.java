package grupo7.volutarapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request){
        System.out.println("Entrando en GET /logout"); // Registro de depuración
        HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
            System.out.println("Sesión invalidada en GET /logout"); // Registro de depuración
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        System.out.println("Entrando en POST /logout"); // Registro de depuración
        session.invalidate();
        System.out.println("Sesión invalidada en POST /logout"); // Registro de depuración
        return "redirect:/";
    }
}
