package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.jugador.Jugador;
import com.tallerwebi.dominio.enums.TipoTorneo;
import com.tallerwebi.dominio.torneo.Torneo;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.EquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioEquipoNBAJugadorTest {

    @Autowired
    private RepositorioEquipoNBAJugador repositorioEquipoNBAJugador;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void sePuedeGuardarUnaAsignacionEquipoNBAJugadorEnLaBdd() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Lakers");

        Jugador jugador = new Jugador();
        jugador.setDni(123456);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipo);
        asignacion.setJugador(jugador);

        // ejecución
        repositorioEquipoNBAJugador.asignarJugadorAUnEquipo(asignacion);

        // verificación
        assertThat(asignacion.getId(), notNullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarUnEquipoYJugadorSeDevuelveLaAsignacionCorrespondiente() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Celtics");

        Jugador jugador = new Jugador();
        jugador.setDni(99999);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipo);
        asignacion.setJugador(jugador);

        sessionFactory.getCurrentSession().save(asignacion);

        // ejecución
        EquipoNBAJugador resultado = repositorioEquipoNBAJugador.buscarEquipoYJugadorAsociado(equipo.getId(), jugador.getId());

        // verificación
        assertThat(resultado, notNullValue());
        assertThat(resultado.getEquipoNBA().getId(), equalTo(equipo.getId()));
        assertThat(resultado.getJugador().getId(), equalTo(jugador.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarJugadoresDeUnEquipoNBASeDevuelvenLasAsignacionesDelEquipo() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Bulls");

        Jugador jugador1 = new Jugador();
        jugador1.setDni(11111);

        Jugador jugador2 = new Jugador();
        jugador2.setDni(22222);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador1);
        sessionFactory.getCurrentSession().save(jugador2);

        EquipoNBAJugador asignacion1 = new EquipoNBAJugador();
        asignacion1.setEquipoNBA(equipo);
        asignacion1.setJugador(jugador1);

        EquipoNBAJugador asignacion2 = new EquipoNBAJugador();
        asignacion2.setEquipoNBA(equipo);
        asignacion2.setJugador(jugador2);

        sessionFactory.getCurrentSession().save(asignacion1);
        sessionFactory.getCurrentSession().save(asignacion2);

        // ejecución
        List<EquipoNBAJugador> listadoJugadoresDeUnEquipo = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBA(equipo.getId());

        // verificación
        assertThat(listadoJugadoresDeUnEquipo.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void alVerificarSiUnJugadorPerteneceAUnEquipoEnElTorneoDevuelveTrue() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Heat");

        Jugador jugador = new Jugador();
        jugador.setDni(88888);

        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");

        /*Se tienen que setear todos los datos porq tiene nullable false*/
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo Heat 2025-2026");
        torneo.setTipoTorneo(TipoTorneo.REAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));
        torneo.setTemporada(temporada);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador);
        sessionFactory.getCurrentSession().save(temporada);
        sessionFactory.getCurrentSession().save(torneo);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipo);
        asignacion.setJugador(jugador);
        asignacion.setTorneo(torneo);

        sessionFactory.getCurrentSession().save(asignacion);

        // ejecución
        boolean esJugadorDelTorneo = repositorioEquipoNBAJugador.jugadorPerteneceAUnEquipoEnElTorneo(jugador.getId(), torneo.getId());

        // verificación
        assertThat(esJugadorDelTorneo, equalTo(true));
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarAsignacionesPorTorneoSeDevuelvenLasAsignacionesDeEseTorneo() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Nuggets");

        Jugador jugador = new Jugador();
        jugador.setDni(99999);

        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");

        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo Nuggets 2025-2026");
        torneo.setTipoTorneo(TipoTorneo.REAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));
        torneo.setTemporada(temporada);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador);
        sessionFactory.getCurrentSession().save(temporada);
        sessionFactory.getCurrentSession().save(torneo);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipo);
        asignacion.setJugador(jugador);
        asignacion.setTorneo(torneo);

        sessionFactory.getCurrentSession().save(asignacion);

        // ejecución
        List<EquipoNBAJugador> resultado = repositorioEquipoNBAJugador.buscarAsignacionesPorTorneo(torneo.getId());

        // verificación
        assertThat(resultado.size(), equalTo(1));
    }

    @Test
    @Transactional
    @Rollback
    public void eliminarJugadorDelEquipoEliminaLaAsignacionDeLaBdd() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Spurs");

        Jugador jugador = new Jugador();
        jugador.setDni(33333);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipo);
        asignacion.setJugador(jugador);

        sessionFactory.getCurrentSession().save(asignacion);

        Long idAsignacion = asignacion.getId();

        // ejecución
        repositorioEquipoNBAJugador.eliminarJugadorDelEquipo(asignacion);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        EquipoNBAJugador asignacionEliminada = sessionFactory.getCurrentSession().get(EquipoNBAJugador.class, idAsignacion);

        // verificación
        assertNull(asignacionEliminada);
    }

    @Test
    @Transactional
    @Rollback
    public void eliminarTodasLasAsignacionesDelEquipoEliminaSoloLasDeEseEquipo() {
        // preparación
        EquipoNBA equipoABorrar = new EquipoNBA();
        equipoABorrar.setNombre("Mavericks");

        EquipoNBA otroEquipo = new EquipoNBA();
        otroEquipo.setNombre("Suns");

        Jugador jugador1 = new Jugador();
        jugador1.setDni(44444);

        Jugador jugador2 = new Jugador();
        jugador2.setDni(55555);

        Jugador jugadorDeOtroEquipo = new Jugador();
        jugadorDeOtroEquipo.setDni(66666);

        sessionFactory.getCurrentSession().save(equipoABorrar);
        sessionFactory.getCurrentSession().save(otroEquipo);
        sessionFactory.getCurrentSession().save(jugador1);
        sessionFactory.getCurrentSession().save(jugador2);
        sessionFactory.getCurrentSession().save(jugadorDeOtroEquipo);

        EquipoNBAJugador asignacion1 = new EquipoNBAJugador();
        asignacion1.setEquipoNBA(equipoABorrar);
        asignacion1.setJugador(jugador1);

        EquipoNBAJugador asignacion2 = new EquipoNBAJugador();
        asignacion2.setEquipoNBA(equipoABorrar);
        asignacion2.setJugador(jugador2);

        EquipoNBAJugador asignacionDeOtroEquipo = new EquipoNBAJugador();
        asignacionDeOtroEquipo.setEquipoNBA(otroEquipo);
        asignacionDeOtroEquipo.setJugador(jugadorDeOtroEquipo);

        sessionFactory.getCurrentSession().save(asignacion1);
        sessionFactory.getCurrentSession().save(asignacion2);
        sessionFactory.getCurrentSession().save(asignacionDeOtroEquipo);

        // ejecución
        repositorioEquipoNBAJugador.eliminarTodasLasAsignacionesDelEquipo(equipoABorrar.getId());

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        List<EquipoNBAJugador> asignacionesDelEquipoBorrado = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBA(equipoABorrar.getId());
        List<EquipoNBAJugador> asignacionesDelOtroEquipo = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBA(otroEquipo.getId());

        // verificación
        assertThat(asignacionesDelEquipoBorrado.size(), equalTo(0));
        assertThat(asignacionesDelOtroEquipo.size(), equalTo(1));
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarJugadoresDelEquipoNBAEnTorneoSeDevuelvenSoloLosDeEseTorneo() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Knicks");

        Jugador jugador1 = new Jugador();
        jugador1.setDni(77771);

        Jugador jugador2 = new Jugador();
        jugador2.setDni(77772);

        Jugador jugadorDeOtroTorneo = new Jugador();
        jugadorDeOtroTorneo.setDni(77773);

        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");

        Torneo torneo1 = new Torneo();
        torneo1.setNombreTorneo("Torneo Knicks A");
        torneo1.setTipoTorneo(TipoTorneo.REAL);
        torneo1.setFechaInicio(LocalDate.now());
        torneo1.setFechaFin(LocalDate.now().plusMonths(6));
        torneo1.setTemporada(temporada);

        Torneo torneo2 = new Torneo();
        torneo2.setNombreTorneo("Torneo Knicks B");
        torneo2.setTipoTorneo(TipoTorneo.REAL);
        torneo2.setFechaInicio(LocalDate.now().plusMonths(7));
        torneo2.setFechaFin(LocalDate.now().plusMonths(12));
        torneo2.setTemporada(temporada);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador1);
        sessionFactory.getCurrentSession().save(jugador2);
        sessionFactory.getCurrentSession().save(jugadorDeOtroTorneo);
        sessionFactory.getCurrentSession().save(temporada);
        sessionFactory.getCurrentSession().save(torneo1);
        sessionFactory.getCurrentSession().save(torneo2);

        EquipoNBAJugador asignacion1 = new EquipoNBAJugador();
        asignacion1.setEquipoNBA(equipo);
        asignacion1.setJugador(jugador1);
        asignacion1.setTorneo(torneo1);

        EquipoNBAJugador asignacion2 = new EquipoNBAJugador();
        asignacion2.setEquipoNBA(equipo);
        asignacion2.setJugador(jugador2);
        asignacion2.setTorneo(torneo1);

        EquipoNBAJugador asignacionDeOtroTorneo = new EquipoNBAJugador();
        asignacionDeOtroTorneo.setEquipoNBA(equipo);
        asignacionDeOtroTorneo.setJugador(jugadorDeOtroTorneo);
        asignacionDeOtroTorneo.setTorneo(torneo2);

        sessionFactory.getCurrentSession().save(asignacion1);
        sessionFactory.getCurrentSession().save(asignacion2);
        sessionFactory.getCurrentSession().save(asignacionDeOtroTorneo);

        // ejecución
        List<EquipoNBAJugador> resultado = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBAEnTorneo(equipo.getId(), torneo1.getId());

        // verificación
        assertThat(resultado.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void existenJugadoresAsignadosEnTorneoDevuelveTrueSiHayAsignaciones() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Clippers");

        Jugador jugador = new Jugador();
        jugador.setDni(88811);

        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");

        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo Clippers 2025-2026");
        torneo.setTipoTorneo(TipoTorneo.REAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));
        torneo.setTemporada(temporada);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador);
        sessionFactory.getCurrentSession().save(temporada);
        sessionFactory.getCurrentSession().save(torneo);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipo);
        asignacion.setJugador(jugador);
        asignacion.setTorneo(torneo);

        sessionFactory.getCurrentSession().save(asignacion);

        // ejecución
        boolean existenAsignaciones = repositorioEquipoNBAJugador.existenJugadoresAsignadosEnTorneo(torneo.getId());

        // verificación
        assertThat(existenAsignaciones, equalTo(true));
    }

    @Test
    @Transactional
    @Rollback
    public void existenJugadoresAsignadosEnTorneoDevuelveFalseSiNoHayAsignaciones() {
        // preparación
        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");

        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo Sin Asignaciones");
        torneo.setTipoTorneo(TipoTorneo.REAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));
        torneo.setTemporada(temporada);

        sessionFactory.getCurrentSession().save(temporada);
        sessionFactory.getCurrentSession().save(torneo);

        // ejecución
        boolean existenAsignaciones = repositorioEquipoNBAJugador.existenJugadoresAsignadosEnTorneo(torneo.getId());

        // verificación
        assertThat(existenAsignaciones, equalTo(false));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarTodasLasAsignacionesDevuelveTodasLasAsignacionesGuardadas() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Pacers");

        Jugador jugador1 = new Jugador();
        jugador1.setDni(91111);

        Jugador jugador2 = new Jugador();
        jugador2.setDni(92222);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador1);
        sessionFactory.getCurrentSession().save(jugador2);

        EquipoNBAJugador asignacion1 = new EquipoNBAJugador();
        asignacion1.setEquipoNBA(equipo);
        asignacion1.setJugador(jugador1);

        EquipoNBAJugador asignacion2 = new EquipoNBAJugador();
        asignacion2.setEquipoNBA(equipo);
        asignacion2.setJugador(jugador2);

        sessionFactory.getCurrentSession().save(asignacion1);
        sessionFactory.getCurrentSession().save(asignacion2);

        // ejecución
        List<EquipoNBAJugador> todasLasAsignaciones = repositorioEquipoNBAJugador.buscarTodasLasAsignaciones();

        // verificación
        assertThat(todasLasAsignaciones.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarEquipoJugadorYTorneoSeDevuelveLaAsignacionCorrespondiente() {
        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Magic");

        Jugador jugador = new Jugador();
        jugador.setDni(95555);

        Temporada temporada = new Temporada();
        temporada.setNombre("2025-2026");

        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo Magic 2025-2026");
        torneo.setTipoTorneo(TipoTorneo.REAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));
        torneo.setTemporada(temporada);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador);
        sessionFactory.getCurrentSession().save(temporada);
        sessionFactory.getCurrentSession().save(torneo);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipo);
        asignacion.setJugador(jugador);
        asignacion.setTorneo(torneo);

        sessionFactory.getCurrentSession().save(asignacion);

        // ejecución
        EquipoNBAJugador resultado = repositorioEquipoNBAJugador.buscarEquipoJugadorYTorneo(equipo.getId(), jugador.getId(), torneo.getId());

        // verificación
        assertThat(resultado, notNullValue());
        assertThat(resultado.getEquipoNBA().getId(), equalTo(equipo.getId()));
        assertThat(resultado.getJugador().getId(), equalTo(jugador.getId()));
        assertThat(resultado.getTorneo().getId(), equalTo(torneo.getId()));
    }
}