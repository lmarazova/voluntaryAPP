package grupo7.volutarapp.repository;

import grupo7.volutarapp.model.entity.Solicitud;
import grupo7.volutarapp.model.entity.UsuarioNecesitado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    Solicitud findByUsuarioNecesitado(UsuarioNecesitado usuarioNecesitado);
    @Query("SELECT s FROM Solicitud s WHERE s.id = :id")
    Solicitud findSolicitudById(@Param("id") Long id);

}

