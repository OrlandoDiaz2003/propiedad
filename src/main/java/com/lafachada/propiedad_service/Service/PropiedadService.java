package com.lafachada.propiedad_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lafachada.propiedad_service.Dto.PropiedadModificarDto;
import com.lafachada.propiedad_service.Dto.PropiedadRespuestaDTO;
import com.lafachada.propiedad_service.Dto.PropiedadSolicitudDTO;
import com.lafachada.propiedad_service.Factory.PropiedadFactory;
import com.lafachada.propiedad_service.Model.Ciudad;
import com.lafachada.propiedad_service.Model.EstadoPropiedad;
import com.lafachada.propiedad_service.Model.Propiedad;
import com.lafachada.propiedad_service.Model.TipoPropiedad;
import com.lafachada.propiedad_service.Repository.CiudadRepository;
import com.lafachada.propiedad_service.Repository.EstadoPropiedadRepository;
import com.lafachada.propiedad_service.Repository.PropiedadRepository;
import com.lafachada.propiedad_service.Repository.TipoPropiedadRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

    @Autowired
    private PropiedadFactory propiedadFactory;

    public PropiedadRespuestaDTO obtenerPropiedadPorId(Integer id) {
        Propiedad propiedad = propiedadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado propiedad con ID " + id));

        PropiedadRespuestaDTO propiedadDTO = new PropiedadRespuestaDTO(propiedad);
        return propiedadDTO;
    }

    public void crearPropiedad(PropiedadSolicitudDTO dto) {
        TipoPropiedad tipoPropiedad = tipoPropiedadRepository.findById(dto.getIdTipoPropiedad())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se ha encontrado un tipo de propidad con id " + dto.getIdTipoPropiedad()));

        String nombreTipoPropiedad = tipoPropiedad.getNombre().toLowerCase();
        // Validamos que si el tipo de propiedad es hotel o departamento esta no tenga
        // una combinacion existente de direccion + numero de unidad
        if (nombreTipoPropiedad.contains("hotel") || nombreTipoPropiedad.contains("departamento")) {
            if (propiedadRepository.existsByDireccionAndNumeroUnidad(dto.getDireccion(), dto.getNumeroUnidad())) {
                throw new IllegalArgumentException(
                        "La direccion " + dto.getDireccion() + ", " + dto.getNumeroUnidad()
                                + " ya se encuentra registrada");
            }

            // validamos que si el tipo es una propidad tipo casa no tenga la misma
            // direccion de otra ya registrada
        } else {
            if (propiedadRepository.existsByDireccionAndNumeroUnidadIsNull(dto.getDireccion())) {
                throw new IllegalArgumentException(
                        "Ya existe una propiedad registrada en esa direccion");
            }
        }

        Ciudad ciudad = ciudadRepository.findById(dto.getIdCiudad()).orElseThrow(
                () -> new EntityNotFoundException(
                        "No se ha encontrado una ciudad con id " + dto.getIdCiudad()));

        EstadoPropiedad estadoPropiedad = estadoPropiedadRepository.findById(dto.getIdEstadoPropiedad()).orElseThrow(
                () -> new EntityNotFoundException(
                        "No se ha encontrado un estado de propidad con id " + dto.getIdEstadoPropiedad()));

        Propiedad propidadCreada = propiedadFactory.crearPropiedad(dto, ciudad, estadoPropiedad, tipoPropiedad);
        propiedadRepository.save(propidadCreada);
    }

    @Transactional
    public void modificarPropiedad(Integer id, PropiedadModificarDto dto) {
        Propiedad p = propiedadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado una propiedad con id " + id));

        if (dto.getPrecio() != null) p.setPrecio(dto.getPrecio());
        if (dto.getMetraje() != null) p.setMetraje(dto.getMetraje());

        if (dto.getCantidadBaños() != null) p.setCantidadBaños(dto.getCantidadBaños());
        if (dto.getCantidadHabitaciones() != null) p.setCantidadHabitaciones(dto.getCantidadHabitaciones());

        if(dto.getIdCliente() != null) p.setIdCliente(dto.getIdCliente());
        if (dto.getEstadoPropiedad() != null) {
            EstadoPropiedad nuevoEstadoPropiedad = estadoPropiedadRepository.findById(dto.getEstadoPropiedad())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No se ha encontrado un estado de propiedad con id " + dto.getEstadoPropiedad()));

            p.setEstadoPropiedad(nuevoEstadoPropiedad);
        }

        propiedadRepository.save(p);
    }

}
