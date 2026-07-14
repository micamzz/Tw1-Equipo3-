package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.excepcion.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ServicioPartidoNBA {

    void agregarPartido(EquipoNBA local, EquipoNBA visitante, Long idfecha, LocalDateTime horaInicio, Torneo torneo)
            throws FechaAnteriorInvalidaException, FechaDuplicadaException;

    void finalizarPartido(Long partidoId, Integer minutoFin);

    List<PartidoNBA> obtenerPartidosActivos();

    List<PartidoNBA> obtenerPartidosFinalizados();

    List<PartidoNBA> obtenerPartidosProgramados();

    PartidoNBA obtenerPorId(Long id);

    void agregarCronologiaPuntaje(Long partidoId, Integer minuto, String descripcion, Integer puntos, Long equipoId);

    void agregarCronologiaPlantel(Long partidoId, Integer minuto, Long jugadorSaleId, Long jugadorEntraId);

    List<CronologiaNBA> obtenerCronologiaDePartido(Long partidoId);

    void iniciarPartido(Long partidoId) throws EquipoJugandoException, FechaNoEstaEnCursoException;

    void reprogramarPartido(Long partidoId, LocalDateTime nuevaHoraInicio)
            throws FechaAnteriorInvalidaException, FechaDuplicadaException;

    void cancelarPartido(Long partidoId);

    void calcularPuntaje(PartidoNBA partido);

    List<PartidoNBA> obtenerPartidosPorFecha(Long idFecha);

    void crearPartidoEnFecha(Long idFecha, Long idEquipoLocal, Long idEquipoVisitante, LocalDateTime horaInicio) throws FechaNoEncontradaException;

    List<PartidoNBA> obtenerPartidosActivosPorFecha(Long idFecha);

    List<PartidoNBA> obtenerPartidosProgramadosPorFecha(Long idFecha);

    List<PartidoNBA> obtenerPartidosFinalizadosPorFecha(Long idFecha);
}
