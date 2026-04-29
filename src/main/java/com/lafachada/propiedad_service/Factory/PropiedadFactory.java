package com.lafachada.propiedad_service.Factory;

import org.springframework.stereotype.Component;

import com.lafachada.propiedad_service.Dto.PropiedadSolicitudDTO;
import com.lafachada.propiedad_service.Model.Ciudad;
import com.lafachada.propiedad_service.Model.EstadoPropiedad;
import com.lafachada.propiedad_service.Model.Propiedad;
import com.lafachada.propiedad_service.Model.TipoPropiedad;

@Component
public class PropiedadFactory {

    public Propiedad crearPropiedad(PropiedadSolicitudDTO dto, Ciudad ciudad, EstadoPropiedad estado,
            TipoPropiedad tipoPropiedad) {

        validarTipo(dto, tipoPropiedad);

        Propiedad propiedad = new Propiedad();
        propiedad.setDireccion(dto.getDireccion());
        propiedad.setCantidadBaños(dto.getCantidadBaños());
        propiedad.setCantidadHabitaciones(dto.getCantidadHabitaciones());
        propiedad.setCiudad(ciudad);
        propiedad.setTipoPropiedad(tipoPropiedad);
        propiedad.setEstadoPropiedad(estado);
        propiedad.setNumeroUnidad(dto.getNumeroUnidad());
        propiedad.setMetraje(dto.getMetraje());
        propiedad.setIdVendedor(dto.getIdVendedor());
        propiedad.setPrecio(dto.getPrecio());
        return propiedad;
    }

    private void validarTipo(PropiedadSolicitudDTO dto, TipoPropiedad tipo) {
        String nombreTipo = tipo.getNombre().toLowerCase();

        boolean esPropiedadConUnidad = (nombreTipo.contains("departamento")) || (nombreTipo.contains("hotel"));
        boolean unidadEstaVacia = (dto.getNumeroUnidad() == null) || (dto.getNumeroUnidad().isBlank());

        if(!esPropiedadConUnidad) {
            dto.setNumeroUnidad(null);
        }
        if (esPropiedadConUnidad && unidadEstaVacia) {
            throw new IllegalArgumentException(
                    "Las propiedades tipo " + nombreTipo + " deben tener un numero de unidad/habitacion");
        }
    }

}
