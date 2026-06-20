package com.tallerwebi.dominio.equipoNBA;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;

import java.util.List;


public interface ServicioEquipoNBA {

    void crearEquipoNBA(EquipoNBA equipo);

    void eliminarEquipoNBA(Long idEquipo) throws EquipoNoEncontradoException;

    EquipoNBA buscarEquipoPorId(Long id) throws EquipoNoEncontradoException;

    List<EquipoNBA> obtenerTodosLosEquiposOrdenadosDeMenorAMayor();


}
