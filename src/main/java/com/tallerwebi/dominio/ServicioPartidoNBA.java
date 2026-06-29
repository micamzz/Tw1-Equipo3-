package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.excepcion.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ServicioPartidoNBA {

    void agregarPartido(EquipoNBA local, EquipoNBA visitante, LocalDateTime horaInicio, Torneo torneo)
            throws FechaAnteriorInvalidaException, FechaDuplicadaException;

    void finalizarPartido(Long partidoId, Integer minutoFin);

    List<PartidoNBA> obtenerPartidosActivos();

    List<PartidoNBA> obtenerPartidosFinalizados();

    List<PartidoNBA> obtenerPartidosProgramados();

    PartidoNBA obtenerPorId(Long id);

    void agregarCronologiaPuntaje(Long partidoId, Integer minuto, String descripcion, Integer puntos, Long equipoId);

    void agregarCronologiaPlantel(Long partidoId, Integer minuto, Long jugadorSaleId, Long jugadorEntraId);

    List<CronologiaNBA> obtenerCronologiaDePartido(Long partidoId);

    /*
    ScorePartido obtenerScoreLocal(Long partidoId);

    //ScorePartido obtenerScoreVisitante(Long partidoId);

    List<PartidoConScoreDTO> obtenerPartidosActivosConScore();

    List<PartidoConScoreDTO> obtenerPartidosFinalizadosConScore();
*/
    void iniciarPartido(Long partidoId) throws EquipoJugandoException;

    void reprogramarPartido(Long partidoId, LocalDateTime nuevaHoraInicio)
            throws FechaAnteriorInvalidaException, FechaDuplicadaException;

    void cancelarPartido(Long partidoId);

    Integer obtenerPuntosLocal(Long partidoId, Long equipoId);

    Integer obtenerPuntosVisitante(Long partidoId, Long equipoId);
}
