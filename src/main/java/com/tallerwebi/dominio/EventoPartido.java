package com.tallerwebi.dominio;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class EventoPartido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private PartidoNBA partido;

    @Column (nullable = false)
    private LocalTime momentoPartido;

    @NotNull
    @ManyToOne
    private Jugador jugador;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoEstadistica tipoEstadistica;


    public EventoPartido() {
    }


    public Long getId() {
        return id;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }


    public PartidoNBA getPartido() {
        return partido;
    }

    public void setPartido(PartidoNBA partido) {
        this.partido = partido;
    }

    public LocalTime getMomentoPartido() {
        return momentoPartido;
    }

    public void setMomentoPartido(LocalTime momentoPartido) {
        this.momentoPartido = momentoPartido;
    }

    public TipoEstadistica getTipoEstadistica() {
        return tipoEstadistica;
    }

    public void setTipoEstadistica(TipoEstadistica tipoEstadistica) {
        this.tipoEstadistica = tipoEstadistica;
    }
}
