package com.tallerwebi.dominio;

import java.util.HashMap;

public interface RepositorioEquipoJugador {

    void guardarEquipoJugador(EquipoJugador equipoJugador);

    HashMap<Integer, Jugador> buscarJugadoresPorEquipoId(Long id);

}
