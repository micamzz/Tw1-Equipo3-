package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public class PartidoJugado extends Partido {
    private int puntosLocal;
    private int puntosVisitante;
    private List<RendimientoJugador> rendimientoEquipoLocal;
    private List<RendimientoJugador> rendimientoEquipoVisitante;

    public PartidoJugado(Equipo equipoLocal, Equipo equipoVisitante, int puntosLocal, int puntosVisitante) {
        this(equipoLocal, equipoVisitante, puntosLocal, puntosVisitante, new ArrayList<>(), new ArrayList<>());
    }

    public PartidoJugado(Equipo equipoLocal, Equipo equipoVisitante, int puntosLocal, int puntosVisitante,
            List<RendimientoJugador> rendimientoEquipoLocal, List<RendimientoJugador> rendimientoEquipoVisitante) {
        super(equipoLocal, equipoVisitante);
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
        this.rendimientoEquipoLocal = rendimientoEquipoLocal;
        this.rendimientoEquipoVisitante = rendimientoEquipoVisitante;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public List<RendimientoJugador> getRendimientoEquipoLocal() {
        return rendimientoEquipoLocal;
    }

    public void setRendimientoEquipoLocal(List<RendimientoJugador> rendimientoEquipoLocal) {
        this.rendimientoEquipoLocal = rendimientoEquipoLocal;
    }

    public List<RendimientoJugador> getRendimientoEquipoVisitante() {
        return rendimientoEquipoVisitante;
    }

    public void setRendimientoEquipoVisitante(List<RendimientoJugador> rendimientoEquipoVisitante) {
        this.rendimientoEquipoVisitante = rendimientoEquipoVisitante;
    }

}
