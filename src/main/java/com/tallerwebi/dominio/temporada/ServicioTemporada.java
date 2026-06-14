package com.tallerwebi.dominio.temporada;

import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;

import java.util.List;

public interface ServicioTemporada {
    void guardarTemporada(Temporada temporada);

    Temporada obtenerTemporadaActual() throws TemporadaActualNoEncontradaException;

    List<Temporada> obtenerTodasLasTemporadas();

}
