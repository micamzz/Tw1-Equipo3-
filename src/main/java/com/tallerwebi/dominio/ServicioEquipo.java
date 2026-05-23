package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoTitularSinCompletarException;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;

import java.util.List;

public interface ServicioEquipo {

    Equipo guardarEquipo(Equipo equipo);

    Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException;

    Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException;

    void agregarJugadorAlEquipo(Long idEquipo, Long idJugador, Integer numeroDeOrden) throws EquipoNoEncontradoException, PresupuestoInsuficienteException, elJugadorYaExisteEnElEquipoException;

    void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException;

    /* Guarda el id del equipo y el Id de los Jugadores*/
    void guardarEquipoCompleto(Long idEquipo, List<Long> idsJugadores) throws EquipoTitularSinCompletarException, EquipoNoEncontradoException, PresupuestoInsuficienteException;

    List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo);
}
