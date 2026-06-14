package com.tallerwebi.dominio.equipoNBAJugador;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Posicion;

import java.util.List;

public interface ServicioEquipoNBAJugador {

    List<Jugador> obtenerJugadoresDelEquipoPorId(Long id);

    List<Jugador> obtenerJugadoresDisponibles();

    List<Jugador> obtenerJugadoresFiltrados(Posicion posicionEnum, String nombre);
}
