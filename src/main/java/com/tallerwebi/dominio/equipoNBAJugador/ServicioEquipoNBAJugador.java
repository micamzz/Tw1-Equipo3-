package com.tallerwebi.dominio.equipoNBAJugador;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;

import java.util.List;

public interface ServicioEquipoNBAJugador {

    void agregarJugadorAlEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException;

    void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException;

    List<Jugador> obtenerJugadoresDelEquipoPorId(Long id);

    List<Jugador> obtenerJugadoresDisponibles();

    List<Jugador> obtenerJugadoresFiltrados(Posicion posicionEnum, String nombre);

    List<Jugador> obtenerJugadoresDelEquipoEnTorneo(Long idEquipo, Long idTorneo);
}
