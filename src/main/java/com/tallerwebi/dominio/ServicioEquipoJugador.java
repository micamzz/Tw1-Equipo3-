package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoJugadorNoEncontradoException;

import java.util.HashMap;
import java.util.List;

public interface ServicioEquipoJugador {

    EquipoJugador guardarEquipoJugador(EquipoJugador equipoJugador);

    EquipoJugador buscarJugadorPorNumeroDeOrden(List<EquipoJugador> jugadores, Integer orden);

    HashMap<Integer, Jugador> buscarJugadoresPorEquipoId(Long id) throws EquipoJugadorNoEncontradoException;

}
