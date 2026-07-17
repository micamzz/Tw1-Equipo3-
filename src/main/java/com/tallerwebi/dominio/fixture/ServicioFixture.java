package com.tallerwebi.dominio.fixture;

import java.time.LocalDateTime;
import java.util.List;

import com.tallerwebi.dominio.partidoNBA.PartidoNBA;

public interface ServicioFixture {
    PartidoNBA crearPartidoNBA(Long idEquipoLocal, Long idEquipoVisitante, LocalDateTime horaInicio, Long idTorneo);

    List<PartidoNBA> obtenerTodosLosPartidos();

    List<PartidoNBA> obtenerPartidosPorTorneo(Long idTorneo);

    void abrirPartido(Long idPartido);

    void cerrarPartido(Long idPartido);
}
