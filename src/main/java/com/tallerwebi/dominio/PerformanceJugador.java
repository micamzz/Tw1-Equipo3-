package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PerformanceJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Jugador jugador;

    private Partido partido;

    private Integer minutosJugados;
    private Integer puntos;
    private Integer rebotes;
    private Integer asistencias;
    private Integer robos;
    private Integer bloqueos;
    private Integer perdidas;

    public Double calcularPuntajePerformance() {
        return puntos + (rebotes * 1.2) +
                (asistencias * 1.5) +
                (robos * 3) +
                (bloqueos * 3) -
                (perdidas * 2);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Integer getMinutosJugados() {
        return minutosJugados;
    }

    public void setMinutosJugados(Integer minutosJugados) {
        this.minutosJugados = minutosJugados;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getRebotes() {
        return rebotes;
    }

    public void setRebotes(Integer rebotes) {
        this.rebotes = rebotes;
    }

    public Integer getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Integer asistencias) {
        this.asistencias = asistencias;
    }

    public Integer getRobos() {
        return robos;
    }

    public void setRobos(Integer robos) {
        this.robos = robos;
    }

    public Integer getBloqueos() {
        return bloqueos;
    }

    public void setBloqueos(Integer bloqueos) {
        this.bloqueos = bloqueos;
    }

    public Integer getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(Integer perdidas) {
        this.perdidas = perdidas;
    }

    public Long getId() {
        return id;
    }
}
