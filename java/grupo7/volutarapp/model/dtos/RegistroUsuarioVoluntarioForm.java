package grupo7.volutarapp.model.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioVoluntarioForm {
   
    @Size(min = 3, max = 15, message = "Username length must be between 3 and 15 characters!")
    @NotNull
    private String nombreUsuario;

    @Size(min = 6, max = 20, message = "La contraseña tiene que ser entre 3 y 20 símbolos")
    @NotNull(message="La contraseña es obligatoria")
    private String contrasenya;
    private String confirmarContrasenya;
    @AssertTrue(message = "Las contraseñas no coinciden!")
    public boolean lasContrasenyasCoinciden(){
        return contrasenya != null && contrasenya.equals(confirmarContrasenya);
    }

    private String nombre;

    private String apellido;


    @Email(message="Tiene que poner un correo válido")
    private String correoElectronico;

    @NotNull(message = "Tiene que elegir una ubicacion")
    @NotBlank(message = "Tiene que elegir una ubicacion")
    private String ubicacion;

    private Set<String>habilidades;

}
