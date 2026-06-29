package com.lafachada.propiedad_service.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.lafachada.propiedad_service.Model.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Integer>, JpaSpecificationExecutor<Propiedad>{
    boolean existsByDireccionAndNumeroUnidadIsNull(String direccion);
    boolean existsByDireccionAndNumeroUnidad(String direccion, String numeroUnidad);
    @Query("SELECT p.idPropiedad FROM Propiedad p WHERE p.ciudad.idCiudad = :idCiudad")
    List<Integer> buscarPorCiudad(Integer idCiudad);
}