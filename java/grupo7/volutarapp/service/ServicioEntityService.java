package grupo7.volutarapp.service;

import grupo7.volutarapp.model.enums.Servicio;
import org.springframework.stereotype.Service;

@Service
public class ServicioEntityService {
    public Servicio encontrarServicioPorNombre(String nombreServicio) {
        // Convertir el nombre del servicio a mayúsculas para evitar problemas de capitalización
        nombreServicio = nombreServicio.toUpperCase();

        // Recorrer todos los valores del enum Servicio
        for (Servicio servicio : Servicio.values()) {
            // Si el nombre del servicio coincide con el nombre proporcionado, retornar el servicio
            if (servicio.name().equals(nombreServicio)) {
                return servicio;
            }
        }

        // Si no se encuentra ningún servicio que coincida con el nombre proporcionado, retornar null
        return null;
    }
}
