package com.lafachada.propiedad_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lafachada.propiedad_service.Dto.PropiedadRespuestaDTO;
import com.lafachada.propiedad_service.Dto.PropiedadSolicitudDTO;
import com.lafachada.propiedad_service.Service.PropiedadService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("api/v0/propiedades")
@RestController
public class PropiedadController {

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping("/obtenerPropiedadPorId/{id}")
    public ResponseEntity<PropiedadRespuestaDTO> obtenerPropiedadPorId(@PathVariable Integer id) {
        PropiedadRespuestaDTO propiedad = propiedadService.obtenerPropiedadPorId(id);
        return ResponseEntity.ok(propiedad);
    }

    @PostMapping("/crear")
    public ResponseEntity<Void> crearPropiedad(@Valid @RequestBody PropiedadSolicitudDTO dto) {
        propiedadService.crearPropiedad(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}