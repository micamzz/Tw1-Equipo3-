package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoJugadorNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;

import java.util.HashMap;

public interface ServicioEquipoJugador {

    EquipoJugador guardarEquipoJugador (EquipoJugador equipoJugador);

    HashMap <Integer,Jugador> buscarJugadoresPorEquipoId(Long id) throws EquipoJugadorNoEncontradoException;

}
