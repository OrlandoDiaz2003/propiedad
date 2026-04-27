package com.lafachada.propiedad_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.propiedad_service.Model.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Integer> {
}