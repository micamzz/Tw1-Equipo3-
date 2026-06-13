package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEquipoNBAJugador {

    void asignarJugadorAUnEquipo(EquipoNBAJugador equipoJugador);

    List<EquipoNBAJugador> buscarJugadoresDelEquipoNBA(Long id);

    List<EquipoNBAJugador> buscarTodasLasAsignaciones();

    boolean jugadorExisteEnLaTemporada(Long idJugador, Long idTemporada);

    void eliminarJugadorDelEquipo(EquipoNBAJugador equipoNBAJugador);

    EquipoNBAJugador buscarEquipoYJugadorAsociado(Long idEquipo, Long idJugador);

    void eliminarTodasLasAsignacionesDelEquipo(Long idEquipo);
}
