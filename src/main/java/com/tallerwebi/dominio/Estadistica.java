package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Estadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PartidoNBA partido;

    private TipoEstadistica tipoEstadistica;

    @ManyToOne
    private Jugador jugador;




    public Estadistica() {
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


    public TipoEstadistica getTipoEstadistica() {
        return tipoEstadistica;
    }

    public void setTipoEstadistica(TipoEstadistica tipoEstadistica) {
        this.tipoEstadistica = tipoEstadistica;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }


}
