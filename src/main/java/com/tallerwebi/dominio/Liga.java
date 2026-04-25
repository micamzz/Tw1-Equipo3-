package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Liga {
  private final List<Equipo> equipos;
  private final List<Partido> historialPartidos;

  public Liga(List<Equipo> equiposGenerados) {
    this.equipos = equiposGenerados;
    this.historialPartidos = new ArrayList<>();
  }

  public void registrarPartido(int numeroFecha, Equipo local, Equipo visitante) {
    int puntosLocal = local.calcularPuntosPorFecha(numeroFecha);
    int puntosVisitante = visitante.calcularPuntosPorFecha(numeroFecha);

    Partido nuevoPartido =
        new Partido(
            numeroFecha, local.getNombre(), visitante.getNombre(), puntosLocal, puntosVisitante);

    this.historialPartidos.add(nuevoPartido);

    System.out.println(
        "Partido registrado: "
            + nuevoPartido.getEquipoLocal()
            + " vs "
            + nuevoPartido.getEquipoVisitante());
  }

  public List<Equipo> getEquipos() {
    return Collections.unmodifiableList(this.equipos);
  }

  public List<Partido> getHistorialPartidos() {
    return Collections.unmodifiableList(this.historialPartidos);
  }
}
