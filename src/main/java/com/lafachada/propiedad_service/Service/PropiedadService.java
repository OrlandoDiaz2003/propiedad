package com.lafachada.propiedad_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;

import com.lafachada.propiedad_service.Dto.PropiedadBuscarDTO;
import com.lafachada.propiedad_service.Dto.PropiedadModificarDTO;
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
import com.lafachada.propiedad_service.Specs.PropiedadSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PropiedadService {

    private PropiedadRepository propiedadRepository;
    private EstadoPropiedadRepository estadoPropiedadRepository;
    private CiudadRepository ciudadRepository;
    private TipoPropiedadRepository tipoPropiedadRepository;
    private PropiedadFactory propiedadFactory;

    public PropiedadService(PropiedadRepository propiedadRepository,
            EstadoPropiedadRepository estadoPropiedadRepository, CiudadRepository ciudadRepository,
            TipoPropiedadRepository tipoPropiedadRepository, PropiedadFactory propiedadFactory) {
        this.propiedadRepository = propiedadRepository;
        this.estadoPropiedadRepository = estadoPropiedadRepository;
        this.ciudadRepository = ciudadRepository;
        this.tipoPropiedadRepository = tipoPropiedadRepository;
        this.propiedadFactory = propiedadFactory;
    }

    public PropiedadRespuestaDTO obtenerPropiedadPorId(Integer id) {
        Propiedad propiedad = propiedadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado propiedad con ID " + id));

        PropiedadRespuestaDTO propiedadDTO = new PropiedadRespuestaDTO(propiedad);
        return propiedadDTO;
    }

    @Transactional
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
    public void modificarPropiedad(Integer id, PropiedadModificarDTO dto) {
        Propiedad p = propiedadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado una propiedad con id " + id));

        if (dto.getPrecio() != null)
            p.setPrecio(dto.getPrecio());
        if (dto.getMetraje() != null)
            p.setMetraje(dto.getMetraje());

        if (dto.getCantidadBaños() != null)
            p.setCantidadBaños(dto.getCantidadBaños());
        if (dto.getCantidadHabitaciones() != null)
            p.setCantidadHabitaciones(dto.getCantidadHabitaciones());

        if (dto.getIdCliente() != null)
            p.setIdCliente(dto.getIdCliente());
        if (dto.getEstadoPropiedad() != null) {
            EstadoPropiedad nuevoEstadoPropiedad = estadoPropiedadRepository.findById(dto.getEstadoPropiedad())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No se ha encontrado un estado de propiedad con id " + dto.getEstadoPropiedad()));
            p.setEstadoPropiedad(nuevoEstadoPropiedad);
        }
        propiedadRepository.save(p);
    }

    public Page<PropiedadRespuestaDTO> buscar(PropiedadBuscarDTO dto, Pageable pageable) {
        TipoPropiedad tipoPropiedad = null;
        if (dto.getTipoPropiedad() != null) {
            tipoPropiedad = tipoPropiedadRepository.findById(dto.getTipoPropiedad())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No se ha encontrado tipo de propiedad con id " + dto.getTipoPropiedad()));
        }
        String ciudad = null;
        if (dto.getCiudad() != null) {
            if (!dto.getCiudad().isBlank()) {
                ciudad = dto.getCiudad();
            }
        }

        dto.validarRangos();
        Specification<Propiedad> spec = Specification.where(PropiedadSpecs.propiedadEnCiudad(ciudad))
                .and(PropiedadSpecs.propiedadCantidadHabitaciones(dto.getCantidadHabitaciones()))
                .and(PropiedadSpecs.propiedadTipoPropiedad(tipoPropiedad))
                .and(PropiedadSpecs.propiedadPrecioMax(dto.getPrecioMax()))
                .and(PropiedadSpecs.propiedadPrecioMin(dto.getPrecioMin()))
                .and(PropiedadSpecs.propiedadMetrajeMax(dto.getMetrajeMax()))
                .and(PropiedadSpecs.propiedadMetrajeMin(dto.getMetrajeMin()));
        Page<Propiedad> entidades = propiedadRepository.findAll(spec, pageable);

        return entidades.map(propidades -> new PropiedadRespuestaDTO(propidades));
    }

    public void eliminarPropiedad(Integer id) {
        if (!propiedadRepository.existsById(id)) {
            throw new EntityNotFoundException("No se ha encontrado una propiedad con id " + id);
        }
        propiedadRepository.deleteById(id);
    }
}