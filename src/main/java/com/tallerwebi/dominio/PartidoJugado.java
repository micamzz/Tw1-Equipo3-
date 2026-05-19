package com.tallerwebi.dominio;

public class PartidoJugado extends Partido {
    private int puntosLocal;
    private int puntosVisitante;

    public PartidoJugado(Equipo equipoLocal, Equipo equipoVisitante, int puntosLocal, int puntosVisitante) {
        super(equipoLocal, equipoVisitante);
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

}
