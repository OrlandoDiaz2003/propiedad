package com.lafachada.propiedad_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.propiedad_service.Model.EstadoPropiedad;


public interface EstadoPropiedadRepository extends JpaRepository <EstadoPropiedad, Integer> {

}