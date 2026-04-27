package com.lafachada.propiedad_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.propiedad_service.Model.Ciudad;

public interface CiudadRepository extends JpaRepository <Ciudad,Integer> {

    Ciudad findByIdCiudad(int idCiudad);
}