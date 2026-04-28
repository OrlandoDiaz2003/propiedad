package com.lafachada.propiedad_service.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tipo_propiedad")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoPropiedad {
    /*1: Propiedad */
    /*2: Departamento*/
    /*3: Hotel*/
    @Id
    @Column(name = "id_tipo_propiedad")
    private int idTipoPropiedad;

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "tipoPropiedad")
    private List<Propiedad> propiedades;
}