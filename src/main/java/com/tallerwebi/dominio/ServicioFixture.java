package com.tallerwebi.dominio;

import java.time.LocalDateTime;
import java.util.List;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.temporada.Temporada;

public interface ServicioFixture {
    PartidoNBA crearPartidoNBA(Long idEquipoLocal, Long idEquipoVisitante, LocalDateTime horaInicio, Long idTemporada);
    List<PartidoNBA> obtenerTodosLosPartidos();
    List<PartidoNBA> obtenerPartidosPorTemporada(Long idTemporada);
    void abrirPartido(Long idPartido);
    void cerrarPartido(Long idPartido);
}
