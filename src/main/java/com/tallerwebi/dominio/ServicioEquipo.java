package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoSinCompletarException;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;

import java.util.List;

public interface ServicioEquipo {

    void agregarJugadorAlEquipo(Long idEquipo, Long idJugador, Integer numeroDeOrden) throws EquipoNoEncontradoException, PresupuestoInsuficienteException, elJugadorYaExisteEnElEquipoException;

    void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException;

    Equipo guardarEquipo(Equipo equipo);

    Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException;

    Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException;

    void validarEquipoCompleto(Long idEquipo) throws EquipoSinCompletarException;

    List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo);
}
