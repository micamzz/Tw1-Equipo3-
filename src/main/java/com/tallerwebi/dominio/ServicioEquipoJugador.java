package com.tallerwebi.dominio;

import java.util.HashMap;
import java.util.List;

public interface ServicioEquipoJugador {

    EquipoJugador guardarEquipoJugador(EquipoJugador equipoJugador);

    EquipoJugador buscarJugadorPorNumeroDeOrden(List<EquipoJugador> jugadores, Integer orden);

    HashMap<Integer, EquipoJugador> buscarJugadoresPorEquipoId(Long id);

    /* List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo);*/
}
