package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioTemporada {


    void guardar(Temporada temporada);

    Temporada buscarPorId(Long id);

    Temporada obtenerTemporadaActual();

    List<Temporada> obtenerTodas();

    boolean jugadorExisteEnLaTemporada(Long idJugador, Long idTemporada);
}
