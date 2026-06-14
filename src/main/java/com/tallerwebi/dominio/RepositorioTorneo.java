package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioTorneo {

    void guardarTorneo(Torneo torneo);
    Torneo buscarTorneoPorId(Long id);
    void actualizarTorneo(Torneo torneo);
    List<Torneo> obtenerTorneosPorTipo(TipoTorneo tipoTorneo);
    void eliminarTorneo(Torneo torneo);
    List<Torneo> obtenerTodosLosTorneos();
    Torneo buscarTorneoActual(TipoTorneo tipoTorneo);
}
