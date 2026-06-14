package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipo.Equipo;

public abstract class Partido {
    private final Equipo equipoLocal;
    private final Equipo equipoVisitante;

    protected Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }
}
