package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FechaIncoherenteException;

import java.util.List;

public interface ServicioTorneo {


    void crearTorneo(TorneoVirtual torneo) throws FechaIncoherenteException;

    TorneoVirtual obtenerTorneoActual();

    Torneo buscarTorneoPorId(Long id);

    void eliminarTorneo(Long id);

    List<TorneoVirtual> obtenerTodosLosTorneos();
}
