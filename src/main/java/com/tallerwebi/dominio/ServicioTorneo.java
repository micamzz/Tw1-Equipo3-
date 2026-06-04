package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FechaIncoherenteException;
import com.tallerwebi.dominio.excepcion.FechasSuperpuestasException;
import com.tallerwebi.dominio.excepcion.NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException;
import com.tallerwebi.dominio.excepcion.TorneoNoEncontradoException;

import java.util.List;

public interface ServicioTorneo {


    void crearTorneo(TorneoVirtual torneo) throws FechaIncoherenteException, FechasSuperpuestasException;

    TorneoVirtual obtenerTorneoActual();

    Torneo buscarTorneoPorId(Long id);

    void eliminarTorneo(Long id) throws NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException, TorneoNoEncontradoException;

    List<TorneoVirtual> obtenerTodosLosTorneos();
}
