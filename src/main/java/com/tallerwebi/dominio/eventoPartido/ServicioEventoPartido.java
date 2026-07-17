package com.tallerwebi.dominio.eventoPartido;

import com.tallerwebi.dominio.enums.TipoEstadistica;
import com.tallerwebi.dominio.excepcion.*;

import java.time.LocalTime;
import java.util.List;

public interface ServicioEventoPartido {

    void registrarEvento(Long idPartido,
                         Long idJugador,
                         LocalTime momentoPartido,
                         TipoEstadistica tipoEstadistica,
                         Boolean esLocal) throws PartidoNoEncontradoException, JugadorNoEncontradoException, JugadorNoConvocadoException, MomentoPartidoInvalidoException, PartidoNoEnCursoException;

    List<EventoPartido> buscarEventosPorPartido(Long idPartido);


    void eliminarEventoPorId(Long idEvento) throws EventoNoEncontradoException;
}
