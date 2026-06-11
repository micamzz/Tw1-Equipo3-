package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioTemporada {
    void guardarTemporada(Temporada temporada);

    Temporada obtenerTemporadaActual();

    List<Temporada> obtenerTodasLasTemporadas();

}
