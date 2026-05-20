package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoTitularSinCompletarException;
import com.tallerwebi.dominio.excepcion.CapitanNoEsTitularException;
import java.util.List;

public interface ServicioEquipo {

    Equipo guardarEquipo(Equipo equipo);

    Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException;

    Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException;

    /* Guarda el id del equipo y el Id de los Jugadores*/
    void guardarEquipoCompleto(Long idEquipo, List<Long> idsJugadores, Long idCapitan) throws EquipoTitularSinCompletarException, CapitanNoEsTitularException;

    List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo);
}
