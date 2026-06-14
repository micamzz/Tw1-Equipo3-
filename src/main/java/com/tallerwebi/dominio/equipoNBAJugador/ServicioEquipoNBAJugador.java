package com.tallerwebi.dominio.equipoNBAJugador;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;

import java.util.List;

public interface ServicioEquipoNBAJugador {

    List<Jugador> obtenerJugadoresDelEquipoPorId(Long id) throws TemporadaActualNoEncontradaException;

    List<Jugador> obtenerJugadoresDisponibles() throws TemporadaActualNoEncontradaException;

    List<Jugador> obtenerJugadoresFiltrados(Posicion posicionEnum, String nombre) throws TemporadaActualNoEncontradaException;
}
