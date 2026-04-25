package com.tallerwebi.dominio;

public class EstadisticaDePartidoDelJugador {
  private final int fecha;
  private final int puntos;
  private final int rebotes;
  private final int asistencias;
  private final int robos;
  private final int bloqueos;
  private final int perdidas;

  public EstadisticaDePartidoDelJugador(
      int fecha, int puntos, int rebotes, int asistencias, int robos, int bloqueos, int perdidas) {
    this.fecha = fecha;
    this.puntos = puntos;
    this.rebotes = rebotes;
    this.asistencias = asistencias;
    this.robos = robos;
    this.bloqueos = bloqueos;
    this.perdidas = perdidas;
  }

  public int getFecha() {
    return this.fecha;
  }

  public int getPuntos() {
    return this.puntos;
  }

  public int getRebotes() {
    return this.rebotes;
  }

  public int getAsistencias() {
    return this.asistencias;
  }

  public int getRobos() {
    return this.robos;
  }

  public int getBloqueos() {
    return this.bloqueos;
  }

  public int getPerdidas() {
    return this.perdidas;
  }
}
