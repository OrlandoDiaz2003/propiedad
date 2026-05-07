package com.lafachada.propiedad_service.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.propiedad_service.Model.Ciudad;

public interface CiudadRepository extends JpaRepository <Ciudad,Integer> {

    Optional<Ciudad> findByIdCiudad(int idCiudad);
    Optional<Ciudad> findByNombre(String nombre);
}