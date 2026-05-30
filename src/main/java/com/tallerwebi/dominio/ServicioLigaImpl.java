package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("servicioLiga")
@Transactional
public class ServicioLigaImpl implements ServicioLiga {

    private RepositorioJugador repositorioJugador;

    @Autowired
    public ServicioLigaImpl(RepositorioJugador repositorioJugador) {
        this.repositorioJugador = repositorioJugador;
    }

    @Override
    public Liga obtenerLiga() {
        List<Jugador> jugadores = repositorioJugador.buscarJugadores(null, null);

        Liga liga = new Liga();

        // Creacion de una demo de liga con 4 equipos, 1 partido jugado y 1 partido
        // programado
        Equipo equipoA = new Equipo();
        equipoA.setNombreEquipo("Equipo A");
        Equipo equipoB = new Equipo();
        equipoB.setNombreEquipo("Equipo B");
        Equipo equipoC = new Equipo();
        equipoC.setNombreEquipo("Equipo C");
        Equipo equipoD = new Equipo();
        equipoD.setNombreEquipo("Equipo D");

        List<RendimientoJugador> rendimientoA = mapJugadoresToRendimientos(jugadores, 0, 4);
        List<RendimientoJugador> rendimientoB = mapJugadoresToRendimientos(jugadores, 4, 8);

        PartidoJugado partido1 = new PartidoJugado(equipoA, equipoB, sumaPuntos(rendimientoA), sumaPuntos(rendimientoB),
                rendimientoA, rendimientoB);
        liga.getHistorialPartidos().add(partido1);

        PartidoProgramado proximo1 = new PartidoProgramado(equipoC, equipoD, 1, "01/06/2026", "20:30", "Main Arena");
        liga.getProximosPartidos().add(proximo1);

        return liga;
    }


    private List<RendimientoJugador> mapJugadoresToRendimientos(List<Jugador> jugadores, int startInclusive,
            int endExclusive) {
        List<RendimientoJugador> rendimientos = new ArrayList<>();
        if (jugadores == null || jugadores.isEmpty()) {
            return rendimientos;
        }

        int size = jugadores.size();
        for (int i = startInclusive; i <= endExclusive; i++) {
            Jugador j = jugadores.get(i % size);
            String nombreCompleto = j.getNombre() + " " + j.getApellido();

            // el rendimiento del jugador es simulado en base a su posicion y un poco de
            // variabilidad
            Posicion pos = j.getPosicion();
            int basePuntos = 10;
            int baseRebotes = 3;
            int baseAsistencias = 2;

            if (pos == Posicion.BASE) {
                basePuntos = 12 + (i % 6);
                baseRebotes = 2 + (i % 3);
                baseAsistencias = 6 + (i % 5);
            } else if (pos == Posicion.ALERO) {
                basePuntos = 14 + (i % 8);
                baseRebotes = 4 + (i % 5);
                baseAsistencias = 3 + (i % 4);
            } else if (pos == Posicion.PIVOT) {
                basePuntos = 11 + (i % 7);
                baseRebotes = 8 + (i % 6);
                baseAsistencias = 2 + (i % 3);
            }

            int puntos = basePuntos;
            int rebotes = baseRebotes;
            int asistencias = baseAsistencias;
            int robos = 1 + (i % 3);
            int perdidas = 1 + (i % 4);

            rendimientos.add(new RendimientoJugador(nombreCompleto, perdidas, robos, rebotes, asistencias, puntos));
        }

        return rendimientos;
    }

    private int sumaPuntos(List<RendimientoJugador> lista) {
        return lista.stream().mapToInt(RendimientoJugador::getPuntos).sum();
    }

}
