package grupo7.volutarapp.model.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginVoluntarioDto {
    private Long id;
    @NotNull
    private String nombreUsuario;

    @NotNull(message="La contraseña es obligatoria")
    private String contrasenya;

}
