package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FuturosPartidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(fetch = FetchType.EAGER)
    private List<PartidoNBA> partidos;

    public FuturosPartidos() {
        this.partidos = new ArrayList<>();
    }

    public FuturosPartidos(String nombre) {
        this.nombre = nombre;
        this.partidos = new ArrayList<>();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PartidoNBA> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<PartidoNBA> partidos) {
        this.partidos = partidos;
    }

    public void agregarPartido(PartidoNBA partido) {
        this.partidos.add(partido);
    }

    public void removerPartido(PartidoNBA partido) {
        this.partidos.remove(partido);
    }
}
