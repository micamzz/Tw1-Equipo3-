package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorDeEquipos {

  private static final Random random = new Random();

  private static final String[] NOMBRE_EQUIPOS = {
      "Dragones", "Tiburones", "Halcones", "Lobos", "Titanes", "Cometas",
      "Meteoros", "Gladiadores", "Pioneros", "Fantasmas", "Cobras", "Leones"
  };

  private static Jugador crearJugador(
      GeneradorDeNombres generadorDeNombres,
      PosicionJugador posicionJugador,
      RolJugador rolJugador) {
    String[] nombreCompleto = generadorDeNombres.obtenerNombreYApellidoUnico();
    String nombre = nombreCompleto[0];
    String apellido = nombreCompleto[1];

    int talento = 0;
    if (rolJugador == RolJugador.TITULAR) {
      talento = random.nextInt(26) + 75; // Talento entre 75 y 100 para titulares
    } else if (rolJugador == RolJugador.SEXTO_HOMBRE) {
      talento = random.nextInt(15) + 70; // Talento entre 70 y 85 para sexto hombre
    } else {
      talento = random.nextInt(20) + 55; // Talento entre 55 y 75 para suplentes
    }

    return new Jugador(nombre, apellido, posicionJugador, rolJugador, talento);
  }

  public static List<Equipo> crearTodosLosEquipos() {
    List<Equipo> listaDeEquipos = new ArrayList<>();

    GeneradorDeNombres generadorDeNombres = new GeneradorDeNombres();

    for (String nombreDelEquipo : NOMBRE_EQUIPOS) {
      Equipo nuevoEquipo = new Equipo(nombreDelEquipo);

      // Titulares
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.BASE, RolJugador.TITULAR));
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.ALERO, RolJugador.TITULAR));
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.ALERO, RolJugador.TITULAR));
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.PIVOT, RolJugador.TITULAR));
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.PIVOT, RolJugador.TITULAR));

      // Sexto Hombre
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.ALERO, RolJugador.SEXTO_HOMBRE));

      // Suplentes
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.BASE, RolJugador.SUPLENTE));
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.ALERO, RolJugador.SUPLENTE));
      nuevoEquipo.agregarJugador(
          crearJugador(generadorDeNombres, PosicionJugador.PIVOT, RolJugador.SUPLENTE));

      listaDeEquipos.add(nuevoEquipo);
    }
    return listaDeEquipos;
  }
}
