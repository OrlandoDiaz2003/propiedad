package com.lafachada.propiedad_service.Model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Propiedad")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Propiedad {

    // Atributos de propiedad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "propiedad_id")
    private Integer idPropiedad;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_propiedad", nullable = false)
    private EstadoPropiedad estadoPropiedad;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tipo_propiedad")
    private TipoPropiedad tipoPropiedad;

    @Column(nullable = false, name = "cantidad_baños")
    private int cantidadBaños;

    @Column(nullable = false, name = "cantidad_habitaciones")
    private int cantidadHabitaciones;

    @Column(nullable = false)
    private int metraje;

    @OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos = new ArrayList<>();

    // Atributos externos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciudad", nullable = false)
    private Ciudad ciudad;

    @Column(nullable = false, name = "id_vendedor")
    private int idVendedor;

    @Column(nullable = true, name = "id_cliente")
    private Integer idCliente;

    @Column(nullable = true, name = "numero_unidad")
    private String numeroUnidad;

    public void setId(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}