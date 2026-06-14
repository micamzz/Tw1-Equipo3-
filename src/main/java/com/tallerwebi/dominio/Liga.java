package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipo.Equipo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Liga {

    private List<PartidoJugado> historialPartidos;
    private List<PartidoProgramado> proximosPartidos;

    public Liga() {
        this.historialPartidos = new ArrayList<>();
        this.proximosPartidos = new ArrayList<>();
    }

    public Liga(List<PartidoJugado> historialPartidos, List<PartidoProgramado> proximosPartidos) {
        this.historialPartidos = historialPartidos;
        this.proximosPartidos = proximosPartidos;
    }

    public List<PartidoJugado> getHistorialPartidos() {
        return historialPartidos;
    }

    public void setHistorialPartidos(List<PartidoJugado> historialPartidos) {
        this.historialPartidos = historialPartidos;
    }

    public List<PartidoProgramado> getProximosPartidos() {
        return proximosPartidos;
    }

    public void setProximosPartidos(List<PartidoProgramado> proximosPartidos) {
        this.proximosPartidos = proximosPartidos;
    }

    public static Liga crearLigaDemo() {
        Equipo lakers = crearEquipo("Los Angeles Lakers");
        Equipo celtics = crearEquipo("Boston Celtics");
        Equipo nuggets = crearEquipo("Denver Nuggets");
        Equipo heat = crearEquipo("Miami Heat");
        Equipo warriors = crearEquipo("Golden State Warriors");
        Equipo suns = crearEquipo("Phoenix Suns");
        Equipo bucks = crearEquipo("Milwaukee Bucks");
        Equipo knicks = crearEquipo("New York Knicks");

        PartidoJugado partido1 = new PartidoJugado(
                lakers,
                celtics,
                102,
                98,
                Arrays.asList(
                        new RendimientoJugador("LeBron James", 3, 1, 8, 7, 28),
                        new RendimientoJugador("Anthony Davis", 2, 1, 11, 3, 26),
                        new RendimientoJugador("Austin Reaves", 1, 0, 3, 4, 12),
                        new RendimientoJugador("Rui Hachimura", 1, 0, 5, 2, 10)
                ),
                Arrays.asList(
                        new RendimientoJugador("Jayson Tatum", 3, 1, 8, 4, 30),
                        new RendimientoJugador("Jaylen Brown", 2, 1, 6, 3, 24),
                        new RendimientoJugador("Jrue Holiday", 2, 2, 4, 7, 17),
                        new RendimientoJugador("Derrick White", 1, 1, 3, 3, 14)
                )
        );

        PartidoJugado partido2 = new PartidoJugado(
                nuggets,
                heat,
                114,
                109,
                Arrays.asList(
                        new RendimientoJugador("Nikola Jokic", 2, 2, 14, 11, 31),
                        new RendimientoJugador("Jamal Murray", 1, 1, 5, 6, 24),
                        new RendimientoJugador("Michael Porter Jr.", 1, 0, 7, 2, 19),
                        new RendimientoJugador("Aaron Gordon", 1, 1, 9, 3, 16)
                ),
                Arrays.asList(
                        new RendimientoJugador("Jimmy Butler", 3, 2, 7, 5, 29),
                        new RendimientoJugador("Bam Adebayo", 2, 1, 12, 4, 23),
                        new RendimientoJugador("Tyler Herro", 1, 0, 4, 6, 18),
                        new RendimientoJugador("Caleb Martin", 1, 1, 5, 2, 11)
                )
        );

        PartidoProgramado proximo1 = new PartidoProgramado(lakers, nuggets, 18, "27/05/2026", "20:30", "Crypto.com Arena");
        PartidoProgramado proximo2 = new PartidoProgramado(celtics, heat, 18, "27/05/2026", "22:00", "TD Garden");
        PartidoProgramado proximo3 = new PartidoProgramado(warriors, suns, 19, "29/05/2026", "21:00", "Chase Center");
        PartidoProgramado proximo4 = new PartidoProgramado(bucks, knicks, 19, "29/05/2026", "21:30", "Fiserv Forum");

        return new Liga(
                Arrays.asList(partido1, partido2),
                Arrays.asList(proximo1, proximo2, proximo3, proximo4)
        );
    }

    private static Equipo crearEquipo(String nombreEquipo) {
        Equipo equipo = new Equipo();
        equipo.setNombreEquipo(nombreEquipo);
        return equipo;
    }


}
