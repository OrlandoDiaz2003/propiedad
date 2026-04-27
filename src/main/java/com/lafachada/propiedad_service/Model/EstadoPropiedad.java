package com.lafachada.propiedad_service.Model;

import java.util.List;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstadoPropiedad;

    @Column(nullable = false, unique = true)
    private String nombre; // DISPONIBLE, VENDIDO, RERSERVADA

    @OneToMany(mappedBy = "estadoPropiedad")
    private List<Propiedad> propiedades;

}
