package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioFormacion {
    void guardar(FormacionPartido formacion);

    List<FormacionPartido> buscarPorPartido(Long idPartido); //Todos los jugadores ya asignados a la formacion de un partido
    List<FormacionPartido> buscarTitularesPorPartidoYEquipo(Long idPartido, Long idEquipo);
    List<FormacionPartido> buscarSuplentesPorPartidoYEquipo(Long idPartido, Long idEquipo);
    boolean jugadorYaEstaEnFormacion(Long idPartido, Long idJugador);
    void eliminar (Long idFormacion);
}
