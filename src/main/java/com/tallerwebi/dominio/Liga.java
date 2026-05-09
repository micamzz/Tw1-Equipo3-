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

    Partido nuevoPartido = new Partido(
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

  /**
   * Genera un fixture donde cada equipo juega una vez de local
   * y otra de
   * visitante contra cada equipo. Si la cantidad de equipos es impar, se
   * agrega una exención
   * temporal para que la rotación funcione correctamente.
   */
  public void generarFixtureIdaVueltaLocalVisitante() {
    List<Equipo> rotacion = new ArrayList<>(this.equipos);
    int totalEquipos = rotacion.size();

    if (totalEquipos == 0) {
      return;
    }

    boolean tieneExencion = false;
    if (totalEquipos % 2 == 1) {
      rotacion.add(new Equipo("Exencion"));
      totalEquipos++;
      tieneExencion = true;
    }

    int rondas = totalEquipos - 1;
    int partidosPorRonda = totalEquipos / 2;

    List<List<Equipo[]>> todasLasRondas = new ArrayList<>();

    for (int indiceRonda = 0; indiceRonda < rondas; indiceRonda++) {
      List<Equipo[]> partidos = new ArrayList<>();
      for (int indicePartido = 0; indicePartido < partidosPorRonda; indicePartido++) {
        Equipo local = rotacion.get(indicePartido);
        Equipo visitante = rotacion.get(totalEquipos - 1 - indicePartido);
        partidos.add(new Equipo[] { local, visitante });
      }
      todasLasRondas.add(partidos);

      // Rotación: mantener la primera posición y rotar el resto hacia la derecha
      List<Equipo> nuevaRotacion = new ArrayList<>();
      nuevaRotacion.add(rotacion.get(0));
      nuevaRotacion.add(rotacion.get(totalEquipos - 1));
      for (int indice = 1; indice < totalEquipos - 1; indice++) {
        nuevaRotacion.add(rotacion.get(indice));
      }
      rotacion = nuevaRotacion;
    }

    int numeroFecha = 1;

    // Primera vuelta (como quedaron asignados: local vs visitante)
    for (List<Equipo[]> ronda : todasLasRondas) {
      for (Equipo[] partido : ronda) {
        Equipo local = partido[0];
        Equipo visitante = partido[1];
        if (tieneExencion && ("Exencion".equals(local.getNombre()) || "Exencion".equals(visitante.getNombre()))) {
          continue;
        }
        registrarPartido(numeroFecha, local, visitante);
      }
      numeroFecha++;
    }

    // Segunda vuelta (invertir local/visitante)
    for (List<Equipo[]> ronda : todasLasRondas) {
      for (Equipo[] partido : ronda) {
        Equipo local = partido[1];
        Equipo visitante = partido[0];
        if (tieneExencion && ("Exencion".equals(local.getNombre()) || "Exencion".equals(visitante.getNombre()))) {
          continue;
        }
        registrarPartido(numeroFecha, local, visitante);
      }
      numeroFecha++;
    }
  }
}
