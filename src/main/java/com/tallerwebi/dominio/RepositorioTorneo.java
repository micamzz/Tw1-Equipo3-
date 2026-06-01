package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioTorneo {

    void guardarTorneo(Torneo torneo);
    Torneo buscarTorneoPorId(Long id);
    TorneoVirtual buscarTorneoVirtualActual();
    void actualizarTorneo(Torneo torneo);
    List<TorneoVirtual> obtenerTodosLosTorneosVirtuales();
    void eliminarTorneo(Torneo torneo);
}
