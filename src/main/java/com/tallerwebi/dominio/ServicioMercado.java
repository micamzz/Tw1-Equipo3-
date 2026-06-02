package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioMercado {
    List<Jugador> obtenerJugadores(Posicion posicion, String nombre);

    List<Jugador> buscarAlero();

    List<Jugador> buscarPivot();

    List<Jugador> buscarBase();
<<<<<<< HEAD
    Jugador buscarJugadorPorId(long id);
    RendimientoJugador obtenerRendimiento(long jugadorId);
    double calcularPuntajeJugador(RendimientoJugador rendimiento);
=======


>>>>>>> origin/main
}
