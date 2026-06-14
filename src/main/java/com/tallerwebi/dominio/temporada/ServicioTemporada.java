package com.tallerwebi.dominio.temporada;

import java.util.List;

public interface ServicioTemporada {
    void guardarTemporada(Temporada temporada);

    Temporada obtenerTemporadaActual();

    List<Temporada> obtenerTodasLasTemporadas();

}
