package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipo {
  private final String nombre;
  private final List<Jugador> jugadores;

  public Equipo(String nombre) {
    this.nombre = nombre;
    this.jugadores = new ArrayList<>();
  }

  public void agregarJugador(Jugador nuevoJugador) {
    if (nuevoJugador != null) {
      this.jugadores.add(nuevoJugador);
    }
  }

  public int calcularPuntosPorFecha(int fechaBuscada) {
    return jugadores.stream()
        .mapToInt(
            jugador ->
                jugador.getEstadisticasDePartidoDelJugador().stream()
                    .filter(estadistica -> estadistica.getFecha() == fechaBuscada)
                    .mapToInt(EstadisticaDePartidoDelJugador::getPuntos)
                    .findFirst()
                    .orElse(0))
        .sum();
  }

  public String getNombre() {
    return this.nombre;
  }

  public List<Jugador> getJugadores() {
    return Collections.unmodifiableList(this.jugadores);
  }
}
