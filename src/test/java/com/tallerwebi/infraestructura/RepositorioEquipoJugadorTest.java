package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.equipo.Equipo;
import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
import com.tallerwebi.dominio.Jugador;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioEquipoJugadorTest {

    @Autowired
    private RepositorioEquipoJugador repositorioEquipoJugador;

    @Autowired
    private SessionFactory sessionFactory;

    /*
     * 1- Se puede guardar un EquipoJugador en la BDD
     * 2- Al buscar un EquipoJugador se obtiene resultado exitoso. */
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


}
