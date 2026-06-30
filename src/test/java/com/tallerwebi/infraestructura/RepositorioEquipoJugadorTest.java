package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.equipo.Equipo;
import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
import com.tallerwebi.dominio.equipoJugador.RepositorioEquipoJugador;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioEquipoJugadorTest {

    @Autowired
    private RepositorioEquipoJugador repositorioEquipoJugador;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void sePuedeGuardarUnEquipoJugadorEnLaBdd() {
        // preparación
        EquipoJugador equipoJugador = new EquipoJugador();

        // ejecución
        repositorioEquipoJugador.guardarEquipoJugador(equipoJugador);

        // Verificación
        assertThat(equipoJugador.getId(), notNullValue());
    }


    @Test
    @Transactional
    @Rollback
    public void AlBuscarUnEquipoYJugadorSeDevuelveElEquipoJugadorPerteneciente() {
        // preparación
        Equipo equipo = new Equipo();
        Jugador jugador1 = new Jugador();
        jugador1.setDni(38662);
        this.sessionFactory.getCurrentSession().save(equipo);
        this.sessionFactory.getCurrentSession().save(jugador1);

        EquipoJugador equipoJugador = new EquipoJugador();
        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(jugador1);

        this.sessionFactory.getCurrentSession().save(equipoJugador);

        // ejecución
        EquipoJugador equipoJugadorBuscado = repositorioEquipoJugador.buscarEquipoYJugadorAsociado(equipo.getId(), jugador1.getId());

        // Verificación
        assertThat(equipoJugadorBuscado, notNullValue());
        assertThat(equipoJugadorBuscado.getEquipo().getId(), equalTo(equipo.getId()));
        assertThat(equipoJugadorBuscado.getJugador().getId(), equalTo(jugador1.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarPorEquipoIdDevuelveTodosLosEquipoJugadorDeEseEquipo() {
        // preparación
        Equipo equipo = new Equipo();
        this.sessionFactory.getCurrentSession().save(equipo);

        Jugador jugador1 = new Jugador();
        jugador1.setDni(38662);
        Jugador jugador2 = new Jugador();
        jugador2.setDni(40123);
        this.sessionFactory.getCurrentSession().save(jugador1);
        this.sessionFactory.getCurrentSession().save(jugador2);

        EquipoJugador equipoJugador1 = new EquipoJugador();
        equipoJugador1.setEquipo(equipo);
        equipoJugador1.setJugador(jugador1);

        EquipoJugador equipoJugador2 = new EquipoJugador();
        equipoJugador2.setEquipo(equipo);
        equipoJugador2.setJugador(jugador2);

        this.sessionFactory.getCurrentSession().save(equipoJugador1);
        this.sessionFactory.getCurrentSession().save(equipoJugador2);

        // ejecución
        List<EquipoJugador> equipoJugadores = repositorioEquipoJugador.buscarPorEquipoId(equipo.getId());

        // Verificación
        assertThat(equipoJugadores, hasSize(2));
        assertThat(equipoJugadores, containsInAnyOrder(equipoJugador1, equipoJugador2));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarPorEquipoIdSinEquipoJugadorAsociadosDevuelveListaVacia() {
        // preparación
        Equipo equipo = new Equipo();
        this.sessionFactory.getCurrentSession().save(equipo);

        // ejecución
        List<EquipoJugador> equipoJugadores = repositorioEquipoJugador.buscarPorEquipoId(equipo.getId());

        // Verificación
        assertThat(equipoJugadores, empty());
    }

    @Test
    @Transactional
    @Rollback
    public void eliminarEquipoJugadorEliminaElRegistroDeLaBdd() {
        // preparación
        Equipo equipo = new Equipo();
        Jugador jugador = new Jugador();
        jugador.setDni(38662);
        this.sessionFactory.getCurrentSession().save(equipo);
        this.sessionFactory.getCurrentSession().save(jugador);

        EquipoJugador equipoJugador = new EquipoJugador();
        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(jugador);
        this.sessionFactory.getCurrentSession().save(equipoJugador);

        Long idEquipoJugador = equipoJugador.getId();

        // ejecución
        repositorioEquipoJugador.eliminarEquipoJugador(equipoJugador);
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        EquipoJugador equipoJugadorEliminado = this.sessionFactory.getCurrentSession().get(EquipoJugador.class, idEquipoJugador);

        // Verificación
        assertNull(equipoJugadorEliminado);
    }

    @Test
    @Transactional
    @Rollback
    public void actualizarEquipoJugadorActualizaElJugadorAsociado() {
        // preparación
        Equipo equipo = new Equipo();
        Jugador jugadorOriginal = new Jugador();
        jugadorOriginal.setDni(38662);
        Jugador jugadorNuevo = new Jugador();
        jugadorNuevo.setDni(40123);

        this.sessionFactory.getCurrentSession().save(equipo);
        this.sessionFactory.getCurrentSession().save(jugadorOriginal);
        this.sessionFactory.getCurrentSession().save(jugadorNuevo);

        EquipoJugador equipoJugador = new EquipoJugador();
        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(jugadorOriginal);
        this.sessionFactory.getCurrentSession().save(equipoJugador);

        equipoJugador.setJugador(jugadorNuevo);

        // ejecución
        repositorioEquipoJugador.actualizarEquipoJugador(equipoJugador);
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        EquipoJugador equipoJugadorActualizado = this.sessionFactory.getCurrentSession().get(EquipoJugador.class, equipoJugador.getId());

        // Verificación
        assertThat(equipoJugadorActualizado.getJugador().getId(), equalTo(jugadorNuevo.getId()));
    }

}