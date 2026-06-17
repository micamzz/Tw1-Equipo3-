package com.tallerwebi.dominio.temporada;

import java.util.List;

public interface RepositorioTemporada {

    void guardar(Temporada temporada);

    void actualizar(Temporada temporada);

    Temporada obtenerTemporadaActual();

    List<Temporada> obtenerTodasLasTemporadas();

    Temporada obtenerTemporadaPorId(Long idTemporada);
}