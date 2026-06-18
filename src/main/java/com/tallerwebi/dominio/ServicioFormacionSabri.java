package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioFormacionSabri {

    void registrarJugador (Long idPartido, Long idEquipo, Long idJugador) throws EquipoNoEncontradoException, PartidoNoEncontradoException, JugadorNoEncontradoException, EquipoNoParticipaEnPartidoException, JugadorNoPerteneceAlEquipoException, FormacionDuplicadaException;

    List<Formacion> obtenerFormacionPorPartido(Long idPartido);

    List<Formacion> obtenerFormacionPorPartidoYEquipo(Long idPartido, Long idEquipo);

    Boolean existeJugadorEnFormacion(Long idJugador, Long idPartido);
}
