package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioJugador {
    List<Jugador> buscarJugadores(Posicion posicion, String nombre);

    Jugador buscarJugadorPorId(long id);
<<<<<<< HEAD
    RendimientoJugador buscarRendimientoPorJugador(long jugadorId);
=======

    List<Jugador> buscarJugadoresPorPosicion(Posicion posicion);

    List<Jugador> buscarTodosLosJugadores();
>>>>>>> origin/main
}
