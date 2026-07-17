package com.tallerwebi.dominio.torneo;

import com.tallerwebi.dominio.enums.TipoTorneo;

import java.util.List;

public interface RepositorioTorneo {

    void guardarTorneo(Torneo torneo);

    Torneo buscarTorneoPorId(Long id);

    void actualizarTorneo(Torneo torneo);

    List<Torneo> obtenerTorneosPorTipo(TipoTorneo tipoTorneo);

    void eliminarTorneo(Torneo torneo);

    List<Torneo> obtenerTodosLosTorneos();

    Torneo buscarTorneoActual(TipoTorneo tipoTorneo);

    List<Torneo> obtenerTorneosPorTemporada(Long idTemporada);
}
