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

      double factorTalento = jugador.getNivelTalento() / 100.0;

      int puntosIniciales = 0, asistenciasIniciales = 0, rebotes = 0;
      int robos = random.nextInt(3);
      int bloqueos = random.nextInt(3);
      int perdidas = random.nextInt(4);

      switch (jugador.getPosicionJugador()) {
        case BASE:
          puntosIniciales = random.nextInt(16) + 10;
          asistenciasIniciales = random.nextInt(8) + 4;
          rebotes = random.nextInt(5);
          break;
        case ALERO:
          puntosIniciales = random.nextInt(21) + 10;
          asistenciasIniciales = random.nextInt(4) + 1;
          rebotes = random.nextInt(6) + 3;
          break;
        case PIVOT:
          puntosIniciales = random.nextInt(15) + 8;
          asistenciasIniciales = random.nextInt(3);
          rebotes = random.nextInt(9) + 6;
          bloqueos = random.nextInt(4) + 1;
          break;
      }

      int puntosFinales =
          (int) (puntosIniciales * multiplicadorSegunMinutosJugados * factorTalento);
      int asistenciasFinales =
          (int) (asistenciasIniciales * multiplicadorSegunMinutosJugados * factorTalento);
      rebotes = (int) (rebotes * multiplicadorSegunMinutosJugados);
      robos = (int) (robos * multiplicadorSegunMinutosJugados);
      bloqueos = (int) (bloqueos * multiplicadorSegunMinutosJugados);
      perdidas = (int) (perdidas * multiplicadorSegunMinutosJugados);

      puntosFinales += random.nextInt(4); // Factor suerte

      EstadisticaDePartidoDelJugador estadisticaPartidoActual =
          new EstadisticaDePartidoDelJugador(
              numeroFecha, puntosFinales, rebotes, asistenciasFinales, robos, bloqueos, perdidas);

      jugador.registrarNuevaEstadisticaDePartido(estadisticaPartidoActual);
    }
  }
}
