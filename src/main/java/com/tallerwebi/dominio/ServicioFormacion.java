package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioFormacion {

    void registrarJugador (Long idPartido, Long idEquipo, Long idJugador);

    List<Formacion> obtenerFormacionPorPartido(Long idPartido);

    List<Formacion> obtenerFormacionPorPartidoYEquipo(Long idPartido, Long idEquipo);
}
