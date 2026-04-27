package com.lafachada.propiedad_service.Model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Region")
public class Region {
    @Id
    @Column(nullable = false, name = "id_region")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRegion;
    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "region")
    private List<Ciudad> ciudades;
}
