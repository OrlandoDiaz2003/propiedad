package com.lafachada.propiedad_service.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lafachada.propiedad_service.Dto.PropiedadBuscarDTO;
import com.lafachada.propiedad_service.Dto.PropiedadModificarDTO;
import com.lafachada.propiedad_service.Dto.PropiedadRespuestaDTO;
import com.lafachada.propiedad_service.Service.PropiedadService;

import jakarta.persistence.EntityNotFoundException;

@WebMvcTest(PropiedadController.class)
class PropiedadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PropiedadService propiedadService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/v0/propiedad";

    @Test
    void obtenerPropiedadPorId_CuandoExiste_DebeRetornar200OK() throws Exception {
        Integer id = 1;
        PropiedadRespuestaDTO respuestaDTO = new PropiedadRespuestaDTO();

        when(propiedadService.obtenerPropiedadPorId(id)).thenReturn(respuestaDTO);

        mockMvc.perform(get(BASE_URL + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPropiedadPorId_CuandoNoExiste_DebeManejarLaExcepcion() throws Exception {
        Integer id = 99;

        when(propiedadService.obtenerPropiedadPorId(id))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        mockMvc.perform(get(BASE_URL + "/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void modificarPropiedad_ConDatosValidos_DebeRetornar202Accepted() throws Exception {
        Integer id = 1;
        PropiedadModificarDTO modificarDTO = new PropiedadModificarDTO();

        doNothing().when(propiedadService).modificarPropiedad(eq(id), any(PropiedadModificarDTO.class));

        mockMvc.perform(patch(BASE_URL + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificarDTO)))
                .andExpect(status().isAccepted());
    }

    @Test
    void buscar_DebeRetornarPaginaY200OK() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PropiedadRespuestaDTO> paginaVacia = new PageImpl<>(List.of());

        when(propiedadService.buscar(any(PropiedadBuscarDTO.class), any(Pageable.class))).thenReturn(paginaVacia);

        mockMvc.perform(get(BASE_URL)
                .param("ciudad", "Santiago")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void eliminarPropiedad_DebeRetornar204NoContent() throws Exception {
        Integer id = 1;
        doNothing().when(propiedadService).eliminarPropiedad(id);

        mockMvc.perform(delete(BASE_URL + "/{id}", id))
                .andExpect(status().isNoContent());
    }
}