package grupo7.volutarapp.repository;

import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.enums.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioEntityRepository extends JpaRepository<ServicioEntity, Long> {


    ServicioEntity findByServicio(String habilidad);
}
