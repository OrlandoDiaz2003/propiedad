package com.lafachada.propiedad_service.Dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PropiedadModificarDTO {
    @Min(value = 1, message = "La propiedad debe tener minimo 1 habitacion")
    private Integer cantidadHabitaciones;

    @Min(value = 1, message = "La propiedad debe tener minimo 1 baño")
    private Integer cantidadBaños;

    @Min(value = 1, message = "El metraje tiene que ser mayor a 0")
    private Integer metraje;

    @Min(value = 1, message = "El id de estado de propiedad tiene que ser mayor a 0")
    private Integer estadoPropiedad;

    @Min(value = 1, message = "El id de un cliente tiene que ser mayor a 0")
    private Integer idCliente;
}
