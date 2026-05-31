package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioEquipo {

    void agregarJugadorAlEquipo(Long idEquipo, Long idJugador, Integer numeroDeOrden) throws EquipoNoEncontradoException, PresupuestoInsuficienteException, elJugadorYaExisteEnElEquipoException;

    void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException;

    Equipo guardarEquipo(Equipo equipo) throws TorneoVirtualActualNoEncontradoException;

    List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo);

    Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException;

    Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException;

    void validarEquipoCompleto(Long idEquipo) throws EquipoSinCompletarException;

}
