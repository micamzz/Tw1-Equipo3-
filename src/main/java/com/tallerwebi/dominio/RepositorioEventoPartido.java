package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEventoPartido {

    void guardarEventoPartido(EventoPartido evento);

    List<EventoPartido> buscarEventosPorPartido(Long partidoId);

    List<EventoPartido> buscarEventosPorJugadorTorneo(Long jugadorId, Long torneoId);

    List<EventoPartido> buscarEventosPorJugadorYFecha(Long jugadorId, Long fechaId);

    void eliminarEvento(EventoPartido Evento);

    EventoPartido buscarEventoPorId(Long idEvento);
}
