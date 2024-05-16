package grupo7.volutarapp.repository;

import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import grupo7.volutarapp.model.enums.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsuarioNecesitadoRepository extends JpaRepository<UsuarioNecesitado, Long> {

    UsuarioNecesitado findByNombreUsuario(String nombreUsuario);

   /* Set<UsuarioNecesitado> findByNecesidadAndUbicacion(ServicioEntity servicio, Ubicacion ubicacion); tengo este query y funciona bien. quiero probar añadir otra condición y por eso lo dejo en comentario*/
    Set<UsuarioNecesitado>findByNecesidadAndUbicacionAndSolicitudNotNull(ServicioEntity servicio, Ubicacion ubicacion);
}