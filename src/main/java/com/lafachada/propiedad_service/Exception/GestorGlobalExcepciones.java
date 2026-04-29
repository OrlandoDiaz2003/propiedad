package com.lafachada.propiedad_service.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("estado", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", errores);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> manejarCreacionPropiedad(IllegalArgumentException ex) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("fecha / hora", LocalDateTime.now());
        cuerpo.put("estado", HttpStatus.BAD_REQUEST.value());
        cuerpo.put("error", ex.getLocalizedMessage());
        return new ResponseEntity<>(cuerpo, HttpStatus.BAD_REQUEST);
    }

}