package com.tallerwebi.dominio.equipoNBAJugador;

import java.util.List;

public interface RepositorioEquipoNBAJugador {

    void asignarJugadorAUnEquipo(EquipoNBAJugador equipoJugador);

    List<EquipoNBAJugador> buscarJugadoresDelEquipoNBA(Long id);

    List<EquipoNBAJugador> buscarTodasLasAsignaciones();

    void eliminarJugadorDelEquipo(EquipoNBAJugador equipoNBAJugador);

    EquipoNBAJugador buscarEquipoYJugadorAsociado(Long idEquipo, Long idJugador);

    void eliminarTodasLasAsignacionesDelEquipo(Long idEquipo);

    /*Se usa validación en un if*/
    boolean jugadorPerteneceAUnEquipoEnElTorneo(Long idJugador, Long id);

    List<EquipoNBAJugador> buscarAsignacionesPorTorneo(Long idTorneo);

    EquipoNBAJugador buscarEquipoJugadorYTorneo(Long idEquipo, Long idJugador, Long idTorneo);

    List<EquipoNBAJugador> buscarJugadoresDelEquipoNBAEnTorneo(Long idEquipo, Long idTorneo);
}
