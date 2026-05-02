package com.lafachada.propiedad_service.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "estado_propiedad")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstadoPropiedad {
    /*

    1. Disponible
    2. Vendido
    3. Reservado
    4. Pendiente

    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstadoPropiedad;

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "estadoPropiedad")
    @JsonIgnore
    private List<Propiedad> propiedades;

}
