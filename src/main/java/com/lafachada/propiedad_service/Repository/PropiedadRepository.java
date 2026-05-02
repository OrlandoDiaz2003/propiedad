package com.lafachada.propiedad_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lafachada.propiedad_service.Model.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Integer>, JpaSpecificationExecutor<Propiedad>{
    boolean existsByDireccionAndNumeroUnidadIsNull(String direccion);
    boolean existsByDireccionAndNumeroUnidad(String direccion, String numeroUnidad);
}