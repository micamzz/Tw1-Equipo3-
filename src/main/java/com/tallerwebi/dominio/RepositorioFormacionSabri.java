package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioFormacionSabri {

    void guardarFormacion(Formacion formacion);

    List<Formacion> obtenerJugadoresPorPartido (Long idPartido);

    List<Formacion> buscarJugadoresPorEquipoYPartido (Long idEquipo, Long idPartido);

    boolean existeJugadorEnFormacion (Long idJugador, Long idPartido);
}
