package com.tallerwebi.dominio.mercado;

import com.tallerwebi.dominio.eventoPartido.EventoPartido;
import com.tallerwebi.dominio.enums.Posicion;
import com.tallerwebi.dominio.jugador.RendimientoJugador;
import com.tallerwebi.dominio.jugador.Jugador;

import java.util.List;

public interface ServicioMercado {
    List<Jugador> obtenerJugadores(Posicion posicion, String nombre);

    List<Jugador> buscarAlero();

    List<Jugador> buscarPivot();

    List<Jugador> buscarBase();

    Jugador buscarJugadorPorId(long id);

    RendimientoJugador obtenerRendimiento(long jugadorId);

    List<RendimientoJugador> obtenerRendimientosPorPartido(long jugadorId);

    double calcularPuntajeJugador(RendimientoJugador rendimiento);

    RendimientoJugador obtenerRendimientoPorJugadorYTorneo(Long jugadorId, Long torneoId);

    List<RendimientoJugador> obtenerRendimientosPorTorneo(Long torneoId);

    List<RendimientoJugador> obtenerTopJugadoresPorTorneo(Long torneoId, int limite);

    List<EventoPartido> buscarEventosPorJugadorYFecha(Long jugadorId, Long fechaId);

    List<EventoPartido> buscarEventosPorJugadorTorneo(Long jugadorId, Long torneoId);
}
