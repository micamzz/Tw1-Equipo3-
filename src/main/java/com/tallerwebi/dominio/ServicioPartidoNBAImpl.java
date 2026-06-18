package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.temporada.Temporada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioPartidoNBAImpl implements ServicioPartidoNBA {

    private final RepositorioPartidoNBA repositorioPartidoNBA;
    private final RepositorioCronologiaNBA repositorioCronologiaNBA;
    private final RepositorioScorePartido repositorioScorePartido;
    private final RepositorioEquipoNBA repositorioEquipoNBA;
    private final RepositorioJugador repositorioJugador;

    @Autowired
    public ServicioPartidoNBAImpl(RepositorioPartidoNBA repositorioPartidoNBA,
                                  RepositorioCronologiaNBA repositorioCronologiaNBA,
                                  RepositorioScorePartido repositorioScorePartido,
                                  RepositorioEquipoNBA repositorioEquipoNBA,
                                  RepositorioJugador repositorioJugador) {
        this.repositorioPartidoNBA = repositorioPartidoNBA;
        this.repositorioCronologiaNBA = repositorioCronologiaNBA;
        this.repositorioScorePartido = repositorioScorePartido;
        this.repositorioEquipoNBA = repositorioEquipoNBA;
        this.repositorioJugador = repositorioJugador;
    }

    @Override
    public void agregarPartido(EquipoNBA local, EquipoNBA visitante, LocalDateTime horaInicio, Temporada temporada) {

        if (local == null || visitante == null) {
            throw new EquiposIgualesException("Los equipos no pueden estar vacios");
        }
        if (local.getId().equals(visitante.getId())) {
            throw new EquiposIgualesException("El equipo local y visitante no pueden ser el mismo");
        }

        if (repositorioPartidoNBA.equipoTienePartidoActivo(local.getId())) {
            throw new PartidoYaActivoException("El equipo local ya tiene un partido activo");
        }
        if (repositorioPartidoNBA.equipoTienePartidoActivo(visitante.getId())) {
            throw new PartidoYaActivoException("El equipo visitante ya tiene un partido activo");
        }

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setHoraInicio(horaInicio);
        partido.setMinutoFin(null); // activo
        partido.setTemporada(temporada);
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
        repositorioPartidoNBA.actualizar(partido);
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
        return repositorioScorePartido.buscarPorPartidoYEquipo(partidoId, partido.getEquipoLocal().getId());
    }

    @Override
    public ScorePartido obtenerScoreVisitante(Long partidoId) {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(partidoId);
        return repositorioScorePartido.buscarPorPartidoYEquipo(partidoId, partido.getEquipoVisitante().getId());
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


}