package com.lafachada.propiedad_service.Dto;

import java.util.ArrayList;
import java.util.List;

import com.lafachada.propiedad_service.Model.Foto;
import com.lafachada.propiedad_service.Model.Propiedad;

import lombok.Data;

@Data
public class PropiedadRespuestaDTO {
    private Integer id;

    private int cantidadBaños;
    private int cantidadHabitaciones;
    private int metraje;
    private double precio;

    private String direccion;
    private String estado;
    private String tipo;
    private String ciudad;
    private String numeroUnidad;

    private List<String> fotosUrl = new ArrayList<>();

    public PropiedadRespuestaDTO(Propiedad p) {
        this.id                   = p.getIdPropiedad();
        this.cantidadBaños        = p.getCantidadBaños();
        this.cantidadHabitaciones = p.getCantidadHabitaciones();
        this.metraje              = p.getMetraje();
        this.precio               = p.getPrecio();
        this.direccion            = p.getDireccion();
        this.estado               = p.getEstadoPropiedad().getNombre();
        this.tipo                 = p.getTipoPropiedad().getNombre();
        this.ciudad               = p.getCiudad().getNombre();
        this.numeroUnidad         = p.getNumeroUnidad();

        if(!p.getFotos().isEmpty()) {
            for(Foto foto: p.getFotos()) {
                this.fotosUrl.add(foto.getUrl());
            }
        }
    }
}