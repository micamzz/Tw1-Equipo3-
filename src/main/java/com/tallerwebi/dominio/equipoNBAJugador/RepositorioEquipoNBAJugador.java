package com.tallerwebi.dominio.equipoNBAJugador;

import java.util.List;

public interface RepositorioEquipoNBAJugador {

    void asignarJugadorAUnEquipo(EquipoNBAJugador equipoJugador);

    void eliminarJugadorDelEquipo(EquipoNBAJugador equipoNBAJugador);

    void eliminarTodasLasAsignacionesDelEquipo(Long idEquipo);

    /*Se usa validación en un if*/
    boolean jugadorPerteneceAUnEquipoEnElTorneo(Long idJugador, Long idTorneo);

    boolean existenJugadoresAsignadosEnTorneo(Long idTorneo);

    List<EquipoNBAJugador> buscarJugadoresDelEquipoNBA(Long id);

    List<EquipoNBAJugador> buscarTodasLasAsignaciones();

    EquipoNBAJugador buscarEquipoYJugadorAsociado(Long idEquipo, Long idJugador);

    List<EquipoNBAJugador> buscarAsignacionesPorTorneo(Long idTorneo);

    EquipoNBAJugador buscarEquipoJugadorYTorneo(Long idEquipo, Long idJugador, Long idTorneo);

    List<EquipoNBAJugador> buscarJugadoresDelEquipoNBAEnTorneo(Long idEquipo, Long idTorneo);

}
