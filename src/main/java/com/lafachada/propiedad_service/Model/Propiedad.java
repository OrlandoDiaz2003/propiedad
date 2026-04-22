package com.lafachada.propiedad_service.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "propiedad")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "propiedad_id")
    private int id;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private int idRegion;

    @Column(nullable = false)
    private int idCiudad;

    @Column(nullable = false)
    private int idEstado;

    @Column(nullable = false)
    private int idTipo;

    @Column(nullable = false)
    private int idVendedor;

    @Column(nullable = true)
    private int idCliente;
}