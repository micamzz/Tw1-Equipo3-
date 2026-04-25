package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jugador {
  private final String nombre;
  private final String apellido;
  private final PosicionJugador posicionJugador;
  private final RolJugador rolJugador;
  private final int nivelTalento;

  private final List<EstadisticaDePartidoDelJugador> estadisticasDePartidoDelJugador;

  public Jugador(
      String nombre,
      String apellido,
      PosicionJugador posicionJugador,
      RolJugador rolJugador,
      int nivelTalento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.posicionJugador = posicionJugador;
    this.rolJugador = rolJugador;
    this.nivelTalento = nivelTalento;
    this.estadisticasDePartidoDelJugador = new ArrayList<>();
  }

  public double getValorDeMercado() {
    if (estadisticasDePartidoDelJugador.isEmpty()) return 0.0;

    double puntosTotales = 0,
        rebotesTotales = 0,
        asistenciasTotales = 0,
        robosTotales = 0,
        bloqueosTotales = 0,
        perdidasTotales = 0;

    for (EstadisticaDePartidoDelJugador estadistica : estadisticasDePartidoDelJugador) {
      puntosTotales += estadistica.getPuntos();
      rebotesTotales += estadistica.getRebotes();
      asistenciasTotales += estadistica.getAsistencias();
      robosTotales += estadistica.getRobos();
      bloqueosTotales += estadistica.getBloqueos();
      perdidasTotales += estadistica.getPerdidas();
    }
    int cantidadPartidosJugados = estadisticasDePartidoDelJugador.size();

    return (puntosTotales / cantidadPartidosJugados)
        + (1.2 * (rebotesTotales / cantidadPartidosJugados))
        + (1.5 * (asistenciasTotales / cantidadPartidosJugados))
        + (3 * (robosTotales / cantidadPartidosJugados))
        + (3 * (bloqueosTotales / cantidadPartidosJugados))
        - (2 * (perdidasTotales / cantidadPartidosJugados));
  }

  public void registrarNuevaEstadisticaDePartido(EstadisticaDePartidoDelJugador nuevaEstadistica) {
    if (nuevaEstadistica != null) {
      this.estadisticasDePartidoDelJugador.add(nuevaEstadistica);
    }
  }

  public String getNombre() {
    return this.nombre;
  }

  public String getApellido() {
    return this.apellido;
  }

  public PosicionJugador getPosicionJugador() {
    return this.posicionJugador;
  }

  public RolJugador getRolJugador() {
    return this.rolJugador;
  }

  public int getNivelTalento() {
    return this.nivelTalento;
  }

  public List<EstadisticaDePartidoDelJugador> getEstadisticasDePartidoDelJugador() {
    return Collections.unmodifiableList(this.estadisticasDePartidoDelJugador);
  }
}
