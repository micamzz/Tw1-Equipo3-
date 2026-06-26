package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServicioPartidoNBAImpl implements ServicioPartidoNBA {

    private final RepositorioPartidoNBA repositorioPartidoNBA;
    private final RepositorioCronologiaNBA repositorioCronologiaNBA;
    private final RepositorioScorePartido repositorioScorePartido;
    private final RepositorioEquipoNBA repositorioEquipoNBA;
    private final RepositorioJugador repositorioJugador;
    private final RepositorioEventoPartido repositorioEventoPartido;

    @Autowired
    public ServicioPartidoNBAImpl(RepositorioPartidoNBA repositorioPartidoNBA,
                                  RepositorioCronologiaNBA repositorioCronologiaNBA,
                                  RepositorioScorePartido repositorioScorePartido,
                                  RepositorioEquipoNBA repositorioEquipoNBA,
                                  RepositorioJugador repositorioJugador,
                                  RepositorioEventoPartido repositorioEventoPartido) {
        this.repositorioPartidoNBA = repositorioPartidoNBA;
        this.repositorioCronologiaNBA = repositorioCronologiaNBA;
        this.repositorioScorePartido = repositorioScorePartido;
        this.repositorioEquipoNBA = repositorioEquipoNBA;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEventoPartido = repositorioEventoPartido;
    }

    @Override
    public void agregarPartido(EquipoNBA local, EquipoNBA visitante, LocalDateTime horaInicio, Torneo torneo)
            throws FechaAnteriorInvalidaException, FechaDuplicadaException {

        if (local == null || visitante == null) {
            throw new EquiposIgualesException("Debe seleccionar dos equipos");
        }
        if (local.getId().equals(visitante.getId())) {
            throw new EquiposIgualesException("El equipo local y visitante no pueden ser el mismo");
        }

        if (horaInicio.toLocalDate().isBefore(torneo.getFechaInicio())) {
            throw new FechaAnteriorInvalidaException("La fecha del partido debe ser posterior o igual al inicio del torneo");
        }

        if (repositorioPartidoNBA.existePartidoEnFecha(horaInicio)) {
            throw new FechaDuplicadaException("Ya existe un partido programado en esa fecha y hora");
        }

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setHoraInicio(horaInicio);
        partido.setMinutoFin(null);
        partido.setTorneo(torneo);
        partido.setEstadoPartido(EstadoPartido.PROGRAMADO);
        repositorioPartidoNBA.guardar(partido);

        repositorioScorePartido.guardar(new ScorePartido(partido, local));
        repositorioScorePartido.guardar(new ScorePartido(partido, visitante));
    }

    @Override
    public void finalizarPartido(Long partidoId, Integer minutoFin) {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);
        if (!partido.estaActivo()) {
            throw new PartidoFinalizadoException("El partido ya fue finalizado");
        }
        partido.setMinutoFin(minutoFin);
        partido.setEstadoPartido(EstadoPartido.FINALIZADO);
        repositorioPartidoNBA.actualizar(partido);
        actualizarRendimientos(partido);
    }

    private void actualizarRendimientos(PartidoNBA partido) {
        List<EventoPartido> eventos = repositorioEventoPartido.buscarEventosPorPartido(partido.getId());
        if (eventos == null || eventos.isEmpty()) return;

        Map<Long, List<EventoPartido>> eventosPorJugador = new HashMap<>();
        for (EventoPartido e : eventos) {
            Long jugadorId = e.getJugador().getId();
            eventosPorJugador.computeIfAbsent(jugadorId, k -> new ArrayList<>()).add(e);
        }

        for (Map.Entry<Long, List<EventoPartido>> entry : eventosPorJugador.entrySet()) {
            Long jugadorId = entry.getKey();
            List<EventoPartido> eventosJugador = entry.getValue();

            int puntos = 0, rebotes = 0, asistencias = 0;
            int robos = 0, bloqueos = 0, perdidas = 0;

            for (EventoPartido e : eventosJugador) {
                switch (e.getTipoEstadistica()) {
                    case TIRO_LIBRE:  puntos += 1; break;
                    case DOBLE:       puntos += 2; break;
                    case TRIPLE:      puntos += 3; break;
                    case REBOTE:      rebotes++; break;
                    case ASISTENCIA:  asistencias++; break;
                    case ROBO:        robos++; break;
                    case TAPA:        bloqueos++; break;
                    case PERDIDA:     perdidas++; break;
                }
            }

            RendimientoJugador rend = repositorioJugador.buscarRendimientoPorJugador(jugadorId);
            if (rend == null) {
                Jugador jugador = repositorioJugador.buscarJugadorPorId(jugadorId);
                rend = new RendimientoJugador();
                rend.setJugador(jugador);
                rend.setPartidoNBA(partido);
                rend.setNombreCompleto(jugador.getNombre() + " " + jugador.getApellido());
                rend.setPuntos(0);
                rend.setRebotes(0);
                rend.setAsistencias(0);
                rend.setRobos(0);
                rend.setBloqueos(0);
                rend.setPerdidas(0);
            }

            rend.setPuntos(rend.getPuntos() + puntos);
            rend.setRebotes(rend.getRebotes() + rebotes);
            rend.setAsistencias(rend.getAsistencias() + asistencias);
            rend.setRobos(rend.getRobos() + robos);
            rend.setBloqueos(rend.getBloqueos() + bloqueos);
            rend.setPerdidas(rend.getPerdidas() + perdidas);

            repositorioJugador.guardarRendimiento(rend);
        }
    }

    @Override
    public List<PartidoNBA> obtenerPartidosActivos() {
        return repositorioPartidoNBA.buscarPartidosActivos();
    }

    @Override
    public List<PartidoNBA> obtenerPartidosFinalizados() {
        return repositorioPartidoNBA.buscarPartidosFinalizados();
    }

    @Override
    public List<PartidoNBA> obtenerPartidosProgramados() {
        return repositorioPartidoNBA.buscarPartidosProgramados();
    }

    @Override
    public PartidoNBA obtenerPorId(Long id) {
        return repositorioPartidoNBA.buscarPorId(id);
    }

    @Override
    public void agregarCronologiaPuntaje(Long partidoId, Integer minuto, String descripcion, Integer puntos, Long equipoId) {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);

        if (!partido.estaActivo()) {
            throw new PartidoFinalizadoException("No se puede agregar cronologia a un partido finalizado");
        }

        EquipoNBA equipo = repositorioEquipoNBA.buscarEquipoPorId(equipoId);

        CronologiaNBA cronologia = new CronologiaNBA();
        cronologia.setPartido(partido);
        cronologia.setMinuto(minuto);
        cronologia.setTipo("PUNTAJE");
        cronologia.setDescripcion(descripcion);
        cronologia.setPuntosSumados(puntos);
        cronologia.setEquipoBeneficiado(equipo);
        repositorioCronologiaNBA.guardar(cronologia);

        ScorePartido score = repositorioScorePartido.buscarPorPartidoYEquipo(partidoId, equipoId);
        score.sumarPuntos(puntos);
        repositorioScorePartido.actualizar(score);
    }

    @Override
    public void agregarCronologiaPlantel(Long partidoId, Integer minuto, Long jugadorSaleId, Long jugadorEntraId) {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);
        if (!partido.estaActivo()) {
            throw new PartidoFinalizadoException("No se puede agregar cronologia a un partido finalizado");
        }

        Jugador jugadorSale = repositorioJugador.buscarJugadorPorId(jugadorSaleId);
        Jugador jugadorEntra = repositorioJugador.buscarJugadorPorId(jugadorEntraId);

        CronologiaNBA cronologia = new CronologiaNBA();
        cronologia.setPartido(partido);
        cronologia.setMinuto(minuto);
        cronologia.setTipo("PLANTEL");
        cronologia.setDescripcion(jugadorSale.getNombre() + " " + jugadorSale.getApellido()
                + " sale, entra " + jugadorEntra.getNombre() + " " + jugadorEntra.getApellido());
        cronologia.setJugadorSale(jugadorSale);
        cronologia.setJugadorEntra(jugadorEntra);
        repositorioCronologiaNBA.guardar(cronologia);
    }

    @Override
    public List<CronologiaNBA> obtenerCronologiaDePartido(Long partidoId) {
        return repositorioCronologiaNBA.buscarPorPartido(partidoId);
    }

    @Override
    public ScorePartido obtenerScoreLocal(Long partidoId) {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);

        ScorePartido scoreLocal = repositorioScorePartido.buscarPorPartidoYEquipo(partidoId, partido.getEquipoLocal().getId());

        if (scoreLocal == null) {
            scoreLocal = new ScorePartido(partido, partido.getEquipoLocal());
            repositorioScorePartido.guardar(scoreLocal);
        }

        return scoreLocal;
    }

    @Override
    public ScorePartido obtenerScoreVisitante(Long partidoId) {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);
        ScorePartido scoreVisitante = repositorioScorePartido.buscarPorPartidoYEquipo(partidoId, partido.getEquipoVisitante().getId());

        if (scoreVisitante == null) {
            scoreVisitante = new ScorePartido(partido, partido.getEquipoVisitante());
            repositorioScorePartido.guardar(scoreVisitante);
        }

        return scoreVisitante;

    }

    @Override
    public List<PartidoConScoreDTO> obtenerPartidosActivosConScore() {

        List<PartidoConScoreDTO> resultado = new ArrayList<>();

        for (PartidoNBA partido : obtenerPartidosActivos()) {
            resultado.add(
                    new PartidoConScoreDTO(
                            partido,
                            obtenerScoreLocal(partido.getId()),
                            obtenerScoreVisitante(partido.getId())
                    )
            );
        }

        return resultado;
    }

    @Override
    public List<PartidoConScoreDTO> obtenerPartidosFinalizadosConScore() {

        List<PartidoConScoreDTO> resultado = new ArrayList<>();

        for (PartidoNBA partido : obtenerPartidosFinalizados()) {
            resultado.add(
                    new PartidoConScoreDTO(
                            partido,
                            obtenerScoreLocal(partido.getId()),
                            obtenerScoreVisitante(partido.getId())
                    )
            );
        }

        return resultado;
    }

    @Override
    public void iniciarPartido(Long partidoId) throws EquipoJugandoException {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);
        if (repositorioPartidoNBA.equipoTienePartidoActivo(partido.getEquipoLocal().getId())) {
            throw new EquipoJugandoException("El equipo local ya tiene un partido en vivo");
        }
        if (repositorioPartidoNBA.equipoTienePartidoActivo(partido.getEquipoVisitante().getId())) {
            throw new EquipoJugandoException("El equipo visitante ya tiene un partido en vivo");
        }
        partido.setEstadoPartido(EstadoPartido.EN_VIVO);
        repositorioPartidoNBA.actualizar(partido);
    }

    @Override
    public void reprogramarPartido(Long partidoId, LocalDateTime nuevaHoraInicio)
            throws FechaAnteriorInvalidaException, FechaDuplicadaException {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);
        if (nuevaHoraInicio.toLocalDate().isBefore(partido.getTorneo().getFechaInicio())) {
            throw new FechaAnteriorInvalidaException("La fecha del partido debe ser posterior o igual al inicio del torneo");
        }
        if (repositorioPartidoNBA.existePartidoEnFecha(nuevaHoraInicio)) {
            throw new FechaDuplicadaException("Ya existe un partido programado en esa fecha y hora");
        }
        partido.setHoraInicio(nuevaHoraInicio);
        repositorioPartidoNBA.actualizar(partido);
    }

    @Override
    public void cancelarPartido(Long partidoId) {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);
        repositorioPartidoNBA.eliminar(partido);
    }
}
