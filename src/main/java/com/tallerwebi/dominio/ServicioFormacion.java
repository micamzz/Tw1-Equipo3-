package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;

import java.util.List;

public interface ServicioFormacion {
    void agregarJugador(Long idPartido, Long idEquipo, Long idJugador);
    void quitarJugador(Long idFormacion);
    List<FormacionPartido> obtenerFormacion(Long idPartido);
    List<FormacionPartido> obtenerFormacionPorEquipo(Long idPartido, Long idEquipo);
    boolean jugadorYaEstasEnFormacion(Long idPartido, Long idJugador);
}
