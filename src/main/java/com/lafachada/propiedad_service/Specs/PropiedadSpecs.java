package com.lafachada.propiedad_service.Specs;

import org.springframework.data.jpa.domain.PredicateSpecification;

import com.lafachada.propiedad_service.Model.Ciudad;
import com.lafachada.propiedad_service.Model.Propiedad;
import com.lafachada.propiedad_service.Model.TipoPropiedad;

public class PropiedadSpecs {
    public static PredicateSpecification<Propiedad> propiedadEnCiudad(Ciudad ciudad) {
        return (from, builder) -> {
            if (ciudad == null || ciudad.getNombre() == null) {
                return null;
            }
            return builder.like(from.get("ciudad").get("nombre"), ciudad.getNombre());
        };
    }

    public static PredicateSpecification<Propiedad> propiedadPrecioMin(Double min) {
        return (from, builder) -> {
            if (min == null || min <= 0) {
                return null;
            }
            return builder.greaterThanOrEqualTo(from.get("precio"), min);
        };
    }

    public static PredicateSpecification<Propiedad> propiedadPrecioMax(Double max) {
        return (from, builder) -> {
            if (max == null || max <= 0) {
                return null;
            }
            return builder.lessThanOrEqualTo(from.get("precio"), max);
        };
    }

    public static PredicateSpecification<Propiedad> propiedadMetrajeMin(Integer min) {
        return (from, builder) -> {
            if (min == null || min <= 0) {
                return null;
            }
            return builder.greaterThanOrEqualTo(from.get("metraje"), min);
        };
    }

    public static PredicateSpecification<Propiedad> propiedadMetrajeMax(Integer max) {
        return (from, builder) -> {
            if (max == null || max <= 0) {
                return null;
            }
            return builder.lessThanOrEqualTo(from.get("metraje"), max);
        };
    }

    public static PredicateSpecification<Propiedad> propiedadCantidadHabitaciones(Integer cantidadHabitaciones) {
        return (from, builder) -> {
            if (cantidadHabitaciones == null || cantidadHabitaciones <= 0) {
                return null;
            }
            return builder.equal(from.get("cantidadHabitaciones"), cantidadHabitaciones);
        };
    }

    public static PredicateSpecification<Propiedad> propiedadTipoPropiedad(TipoPropiedad tipoPropiedad) {
        return (from, builder) -> {
            if (tipoPropiedad == null || tipoPropiedad.getNombre().isEmpty()) {
                return null;
            }
            tipoPropiedad.getNombre().toLowerCase();
            return builder.equal(from.get("tipoPropiedad"), tipoPropiedad);
        };
    }
}
