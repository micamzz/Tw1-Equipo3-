package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;

import java.util.List;

public interface ServicioEquipo {

    Equipo guardarEquipo(Equipo equipo);

    Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException;

    Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException;

    /* Guarda el id del equipo y el Id de los Jugadores*/
    void guardarEquipoCompleto(Long idEquipo, Long baseTitular1, Long baseTitular2, Long aleroTitular1, Long aleroTitular2, Long pivotTitular, Long baseSuplente1, Long baseSuplente2, Long aleroSuplente1, Long aleroSuplente2, Long pivotSuplente);

    List<EquipoJugador> buscarJugadoresDelEquipo(Long id);
}
