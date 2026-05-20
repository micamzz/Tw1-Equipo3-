package com.tallerwebi.dominio;

public class RendimientoJugador {
    private String nombreCompleto;
    private int perdidas;
    private int robos;
    private int rebotes;
    private int asistencias;
    private int puntos;

    public RendimientoJugador() {
    }

    public RendimientoJugador(String nombreCompleto, int perdidas, int robos, int rebotes, int asistencias, int puntos) {
        this.nombreCompleto = nombreCompleto;
        this.perdidas = perdidas;
        this.robos = robos;
        this.rebotes = rebotes;
        this.asistencias = asistencias;
        this.puntos = puntos;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public int getRobos() {
        return robos;
    }

    public void setRobos(int robos) {
        this.robos = robos;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
