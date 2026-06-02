package com.tallerwebi.dominio;

public interface ServicioTorneo {


    void crearTorneo(TorneoVirtual torneo);

    TorneoVirtual obtenerTorneoActual();

    Torneo buscarTorneoPorId(Long id);

    void finalizarTorneo(Long id);


}
