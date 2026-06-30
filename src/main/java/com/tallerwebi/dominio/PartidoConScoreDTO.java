package com.tallerwebi.dominio;

public class PartidoConScoreDTO {

    private final PartidoNBA partido;
    private ScorePartido scoreLocal; //le saque el final
    private ScorePartido scoreVisitante; //le saque el final

    private Integer tanteadorLocal = 0;
    private Integer tanteadorVisitante = 0;

    public PartidoConScoreDTO(
            PartidoNBA partido,
            Integer tanteadorLocal,
            Integer tanteadorVisitante
    ) {
        this.partido = partido;
        this.tanteadorLocal = tanteadorLocal;
        this.tanteadorVisitante = tanteadorVisitante;
    }


    public Integer getTanteadorLocal() {
        return tanteadorLocal;
    }

    public Integer getTanteadorVisitante() {
        return tanteadorVisitante;
    }
    public void setTanteadorLocal(Integer tanteadorLocal) {
        this.tanteadorLocal = tanteadorLocal;
    }

    public void setTanteadorVisitante(Integer tanteadorVisitante) {
        this.tanteadorVisitante = tanteadorVisitante;
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