package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.JugadorNoConvocadoException;
import com.tallerwebi.dominio.excepcion.JugadorNoEncontradoException;
import com.tallerwebi.dominio.excepcion.MomentoPartidoInvalidoException;
import com.tallerwebi.dominio.excepcion.PartidoNoEncontradoException;

import java.time.LocalTime;
import java.util.List;

public interface ServicioEventoPartido {

    void registrarEvento (Long idPartido,
                          Long idJugador,
                          LocalTime momentoPartido,
                          TipoEstadistica tipoEstadistica) throws PartidoNoEncontradoException, JugadorNoEncontradoException, JugadorNoConvocadoException, MomentoPartidoInvalidoException;

    List<EventoPartido> buscarEventosPorPartido(Long idPartido);

    List<EventoPartido> buscarEventosPorPartidoYJugador(Long idPartido, Long idJugador);
}
