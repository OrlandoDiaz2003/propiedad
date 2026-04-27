package com.lafachada.propiedad_service.Exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GestorGlobalExcepciones {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> manejarEntidadNoEncontrada(EntityNotFoundException ex) {
        Map<String, Object>  cuerpo = new LinkedHashMap<>();
        cuerpo.put("fecha_hora", LocalDateTime.now());
        cuerpo.put("estado", HttpStatus.NOT_FOUND);
        cuerpo.put("error", "no encontrado");
        cuerpo.put("mensaje", ex.getMessage());

        return new ResponseEntity<>(cuerpo, HttpStatus.NOT_FOUND);
    }

}