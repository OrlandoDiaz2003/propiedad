package com.lafachada.propiedad_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.propiedad_service.Model.TipoPropiedad;


public interface TipoPropiedadRepository extends JpaRepository <TipoPropiedad, Integer> {

    TipoPropiedad findByIdTipoPropiedad(int idTipoPropiedad);
}