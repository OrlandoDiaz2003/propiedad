package com.lafachada.propiedad_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lafachada.propiedad_service.Dto.PropiedadBuscarDTO;
import com.lafachada.propiedad_service.Dto.PropiedadModificarDto;
import com.lafachada.propiedad_service.Dto.PropiedadRespuestaDTO;
import com.lafachada.propiedad_service.Dto.PropiedadSolicitudDTO;
import com.lafachada.propiedad_service.Service.PropiedadService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@Controller
@RequestMapping("api/v0/propiedad")
@RestController
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<PropiedadRespuestaDTO> obtenerPropiedadPorId(@PathVariable Integer id) {
        PropiedadRespuestaDTO propiedad = propiedadService.obtenerPropiedadPorId(id);
        return ResponseEntity.ok(propiedad);
    }

    @PostMapping("/crear")
    public ResponseEntity<Void> crearPropiedad(@Valid @RequestBody PropiedadSolicitudDTO dto) {
        propiedadService.crearPropiedad(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/modificar/{id}")
    public ResponseEntity<Void> modificarPropiedad(@PathVariable Integer id, @Valid @RequestBody PropiedadModificarDto dto) {
        propiedadService.modificarPropiedad(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<PropiedadRespuestaDTO>> buscar(@Valid PropiedadBuscarDTO dto, @PageableDefault(size = 10, page = 0) Pageable page){
        Page<PropiedadRespuestaDTO> resultado = propiedadService.buscar(dto, page);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPropiedad(@PathVariable Integer id) {
        propiedadService.eliminarPropiedad(id);
        return ResponseEntity.noContent().build();
    }
}