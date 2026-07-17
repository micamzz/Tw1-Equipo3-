package com.tallerwebi.dominio.fecha;

import com.tallerwebi.dominio.enums.EstadoFecha;
import com.tallerwebi.dominio.excepcion.FechaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.TorneoNoEncontradoException;

public interface ServicioFecha {
    void registrarFecha(Long idTorneo, Integer numeroFecha, EstadoFecha estadoFecha) throws TorneoNoEncontradoException;

    Fecha obtenerFechaPorId(Long id) throws FechaNoEncontradaException;

    void actualizarFecha(Long idFecha, Integer numero, EstadoFecha estado) throws FechaNoEncontradaException;

    void eliminarFecha(Long id) throws FechaNoEncontradaException;

    java.util.List<Fecha> obtenerTodasLasFechas();

    Fecha obtenerFechaActual() throws FechaNoEncontradaException;

    ;
}
