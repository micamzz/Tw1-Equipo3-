package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class EquipoNBA {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String escudoImagen;

    @ManyToOne
    private Temporada temporada;


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

    public String getEscudoImagen() {
        return escudoImagen;
    }

    public void setEscudoImagen(String escudoImagen) {
        this.escudoImagen = escudoImagen;

    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }
}
