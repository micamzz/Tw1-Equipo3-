package com.tallerwebi.dominio;

public class Partido {
  private final int fecha;
  private final String equipoLocal;
  private final String equipoVisitante;
  private final int puntosLocal;
  private final int puntosVisitante;

  public Partido(
      int fecha, String equipoLocal, String equipoVisitante, int puntosLocal, int puntosVisitante) {
    this.fecha = fecha;
    this.equipoLocal = equipoLocal;
    this.equipoVisitante = equipoVisitante;
    this.puntosLocal = puntosLocal;
    this.puntosVisitante = puntosVisitante;
  }

  public int getFecha() {
    return this.fecha;
  }

  public String getEquipoLocal() {
    return this.equipoLocal;
  }

  public String getEquipoVisitante() {
    return this.equipoVisitante;
  }

  public int getPuntosLocal() {
    return this.puntosLocal;
  }

  public int getPuntosVisitante() {
    return this.puntosVisitante;
  }
}
