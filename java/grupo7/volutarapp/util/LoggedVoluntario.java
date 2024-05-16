package grupo7.volutarapp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@ComponentScan
@SessionScope
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoggedVoluntario {
    private Long id;
    private String nombreUsuario;

    public boolean isLogged(){
        return this.nombreUsuario != null && this.id != null;
    }

}
