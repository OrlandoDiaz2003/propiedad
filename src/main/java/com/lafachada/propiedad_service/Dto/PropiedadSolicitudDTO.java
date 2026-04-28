package com.lafachada.propiedad_service.Dto;

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

    @NotNull(message = "El precio no puede estar vacio")
    @Min(value = 1, message = "El precio deber mayor a 0")
    private Double precio;

    private Integer idVendedor;
    private Integer idCliente;

    @NotNull(message = "El tipo de propiedad debe ser especificado")
    private Integer idTipoPropiedad;

    @NotNull(message = "El estado de la propiedad debe ser especificado")
    private Integer idEstadoPropiedad;

    @NotNull(message = "La ciudad no puede venir vacia")
    private Integer idCiudad;

    private String numeroUnidad;
}
