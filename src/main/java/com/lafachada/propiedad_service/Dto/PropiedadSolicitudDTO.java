package com.lafachada.propiedad_service.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadSolicitudDTO {
    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccion;

    @NotNull(message = "Especifique la cantidad de baños")
    private Integer cantidadBaños;

    @NotNull(message = "Especifique la cantidad de habitaciones")
    @Min(value = 1, message = "La propiedad debe tener minimo una habitacion")
    private Integer cantidadHabitaciones;
    @NotNull(message = "Especifique el metraje de la vivienda")
    private Integer metraje;

    @NotNull(message = "Una propiedad tiene que estar asociada a un id de vendedor")
    @Min(value = 1, message = "El id de un vendedor tiene que ser mayor a 0")
    private Integer idVendedor;
    private Integer idCliente;

    @NotNull(message = "El tipo de propiedad debe ser especificado")
    @Min(value = 1, message = "El tipo de propiedad debe tener un id mayor a 0")
    @Max(value = 3, message = "El tipo de propiedad debe tener un id menor a 4")
    private Integer idTipoPropiedad;

    @NotNull(message = "El estado de la propiedad debe ser especificado")
    @Min(value = 1, message = "El estado de la propiedad debe tener un id mayor a 0")
    @Max(value = 4, message = "El estado de la propiedad debe tener un id menor a 5")
    private Integer idEstadoPropiedad;

    @NotNull(message = "La ciudad no puede venir vacia")
    private Integer idCiudad;

    private String numeroUnidad;
}
