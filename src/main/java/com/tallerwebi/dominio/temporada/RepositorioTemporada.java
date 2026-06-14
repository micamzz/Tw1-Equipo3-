package com.tallerwebi.dominio.temporada;

import java.util.List;

public interface RepositorioTemporada {


    void guardar(Temporada temporada);

    Temporada obtenerTemporadaActual();

    List<Temporada> obtenerTodas();


}
