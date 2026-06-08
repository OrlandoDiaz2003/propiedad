package com.lafachada.propiedad_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lafachada.propiedad_service.Model.Region;
import java.util.List;


public interface RegionRepository extends JpaRepository <Region,Integer>  {

    Region findByNombre(String nombre);
}
