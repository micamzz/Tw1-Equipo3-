package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.TipoTorneo;
import com.tallerwebi.dominio.Torneo;
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

        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Lakers");

        Jugador jugador = new Jugador();
        jugador.setDni(123456);

        sessionFactory.getCurrentSession().save(equipo);
        sessionFactory.getCurrentSession().save(jugador);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipo);
        asignacion.setJugador(jugador);

        repositorioEquipoNBAJugador.asignarJugadorAUnEquipo(asignacion);

        assertThat(asignacion.getId(), notNullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarUnEquipoYJugadorSeDevuelveLaAsignacionCorrespondiente() {

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

        EquipoNBAJugador resultado = repositorioEquipoNBAJugador.buscarEquipoYJugadorAsociado(equipo.getId(), jugador.getId());

        assertThat(resultado, notNullValue());
        assertThat(resultado.getEquipoNBA().getId(), equalTo(equipo.getId()));
        assertThat(resultado.getJugador().getId(), equalTo(jugador.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarJugadoresDeUnEquipoNBASeDevuelvenLasAsignacionesDelEquipo() {

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

        List<EquipoNBAJugador> listadoJugadoresDeUnEquipo = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBA(equipo.getId());

        assertThat(listadoJugadoresDeUnEquipo.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void alVerificarSiUnJugadorPerteneceAUnEquipoEnElTorneoDevuelveTrue() {

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

        boolean esJugadorDelTorneo = repositorioEquipoNBAJugador.jugadorPerteneceAUnEquipoEnElTorneo(jugador.getId(), torneo.getId());

        assertThat(esJugadorDelTorneo, equalTo(true));
    }

    @Test
    @Transactional
    @Rollback
    public void alBuscarAsignacionesPorTorneoSeDevuelvenLasAsignacionesDeEseTorneo() {

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

        List<EquipoNBAJugador> resultado = repositorioEquipoNBAJugador.buscarAsignacionesPorTorneo(torneo.getId());

        assertThat(resultado.size(), equalTo(1));
    }
}