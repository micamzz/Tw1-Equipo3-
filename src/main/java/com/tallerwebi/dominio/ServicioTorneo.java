package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioTorneo {


    void crearTorneo(Torneo torneo) throws FechaIncoherenteException, FechasSuperpuestasException, NombreDeTorneoEnBlancoException, TipoDeTorneoEnBlancoException;

    Torneo obtenerTorneoActual(TipoTorneo tipoTorneo);

    Torneo buscarTorneoPorId(Long id);

    void eliminarTorneo(Long id) throws NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException, TorneoNoEncontradoException;

    List<Torneo> obtenerTodosLosTorneos();


}
