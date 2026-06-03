package com.lafachada.propiedad_service.Specs;

import org.springframework.data.jpa.domain.Specification;

import com.lafachada.propiedad_service.Model.Propiedad;
import com.lafachada.propiedad_service.Model.TipoPropiedad;

public class PropiedadSpecs {
    public static Specification<Propiedad> propiedadEnCiudad(String ciudad) {
        return (from, query, builder) -> {
            if (ciudad == null || ciudad.isBlank()) return null;
            return builder.like(from.get("ciudad").get("nombre"), "%" + ciudad.toLowerCase() + "%");
        };
    }

    public static Specification<Propiedad> propiedadMetrajeMin(Integer min) {
        return (from, query, builder) -> {
            if (min == null || min <= 0) return null;
            return builder.greaterThanOrEqualTo(from.get("metraje"), min);
        };
    }

    public static Specification<Propiedad> propiedadMetrajeMax(Integer max) {
        return (from, query, builder) -> {
            if (max == null || max <= 0) return null;
            return builder.lessThanOrEqualTo(from.get("metraje"), max);
        };
    }

    public static Specification<Propiedad> propiedadCantidadHabitaciones(Integer cantidadHabitaciones) {
        return (from, query, builder) -> {
            if (cantidadHabitaciones == null || cantidadHabitaciones <= 0) return null;
            return builder.equal(from.get("cantidadHabitaciones"), cantidadHabitaciones);
        };
    }

    public static Specification<Propiedad> propiedadTipoPropiedad(TipoPropiedad tipoPropiedad) {
        return (from, query, builder) -> {
            if (tipoPropiedad == null || tipoPropiedad.getNombre().isEmpty()) return null;
            tipoPropiedad.getNombre().toLowerCase();
            return builder.equal(from.get("tipoPropiedad"), tipoPropiedad);
        };
    }
}
