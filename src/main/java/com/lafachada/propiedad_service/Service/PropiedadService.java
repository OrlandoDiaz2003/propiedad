package com.lafachada.propiedad_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lafachada.propiedad_service.Dto.PropiedadRespuestaDTO;
import com.lafachada.propiedad_service.Dto.PropiedadSolicitudDTO;
import com.lafachada.propiedad_service.Model.Propiedad;
import com.lafachada.propiedad_service.Repository.CiudadRepository;
import com.lafachada.propiedad_service.Repository.EstadoPropiedadRepository;
import com.lafachada.propiedad_service.Repository.PropiedadRepository;
import com.lafachada.propiedad_service.Repository.TipoPropiedadRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;
    @Autowired
    private EstadoPropiedadRepository estadoPropiedadRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private TipoPropiedadRepository tipoPropiedadRepository;

    public PropiedadRespuestaDTO obtenerPropiedadPorId(Integer id) {
        Propiedad propiedad = propiedadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado propiedad con ID " + id));

        PropiedadRespuestaDTO propiedadDTO = new PropiedadRespuestaDTO(propiedad);
        return propiedadDTO;
    }

    public void subirPropiedad(PropiedadSolicitudDTO dto) {
        Propiedad propiedad = new Propiedad();

        propiedad.setDireccion(dto.getDireccion());
        propiedad.setCantidadBaños(dto.getCantidadBaños());
        propiedad.setCantidadHabitaciones(dto.getCantidadHabitaciones());
        propiedad.setMetraje(dto.getMetraje());
        propiedad.setPrecio(dto.getPrecio());
        propiedad.setIdVendedor(dto.getIdVendedor());
        propiedad.setIdCliente(dto.getIdCliente());

        propiedad.setEstadoPropiedad(estadoPropiedadRepository.findById(dto.getIdEstadoPropiedad())
                .orElseThrow(() -> new EntityNotFoundException("Estado de propiedad no valido intentelo otra vez")));

        propiedad.setCiudad(ciudadRepository.findById(dto.getIdCiudad())
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada")));

        propiedad.setTipoPropiedad(tipoPropiedadRepository.findById(dto.getIdTipoPropiedad())
                .orElseThrow(() -> new EntityNotFoundException("Este tipo de propiedad no es valido")));

        propiedadRepository.save(propiedad);
    }

}
