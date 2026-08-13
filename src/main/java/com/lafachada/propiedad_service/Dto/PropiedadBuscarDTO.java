package com.lafachada.propiedad_service.Dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PropiedadBuscarDTO {
    private String direccion;

    @PositiveOrZero(message="La cantidad de habitaciones no puede ser menor a 0")
    private Integer cantidadHabitaciones;

    @PositiveOrZero(message="La cantidad de baños no puede ser menor a 0")
    private String ciudad;

    @PositiveOrZero(message = "El metraje no puede ser negativo")
    private Integer metrajeMin;

    @PositiveOrZero(message = "El metraje no puede ser negativo")
    private Integer metrajeMax;

    private Integer tipoPropiedad;

    public boolean esRangoValido(Number min, Number max) {
        if(min == null || max == null)  return true;
        return min.doubleValue() < max.doubleValue();
    }

    public void validarRangos(){
        if(!esRangoValido(this.metrajeMin, this.metrajeMax)){
            throw new IllegalArgumentException("El rango de metraje no es valido");
        }
    }
}
