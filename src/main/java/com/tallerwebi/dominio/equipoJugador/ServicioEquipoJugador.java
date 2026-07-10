package com.tallerwebi.dominio.equipoJugador;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.excepcion.FechaNoEncontradaException;

import java.util.HashMap;
import java.util.List;

public interface ServicioEquipoJugador {

    HashMap<Integer, EquipoJugador> buscarJugadoresPorEquipoId(Long id) throws FechaNoEncontradaException;

    List<Jugador> obtenerJugadoresDisponiblesPorPosicion(Long idEquipo, Posicion posicion) throws FechaNoEncontradaException;

}
