package com.tallerwebi.dominio;

public interface RepositorioTorneo {

    void guardarTorneo(Torneo torneo);
    Torneo buscarTorneoPorId(Long id);
    TorneoVirtual buscarTorneoVirtualActual();
    void actualizarTorneo(Torneo torneo);

}
