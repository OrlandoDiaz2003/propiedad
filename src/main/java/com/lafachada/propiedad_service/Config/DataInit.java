package com.lafachada.propiedad_service.Config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lafachada.propiedad_service.Model.Ciudad;
import com.lafachada.propiedad_service.Model.EstadoPropiedad;
import com.lafachada.propiedad_service.Model.Region;
import com.lafachada.propiedad_service.Model.TipoPropiedad;
import com.lafachada.propiedad_service.Repository.CiudadRepository;
import com.lafachada.propiedad_service.Repository.EstadoPropiedadRepository;
import com.lafachada.propiedad_service.Repository.RegionRepository;
import com.lafachada.propiedad_service.Repository.TipoPropiedadRepository;

@Component
public class DataInit implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final CiudadRepository ciudadRepository;
    private final TipoPropiedadRepository tipoPropiedadRepository;
    private final EstadoPropiedadRepository estadoPropiedadRepository;

    public DataInit(CiudadRepository ciudad, RegionRepository region, TipoPropiedadRepository tipoPropiedad,
            EstadoPropiedadRepository estadoPropiedadRepository) {
        this.ciudadRepository = ciudad;
        this.regionRepository = region;
        this.tipoPropiedadRepository = tipoPropiedad;
        this.estadoPropiedadRepository = estadoPropiedadRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (regionRepository.count() == 0) {
            List<String> regiones = obtenerRegiones();
            for (String nombre : regiones) {
                Region region = new Region();
                region.setNombre(nombre);
                regionRepository.save(region);
            }
            System.out.println("Regiones inicializadas.");
        }

        if (tipoPropiedadRepository.count() == 0) {
            List<String> tipos = obtenerTiposPropiedad();
            for (String nombre : tipos) {
                TipoPropiedad tipo = new TipoPropiedad();
                tipo.setNombre(nombre);
                tipoPropiedadRepository.save(tipo);
            }
            System.out.println("Tipos de propiedad inicializados.");
        }

        if (estadoPropiedadRepository.count() == 0) {
            List<String> estados = obtenerEstadosPropiedad();
            for (String nombre : estados) {
                EstadoPropiedad estado = new EstadoPropiedad();
                estado.setNombre(nombre);
                estadoPropiedadRepository.save(estado);
            }
            System.out.println("Estados de propiedad inicializados.");
        }

        if (ciudadRepository.count() == 0) {
            Map<String, List<String>> ciudadesPorRegion = obtenerCiudadesPorRegion();
            ciudadesPorRegion.forEach((nombreRegion, listaCiudades) -> {
                Region region = regionRepository.findByNombre(nombreRegion);
                if (region != null) {
                    for (String nombreCiudad : listaCiudades) {
                        Ciudad ciudad = new Ciudad();
                        ciudad.setNombre(nombreCiudad);
                        ciudad.setRegion(region);
                        ciudadRepository.save(ciudad);
                    }
                }
            });
        }
        ;
        System.out.println("Ciudades inicializadas.");
    }

    private List<String> obtenerTiposPropiedad() {
        return List.of("propiedad", "departamento");
    }

    private List<String> obtenerEstadosPropiedad() {
        return List.of("disponible", "vendido", "reservado", "pendiente");
    }

    private List<String> obtenerRegiones() {
        return List.of(
                "valparaiso", "metropolitana", "biobío", "coquimbo",
                "antofagasta", "o'higgins", "maule", "los lagos", "magallanes");
    }

    private Map<String, List<String>> obtenerCiudadesPorRegion() {
        Map<String, List<String>> map = new LinkedHashMap<>();

        map.put("valparaiso", List.of("viña del mar", "valparaíso"));
        map.put("metropolitana", List.of("santiago"));
        map.put("biobío", List.of("concepción"));
        map.put("antofagasta", List.of("antofagasta", "calama"));
        map.put("coquimbo", List.of("la serena", "coquimbo"));
        map.put("o'higgins", List.of("rancagua"));
        map.put("maule", List.of("talca"));
        map.put("los lagos", List.of("puerto montt"));
        map.put("magallanes", List.of("punta arenas"));
        return map;
    }
}