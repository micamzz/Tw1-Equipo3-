package com.tallerwebi.dominio;

import java.util.HashMap;
import java.util.List;

public interface ServicioEquipoJugador {

    EquipoJugador guardarEquipoJugador(EquipoJugador equipoJugador);

    HashMap<Integer, EquipoJugador> buscarJugadoresPorEquipoId(Long id);

    List<Jugador> obtenerJugadoresDisponiblesPorPosicion(Long idEquipo, Posicion posicion);

}
