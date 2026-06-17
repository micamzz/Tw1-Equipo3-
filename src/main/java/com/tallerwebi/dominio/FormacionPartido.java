package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;

import javax.persistence.*;

@Entity
public class FormacionPartido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PartidoNBA partido;

    @ManyToOne
    private Jugador jugador;

    @ManyToOne
    private EquipoNBA equipo;

    @Enumerated(EnumType.STRING)
    private RolFormacion rol; //Titular o suplente

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PartidoNBA getPartido() {
        return partido;
    }

    public void setPartido(PartidoNBA partido) {
        this.partido = partido;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public EquipoNBA getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoNBA equipo) {
        this.equipo = equipo;
    }

    public RolFormacion getRol() {
        return rol;
    }

    public void setRol(RolFormacion rol) {
        this.rol = rol;
    }
}
