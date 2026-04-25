package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDeEquipos {

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

    return new Jugador(nombre, apellido, posicionJugador, rolJugador);
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
