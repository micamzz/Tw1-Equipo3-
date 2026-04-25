package com.tallerwebi.dominio;

import java.util.Random;

public class SimuladorDePartidos {
  private final Random random;

  public SimuladorDePartidos() {
    this.random = new Random();
  }

  private double obtenerMultiplicadorPorRol(RolJugador rol) {
    switch (rol) {
      case TITULAR:
        return 1.0;
      case SEXTO_HOMBRE:
        return 0.7;
      case SUPLENTE:
        return 0.4;
      default:
        return 0.0;
    }
  }

  private void simularPartidoDeEquipo(Equipo equipo, int numeroFecha) {
    for (Jugador jugador : equipo.getJugadores()) {

      double multiplicadorSegunMinutosJugados = obtenerMultiplicadorPorRol(jugador.getRolJugador());

      int puntos = 0, rebotes = 0, asistencias = 0;
      int robos = random.nextInt(3);
      int bloqueos = random.nextInt(3);
      int perdidas = random.nextInt(4);

      switch (jugador.getPosicionJugador()) {
        case BASE:
          puntos = random.nextInt(16) + 10;
          asistencias = random.nextInt(8) + 4;
          rebotes = random.nextInt(5);
          break;
        case ALERO:
          puntos = random.nextInt(21) + 10;
          asistencias = random.nextInt(4) + 1;
          rebotes = random.nextInt(6) + 3;
          break;
        case PIVOT:
          puntos = random.nextInt(15) + 8;
          asistencias = random.nextInt(3);
          rebotes = random.nextInt(9) + 6;
          bloqueos = random.nextInt(4) + 1;
          break;
      }

      puntos = (int) (puntos * multiplicadorSegunMinutosJugados);
      rebotes = (int) (rebotes * multiplicadorSegunMinutosJugados);
      asistencias = (int) (asistencias * multiplicadorSegunMinutosJugados);
      robos = (int) (robos * multiplicadorSegunMinutosJugados);
      bloqueos = (int) (bloqueos * multiplicadorSegunMinutosJugados);
      perdidas = (int) (perdidas * multiplicadorSegunMinutosJugados);

      EstadisticaDePartidoDelJugador estadisticaPartidoActual =
          new EstadisticaDePartidoDelJugador(
              numeroFecha, puntos, rebotes, asistencias, robos, bloqueos, perdidas);

      jugador.registrarNuevaEstadisticaDePartido(estadisticaPartidoActual);
    }
  }
}
