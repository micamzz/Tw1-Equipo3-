package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEquipoNBAJugador {

    List<Jugador> obtenerJugadoresDelEquipoPorId(Long id);

    List<Jugador> obtenerJugadoresDisponibles();
}
