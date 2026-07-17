package com.tallerwebi.dominio.formacion;

import com.sun.istack.NotNull;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.jugador.Jugador;
import com.tallerwebi.dominio.partidoNBA.PartidoNBA;

import javax.persistence.*;

@Entity
public class Formacion {

    /* id   1       2       3       4
    Equipo  1       1       2       2
    Partido 1       1       1       1
    Jugador sabri   mica    sofi    mati
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private PartidoNBA partido;

    @NotNull
    @ManyToOne
    private EquipoNBA equipoNBA;

    @NotNull
    @ManyToOne
    private Jugador jugador;


    public Formacion() {
    }

    public Long getId() {
        return id;
    }

    public PartidoNBA getPartido() {

        return partido;
    }

    public void setPartido(PartidoNBA partido) {
        this.partido = partido;
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
}
