package com.lafachada.propiedad_service.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
class PropiedadServiceTest {

    @Mock
    private PropiedadRepository propiedadRepository;
    @Mock
    private EstadoPropiedadRepository estadoPropiedadRepository;
    @Mock
    private CiudadRepository ciudadRepository;
    @Mock
    private TipoPropiedadRepository tipoPropiedadRepository;
    @Mock
    private PropiedadFactory propiedadFactory;

    @InjectMocks
    private PropiedadService propiedadService;

    @Test
    void obtenerPropiedadPorId_CuandoExiste_DebeRetornarDTO() {
        Integer id = 1;

        EstadoPropiedad estadoMock = new EstadoPropiedad();
        estadoMock.setNombre("Disponible");

        TipoPropiedad tipoMock = new TipoPropiedad();
        tipoMock.setNombre("Casa");

        Ciudad ciudadMock = new Ciudad();
        ciudadMock.setNombre("Santiago");

        Propiedad propiedadMock = new Propiedad();
        propiedadMock.setDireccion("Av. Siempre Viva 742");
        propiedadMock.setEstadoPropiedad(estadoMock);
        propiedadMock.setTipoPropiedad(tipoMock);
        propiedadMock.setCiudad(ciudadMock);

        when(propiedadRepository.findById(id)).thenReturn(Optional.of(propiedadMock));

        PropiedadRespuestaDTO resultado = propiedadService.obtenerPropiedadPorId(id);

        assertNotNull(resultado);
        assertEquals("Av. Siempre Viva 742", resultado.getDireccion());
        verify(propiedadRepository, times(1)).findById(id);

    }

    @Test
    void obtenerPropiedadPorId_CuandoNoExiste_DebeLanzarEntityNotFoundException() {
        Integer id = 99;
        when(propiedadRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            propiedadService.obtenerPropiedadPorId(id);
        });

        assertEquals("No se ha encontrado propiedad con ID 99", exception.getMessage());
    }

    @Test
    void crearPropiedad_TipoDepartamentoYDireccionDuplicada_DebeLanzarIllegalArgumentException() {
        PropiedadSolicitudDTO dto = new PropiedadSolicitudDTO();
        dto.setIdTipoPropiedad(1);
        dto.setDireccion("Av. Vitacura 1234");
        dto.setNumeroUnidad("Apt 402");

        TipoPropiedad tipoPropiedad = new TipoPropiedad();
        tipoPropiedad.setNombre("Departamento");

        when(tipoPropiedadRepository.findById(1)).thenReturn(Optional.of(tipoPropiedad));
        when(propiedadRepository.existsByDireccionAndNumeroUnidad(dto.getDireccion(), dto.getNumeroUnidad()))
                .thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.crearPropiedad(dto);
        });

        assertTrue(exception.getMessage().contains("ya se encuentra registrada"));
        verify(ciudadRepository, never()).findById(any());
        verify(propiedadRepository, never()).save(any());
    }

    @Test
    void crearPropiedad_TipoCasaYDireccionDuplicada_DebeLanzarIllegalArgumentException() {
        PropiedadSolicitudDTO dto = new PropiedadSolicitudDTO();
        dto.setIdTipoPropiedad(2);
        dto.setDireccion("Pasaje Los Alerces 450");

        TipoPropiedad tipoPropiedad = new TipoPropiedad();
        tipoPropiedad.setNombre("Casa");

        when(tipoPropiedadRepository.findById(2)).thenReturn(Optional.of(tipoPropiedad));
        when(propiedadRepository.existsByDireccionAndNumeroUnidadIsNull(dto.getDireccion())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            propiedadService.crearPropiedad(dto);
        });

        assertEquals("Ya existe una propiedad registrada en esa direccion", exception.getMessage());
    }

    @Test
    void crearPropiedad_FlujoExitoso_DebeRetornarPropiedadGuardada() {
        PropiedadSolicitudDTO dto = new PropiedadSolicitudDTO();
        dto.setIdTipoPropiedad(2);
        dto.setIdCiudad(10);
        dto.setIdEstadoPropiedad(5);
        dto.setDireccion("Calle Nueva 777");

        TipoPropiedad tipoPropiedad = new TipoPropiedad();
        tipoPropiedad.setNombre("Casa");

        Ciudad ciudad = new Ciudad();
        EstadoPropiedad estado = new EstadoPropiedad();
        Propiedad propiedadCreadaPorFactory = new Propiedad();
        Propiedad propiedadGuardadaBD = new Propiedad();

        when(tipoPropiedadRepository.findById(2)).thenReturn(Optional.of(tipoPropiedad));
        when(propiedadRepository.existsByDireccionAndNumeroUnidadIsNull(dto.getDireccion())).thenReturn(false);
        when(ciudadRepository.findById(10)).thenReturn(Optional.of(ciudad));
        when(estadoPropiedadRepository.findById(5)).thenReturn(Optional.of(estado));

        when(propiedadFactory.crearPropiedad(dto, ciudad, estado, tipoPropiedad)).thenReturn(propiedadCreadaPorFactory);
        when(propiedadRepository.save(propiedadCreadaPorFactory)).thenReturn(propiedadGuardadaBD);

        Propiedad resultado = propiedadService.crearPropiedad(dto);

        assertNotNull(resultado);
        verify(propiedadRepository, times(1)).save(propiedadCreadaPorFactory);
    }

    @Test
    void eliminarPropiedad_CuandoExiste_DebeLlamarAlRepository() {
        Integer id = 1;
        when(propiedadRepository.existsById(id)).thenReturn(true);

        propiedadService.eliminarPropiedad(id);

        verify(propiedadRepository, times(1)).deleteById(id);
    }

    @Test
    void eliminarPropiedad_CuandoNoExiste_DebeLanzarEntityNotFoundException() {
        Integer id = 99;
        when(propiedadRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            propiedadService.eliminarPropiedad(id);
        });

        verify(propiedadRepository, never()).deleteById(any());
    }
}