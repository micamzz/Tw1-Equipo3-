package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.time.LocalTime;
import java.util.List;

public interface ServicioEventoPartido {

    void registrarEvento (Long idPartido,
                          Long idJugador,
                          LocalTime momentoPartido,
                          TipoEstadistica tipoEstadistica,
                          Boolean esLocal) throws PartidoNoEncontradoException, JugadorNoEncontradoException, JugadorNoConvocadoException, MomentoPartidoInvalidoException, PartidoNoEnCursoException;

    List<EventoPartido> buscarEventosPorPartido(Long idPartido);

    List<EventoPartido> buscarEventosPorPartidoYJugador(Long idPartido, Long idJugador);

    List<EventoPartido> obtenerEventosPorPartidoYEquipo(Long partidoId, Long equipoId);
}
