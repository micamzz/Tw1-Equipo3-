package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEventoPartido {

    void guardarEventoPartido(EventoPartido evento);


    List<EventoPartido> buscarEventosPorPartido(Long partidoId);

    List<EventoPartido> buscarEventosPorPartidoYJugador(Long partidoId, Long jugadorId);



}
