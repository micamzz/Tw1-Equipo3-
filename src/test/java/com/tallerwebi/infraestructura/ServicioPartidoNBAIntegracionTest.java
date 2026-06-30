package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.temporada.Temporada;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ServicioPartidoNBAIntegracionTest {

    @Autowired
    private ServicioPartidoNBA servicioPartidoNBA;

    @Autowired
    private RepositorioJugador repositorioJugador;

    @Autowired
    private RepositorioEventoPartido repositorioEventoPartido;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void alFinalizarPartidoLosEventosSeAcumulanEnRendimientoJugador() {
        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");
        sessionFactory.getCurrentSession().save(temporada);

        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Test Torneo");
        torneo.setTipoTorneo(TipoTorneo.REAL);
        torneo.setFechaInicio(LocalDate.now().minusMonths(1));
        torneo.setFechaFin(LocalDate.now().plusMonths(1));
        torneo.setTemporada(temporada);
        sessionFactory.getCurrentSession().save(torneo);

        EquipoNBA local = new EquipoNBA();
        local.setNombre("Lakers");
        sessionFactory.getCurrentSession().save(local);

        EquipoNBA visitante = new EquipoNBA();
        visitante.setNombre("Celtics");
        sessionFactory.getCurrentSession().save(visitante);

        Jugador jugador = new Jugador();
        jugador.setDni(12345);
        jugador.setNombre("Stephen");
        jugador.setApellido("Curry");
        sessionFactory.getCurrentSession().save(jugador);

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setEstadoPartido(EstadoPartido.EN_VIVO);
        partido.setTorneo(torneo);
        partido.setHoraInicio(LocalDateTime.now().minusHours(1));
        sessionFactory.getCurrentSession().save(partido);

        crearEvento(partido, jugador, TipoEstadistica.DOBLE, LocalTime.of(0, 5));
        crearEvento(partido, jugador, TipoEstadistica.DOBLE, LocalTime.of(0, 10));
        crearEvento(partido, jugador, TipoEstadistica.TRIPLE, LocalTime.of(0, 15));
        crearEvento(partido, jugador, TipoEstadistica.TIRO_LIBRE, LocalTime.of(0, 20));
        crearEvento(partido, jugador, TipoEstadistica.TIRO_LIBRE, LocalTime.of(0, 25));
        crearEvento(partido, jugador, TipoEstadistica.REBOTE, LocalTime.of(0, 30));
        crearEvento(partido, jugador, TipoEstadistica.REBOTE, LocalTime.of(0, 35));
        crearEvento(partido, jugador, TipoEstadistica.REBOTE, LocalTime.of(0, 40));
        crearEvento(partido, jugador, TipoEstadistica.ASISTENCIA, LocalTime.of(0, 45));
        crearEvento(partido, jugador, TipoEstadistica.ROBO, LocalTime.of(0, 50));
        crearEvento(partido, jugador, TipoEstadistica.TAPA, LocalTime.of(0, 55));
        crearEvento(partido, jugador, TipoEstadistica.PERDIDA, LocalTime.of(1, 0));
        crearEvento(partido, jugador, TipoEstadistica.FALTA_PERSONAL, LocalTime.of(1, 5));

        servicioPartidoNBA.finalizarPartido(partido.getId(), 48);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        RendimientoJugador rendimiento = repositorioJugador.buscarRendimientoPorJugador(jugador.getId());

        assertThat(rendimiento, notNullValue());
        assertThat(rendimiento.getPuntos(), equalTo(9));
        assertThat(rendimiento.getRebotes(), equalTo(3));
        assertThat(rendimiento.getAsistencias(), equalTo(1));
        assertThat(rendimiento.getRobos(), equalTo(1));
        assertThat(rendimiento.getBloqueos(), equalTo(1));
        assertThat(rendimiento.getPerdidas(), equalTo(1));
    }

    @Test
    @Transactional
    @Rollback
    public void alFinalizarPartidoSinEventosNoSeCreaRendimientoJugador() {
        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");
        sessionFactory.getCurrentSession().save(temporada);

        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Test Torneo 2");
        torneo.setTipoTorneo(TipoTorneo.REAL);
        torneo.setFechaInicio(LocalDate.now().minusMonths(1));
        torneo.setFechaFin(LocalDate.now().plusMonths(1));
        torneo.setTemporada(temporada);
        sessionFactory.getCurrentSession().save(torneo);

        EquipoNBA local = new EquipoNBA();
        local.setNombre("Warriors");
        sessionFactory.getCurrentSession().save(local);

        EquipoNBA visitante = new EquipoNBA();
        visitante.setNombre("Bulls");
        sessionFactory.getCurrentSession().save(visitante);

        Jugador jugador = new Jugador();
        jugador.setDni(67890);
        jugador.setNombre("LeBron");
        jugador.setApellido("James");
        sessionFactory.getCurrentSession().save(jugador);

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setEstadoPartido(EstadoPartido.EN_VIVO);
        partido.setTorneo(torneo);
        partido.setHoraInicio(LocalDateTime.now().minusHours(1));
        sessionFactory.getCurrentSession().save(partido);

        servicioPartidoNBA.finalizarPartido(partido.getId(), 48);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        RendimientoJugador rendimiento = repositorioJugador.buscarRendimientoPorJugador(jugador.getId());

        assertThat(rendimiento, nullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void alFinalizarPartidoElEstadoCambiaAFINALIZADO() {
        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");
        sessionFactory.getCurrentSession().save(temporada);

        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Test Torneo 3");
        torneo.setTipoTorneo(TipoTorneo.REAL);
        torneo.setFechaInicio(LocalDate.now().minusMonths(1));
        torneo.setFechaFin(LocalDate.now().plusMonths(1));
        torneo.setTemporada(temporada);
        sessionFactory.getCurrentSession().save(torneo);

        EquipoNBA local = new EquipoNBA();
        local.setNombre("Spurs");
        sessionFactory.getCurrentSession().save(local);

        EquipoNBA visitante = new EquipoNBA();
        visitante.setNombre("Heat");
        sessionFactory.getCurrentSession().save(visitante);

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setEstadoPartido(EstadoPartido.EN_VIVO);
        partido.setTorneo(torneo);
        partido.setHoraInicio(LocalDateTime.now().minusHours(1));
        sessionFactory.getCurrentSession().save(partido);

        servicioPartidoNBA.finalizarPartido(partido.getId(), 48);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        PartidoNBA partidoFinalizado = sessionFactory.getCurrentSession().get(PartidoNBA.class, partido.getId());

        assertThat(partidoFinalizado.getEstadoPartido(), equalTo(EstadoPartido.FINALIZADO));
    }

    private void crearEvento(PartidoNBA partido, Jugador jugador, TipoEstadistica tipo, LocalTime momento) {
        EventoPartido evento = new EventoPartido();
        evento.setPartido(partido);
        evento.setJugador(jugador);
        evento.setTipoEstadistica(tipo);
        evento.setMomentoPartido(momento);
        evento.setEsLocal(true);
        repositorioEventoPartido.guardarEventoPartido(evento);
    }
}
