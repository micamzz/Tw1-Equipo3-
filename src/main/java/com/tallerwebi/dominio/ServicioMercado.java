package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioMercado {
    List<Jugador> obtenerJugadores(Posicion posicion, String nombre);

    List<Jugador> buscarAlero();

    List<Jugador> buscarPivot();

    List<Jugador> buscarBase();

    Jugador buscarJugadorPorId(long id);

    RendimientoJugador obtenerRendimiento(long jugadorId);

    double calcularPuntajeJugador(RendimientoJugador rendimiento);

    RendimientoJugador obtenerRendimientoPorJugadorYTorneo(Long jugadorId, Long torneoId);

    List<RendimientoJugador> obtenerRendimientosPorTorneo(Long torneoId);

    List<RendimientoJugador> obtenerTopJugadoresPorTorneo(Long torneoId, int limite);
}
