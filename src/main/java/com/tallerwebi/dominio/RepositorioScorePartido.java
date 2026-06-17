package com.tallerwebi.dominio;

public interface RepositorioScorePartido {

    void guardar(ScorePartido score);

    void actualizar(ScorePartido score);

    ScorePartido buscarPorPartidoYEquipo(Long partidoId, Long equipoId);
}