package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioJugador {
    List<Jugador> buscarJugadores(Posicion posicion, String nombre);

    Jugador buscarJugadorPorId(long id);

    List<Jugador> buscarJugadoresPorPosicion(Posicion posicion);

    List<Jugador> buscarTodosLosJugadores();
}
