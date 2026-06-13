package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;

import java.util.List;


public interface ServicioEquipoNBA {

    void agregarJugadorAlEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException;

    void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException;

    void guardarEquipoNBA(EquipoNBA equipo);

    EquipoNBA buscarEquipoPorId(Long id) throws EquipoNoEncontradoException;

    EquipoNBA buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException;


    List<EquipoNBA> obtenerTodosLosEquiposOrdenadosDeMenorAMayor();

    void eliminarEquipoNBA(Long idEquipo) throws EquipoNoEncontradoException;


}
