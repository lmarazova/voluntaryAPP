package grupo7.volutarapp.config;

import grupo7.volutarapp.model.entity.ServicioEntity;
import grupo7.volutarapp.model.enums.Servicio;
import grupo7.volutarapp.repository.ServicioEntityRepository;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final ServicioEntityRepository servicioRepository;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Verificar si los datos ya están cargados
        if (servicioRepository.count() == 0) {
            // Cargar los servicios disponibles
            for (String servicio : obtenerServiciosDisponibles()) {
                ServicioEntity servicioEntity = new ServicioEntity();
                servicioEntity.setServicio(servicio);
                servicioRepository.save(servicioEntity);
            }
        }
    }

    private List<String> obtenerServiciosDisponibles() {
        List<String> servicios = new ArrayList<>();
        for (Servicio servicio : Servicio.values()) {
            servicios.add(servicio.toString()); // Convierte el enum a String
        }
        return servicios;
    }
}

