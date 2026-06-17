package com.tallerwebi.dominio.temporada;

import com.tallerwebi.dominio.excepcion.FechaFinAnteriorAInicioException;
import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;
import com.tallerwebi.dominio.excepcion.TemporadaFueraDeRangoException;

import java.time.LocalDate;
import java.util.List;

public interface ServicioTemporada {

    void guardarTemporada(Temporada temporada);

    Temporada obtenerTemporadaActual() throws TemporadaActualNoEncontradaException;

    List<Temporada> obtenerTodasLasTemporadas();

    Temporada obtenerTemporadaPorId(Long idTemporada);

    // Finaliza la temporada activa y crea la del siguiente año
    void finalizarTemporada(LocalDate fechaFin);
}