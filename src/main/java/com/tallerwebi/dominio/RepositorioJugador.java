package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioJugador {
    List<Jugador> buscarJugadores(Posicion posicion, String nombre);

    Jugador buscarJugadorPorId(long id);

    RendimientoJugador buscarRendimientoPorJugador(long jugadorId);

    RendimientoJugador buscarRendimientoPorJugadorYTorneo(long jugadorId, long torneoId);

    List<RendimientoJugador> buscarRendimientosPorTorneo(Long torneoId);

    List<Jugador> buscarTodosLosJugadores();

    List<RendimientoJugador> buscarRendimientosPorJugadorConPartido(long jugadorId);
    void guardarRendimiento(RendimientoJugador rendimiento);
}
