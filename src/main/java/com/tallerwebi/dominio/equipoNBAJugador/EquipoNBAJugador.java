package com.tallerwebi.dominio.equipoNBAJugador;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.temporada.Temporada;

import javax.persistence.*;

@Entity
public class EquipoNBAJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EquipoNBA equipoNBA;

    @ManyToOne
    private Jugador jugador;

    @ManyToOne
    private Temporada temporada;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EquipoNBA getEquipoNBA() {
        return equipoNBA;
    }

    public void setEquipoNBA(EquipoNBA equipoNBA) {
        this.equipoNBA = equipoNBA;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }
}


