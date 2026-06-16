package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEventoPartido {

    void guardarEventoPartido(EventoPartido evento);

    EventoPartido buscarEventoPorId(Long id);

    List<EventoPartido> buscarEventoPorPartido(Long partidoId);




}
