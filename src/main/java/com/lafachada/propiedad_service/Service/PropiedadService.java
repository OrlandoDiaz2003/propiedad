package com.lafachada.propiedad_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lafachada.propiedad_service.Dto.PropiedadRespuestaDTO;
import com.lafachada.propiedad_service.Model.Propiedad;
import com.lafachada.propiedad_service.Repository.PropiedadRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    public PropiedadRespuestaDTO obtenerPropiedadPorId(Integer id) {
        Propiedad propiedad = propiedadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado propiedad con ID " + id));

        PropiedadRespuestaDTO propiedadDTO = new PropiedadRespuestaDTO(propiedad);
        return propiedadDTO;
    }

}
