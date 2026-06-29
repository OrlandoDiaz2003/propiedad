package com.lafachada.propiedad_service.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lafachada.propiedad_service.Dto.PropiedadBuscarDTO;
import com.lafachada.propiedad_service.Dto.PropiedadModificarDTO;
import com.lafachada.propiedad_service.Dto.PropiedadRespuestaDTO;
import com.lafachada.propiedad_service.Dto.PropiedadSolicitudDTO;
import com.lafachada.propiedad_service.Model.Propiedad;
import com.lafachada.propiedad_service.Service.PropiedadService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RequestMapping("api/v0/propiedad")
@RestController
public class PropiedadController {

    private final PropiedadService propiedadService;

    public PropiedadController(PropiedadService propiedadService) {
        this.propiedadService = propiedadService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropiedadRespuestaDTO> obtenerPropiedadPorId(@PathVariable Integer id) {
        PropiedadRespuestaDTO propiedad = propiedadService.obtenerPropiedadPorId(id);
        return ResponseEntity.ok(propiedad);
    }

    @PostMapping
    public ResponseEntity<Propiedad> crearPropiedad(@Valid @RequestBody PropiedadSolicitudDTO dto) {
        Propiedad pro = propiedadService.crearPropiedad(dto);
        return ResponseEntity.ok(pro);
    }

    @GetMapping("ciudad/{id}")
    public ResponseEntity<List<Integer>> buscarPorCiudad(@PathVariable Integer id) {
        List<Integer> propiedades = propiedadService.buscarPorCiudad(id);
        return ResponseEntity.ok(propiedades);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> modificarPropiedad(@PathVariable Integer id, @Valid @RequestBody PropiedadModificarDTO dto) {
        propiedadService.modificarPropiedad(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping
    public ResponseEntity<Page<PropiedadRespuestaDTO>> buscar(@Validated PropiedadBuscarDTO dto, @PageableDefault(size = 10, page = 0) Pageable page){
        Page<PropiedadRespuestaDTO> resultado = propiedadService.buscar(dto, page);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPropiedad(@PathVariable Integer id) {
        propiedadService.eliminarPropiedad(id);
        return ResponseEntity.noContent().build();
    }
}