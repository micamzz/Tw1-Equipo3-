package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class RendimientoJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Jugador jugador;

    @ManyToOne
    private PartidoNBA partidoNBA;


    private Integer perdidas;
    private Integer robos;
    private Integer rebotes;
    private Integer asistencias;
    private Integer puntos;
    private Integer bloqueos;
    private String nombreCompleto;

    public RendimientoJugador() {
    }
    //Pongo este constructor para no lidiar con el problema de las otras clases ahora
    public RendimientoJugador(String nombreCompleto, int perdidas, int robos, int rebotes, int asistencias, int puntos) {
        this.perdidas = perdidas;
        this.robos = robos;
        this.rebotes = rebotes;
        this.asistencias = asistencias;
        this.puntos = puntos;
        this.nombreCompleto = nombreCompleto;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Jugador getJugador() {return jugador;}
    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }
    public PartidoNBA getPartidoNBA() {return partidoNBA;}
    public void setPartidoNBA(PartidoNBA partidoNBA) {this.partidoNBA = partidoNBA;}
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

    public Integer getRobos() {
        return robos;
    }

    public void setRobos(Integer robos) {
        this.robos = robos;
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

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }


}
