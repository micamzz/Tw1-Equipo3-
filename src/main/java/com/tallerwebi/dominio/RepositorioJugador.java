package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioJugador {
    List<Jugador> buscarJugadores(Posicion posicion, String nombre);
    Jugador buscarJugadorPorId(long id);
    RendimientoJugador buscarRendimientoPorJugador(long jugadorId);
}
