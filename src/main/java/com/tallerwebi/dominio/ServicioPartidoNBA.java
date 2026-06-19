/*
package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.temporada.Temporada;

import java.time.LocalDateTime;
import java.util.List;

public interface ServicioPartidoNBA {

    void agregarPartido(EquipoNBA local, EquipoNBA visitante, LocalDateTime horaInicio, Temporada temporada);

    void finalizarPartido(Long partidoId, Integer minutoFin);

    List<PartidoNBA> obtenerPartidosActivos();

    List<PartidoNBA> obtenerPartidosFinalizados();

    PartidoNBA obtenerPorId(Long id);

    void agregarCronologiaPuntaje(Long partidoId, Integer minuto, String descripcion, Integer puntos, Long equipoId);

    void agregarCronologiaPlantel(Long partidoId, Integer minuto, Long jugadorSaleId, Long jugadorEntraId);

    List<CronologiaNBA> obtenerCronologiaDePartido(Long partidoId);

    ScorePartido obtenerScoreLocal(Long partidoId);

    ScorePartido obtenerScoreVisitante(Long partidoId);

    List<PartidoConScoreDTO> obtenerPartidosActivosConScore();

    List<PartidoConScoreDTO> obtenerPartidosFinalizadosConScore();
}*/
