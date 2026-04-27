package com.lafachada.propiedad_service.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.propiedad_service.Model.EstadoPropiedad;


public interface EstadoPropiedadRepository extends JpaRepository <EstadoPropiedad, Integer> {

    @Override
    default Optional<EstadoPropiedad> findById(Integer id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}