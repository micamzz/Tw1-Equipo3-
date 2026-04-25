package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class GeneradorDeNombres {

  private static final List<String> NOMBRES_BASE =
      Arrays.asList(
          "Mateo",
          "Valentino",
          "LeBron",
          "Facundo",
          "Stephen",
          "Luka",
          "Emanuel",
          "Giannis",
          "Nikola",
          "Jayson",
          "Kevin",
          "Jimmy",
          "Damian",
          "Joel",
          "Kyrie");

  private static final List<String> APELLIDO_BASE =
      Arrays.asList(
          "Campazzo",
          "Ginobili",
          "James",
          "Curry",
          "Doncic",
          "Scola",
          "Antetokounmpo",
          "Jokic",
          "Tatum",
          "Durant",
          "Butler",
          "Lillard",
          "Embiid",
          "Irving",
          "Perez");

  private final List<String[]> listaDeNombresPosibles;

  public GeneradorDeNombres() {
    this.listaDeNombresPosibles = new ArrayList<>();

    for (String nombres : NOMBRES_BASE) {
      for (String apellido : APELLIDO_BASE) {
        this.listaDeNombresPosibles.add(new String[] {nombres, apellido});
      }
    }

    Collections.shuffle(this.listaDeNombresPosibles);
  }

  public String[] obtenerNombreYApellidoUnico() {
    if (this.listaDeNombresPosibles.isEmpty()) {
      throw new RuntimeException("Te quedaste sin combinaciones de nombres.");
    }

    return this.listaDeNombresPosibles.remove(0);
  }
}
