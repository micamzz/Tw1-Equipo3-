package com.tallerwebi.dominio.equipoNBAJugador;

import java.util.List;

public interface RepositorioEquipoNBAJugador {

    void asignarJugadorAUnEquipo(EquipoNBAJugador equipoJugador);

    List<EquipoNBAJugador> buscarJugadoresDelEquipoNBA(Long id);

    List<EquipoNBAJugador> buscarJugadoresDelEquipoNBAEnTemporada(Long idEquipo, Long idTemporada);

    List<EquipoNBAJugador> buscarTodasLasAsignaciones();

    /* boolean jugadorPerteneceAUnEquipoEnLaTemporada(Long idJugador, Long idTemporada);*/

    void eliminarJugadorDelEquipo(EquipoNBAJugador equipoNBAJugador);

    EquipoNBAJugador buscarEquipoYJugadorAsociado(Long idEquipo, Long idJugador);

    void eliminarTodasLasAsignacionesDelEquipo(Long idEquipo);

    /*Se usa validación en un if*/
    boolean jugadorPerteneceAUnEquipoEnElTorneo(Long idJugador, Long id);

    List<EquipoNBAJugador> buscarAsignacionesPorTorneo(Long idTorneo);

    EquipoNBAJugador buscarEquipoJugadorYTorneo(Long idEquipo, Long idJugador, Long idTorneo);

    List<EquipoNBAJugador> buscarJugadoresDelEquipoNBAEnTorneo(Long idEquipo, Long idTorneo);

    EquipoNBAJugador buscarEquipoJugadorYTemporada(Long idEquipo, Long idJugador, Long idTemporada);
}
