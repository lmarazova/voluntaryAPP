package grupo7.volutarapp.repository;

import grupo7.volutarapp.model.entity.UsuarioVoluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioVoluntarioRepository extends JpaRepository<UsuarioVoluntario, Long> {

    UsuarioVoluntario findByNombreUsuario(String nombreUsuario);
}
