package com.tallerwebi.dominio;

public class PartidoConScoreDTO {

    private PartidoNBA partido;
    private ScorePartido scoreLocal;
    private ScorePartido scoreVisitante;

    public PartidoConScoreDTO(
            PartidoNBA partido,
            ScorePartido scoreLocal,
            ScorePartido scoreVisitante
    ) {
        this.partido = partido;
        this.scoreLocal = scoreLocal;
        this.scoreVisitante = scoreVisitante;
    }

    public PartidoNBA getPartido() {
        return partido;
    }

    public ScorePartido getScoreLocal() {
        return scoreLocal;
    }

    public ScorePartido getScoreVisitante() {
        return scoreVisitante;
    }
}