package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.TipoTorneo;
import com.tallerwebi.dominio.Torneo;
import com.tallerwebi.dominio.equipo.Equipo;
import com.tallerwebi.dominio.equipo.RepositorioEquipo;
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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioEquipoTest {

    @Autowired
    private RepositorioEquipo repositorioEquipo;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void sePuedeGuardarUnEquipoEnLaBdd() {
        // preparación
        Equipo equipo = new Equipo();

        // ejecución
        repositorioEquipo.guardarEquipo(equipo);

        // Verificación
        assertThat(equipo.getId(), notNullValue());
    }


    @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorNombreDevuelveUnEquipo() {
        // preparación
        Equipo equipo1 = new Equipo();
        equipo1.setNombreEquipo("CABJ12");
        Equipo equipo2 = new Equipo();
        equipo2.setNombreEquipo("PLM2026");
        Equipo equipo3 = new Equipo();
        equipo3.setNombreEquipo("QueremosLaCopa");

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecución
        Equipo equipoBuscado = repositorioEquipo.buscarEquipoPorNombre("PLM2026");

        // verificación
        assertThat(equipoBuscado, notNullValue());
        assertThat(equipoBuscado.getNombreEquipo(), equalToIgnoringCase("PLM2026"));
        assertThat(equipoBuscado.getId(), equalTo(equipo2.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorIdDevuelveUnEquipo() {
        // preparación

        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();
        Equipo equipo3 = new Equipo();

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecución
        Equipo equipoBuscado = repositorioEquipo.buscarEquipoPorId(equipo3.getId());

        assertThat(equipoBuscado, notNullValue());
        assertThat(equipoBuscado.getId(), equalTo(equipo3.getId()));
    }

  /*  @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorIdInexistenteDevuelveNull() {
        // preparación
        when(sessionMock.get(Equipo.class, 1L)).thenReturn(null);

        // ejecución
        Equipo equipoObtenido = repositorioEquipo.buscarEquipoPorId(1L);

        // verification
        assertThat(equipoObtenido, equalTo(null));
    }*/

    @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorNombreInexistenteDevuelveNull() {
        // preparación
        Equipo equipo1 = new Equipo();
        equipo1.setNombreEquipo("CABJ12");
        Equipo equipo2 = new Equipo();
        equipo2.setNombreEquipo("PLM2026");
        Equipo equipo3 = new Equipo();
        equipo3.setNombreEquipo("QueremosLaCopa");

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecución
        Equipo equipoBuscado = repositorioEquipo.buscarEquipoPorNombre("KeLocura");

        // verificación
        assertNull(equipoBuscado);
    }

    @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorIDInexistenteDevuelveNull() {
        // preparación
        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();
        Equipo equipo3 = new Equipo();

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecución
        Equipo equipoBuscado = repositorioEquipo.buscarEquipoPorId(2323L);

        // verificación
        assertNull(equipoBuscado);
    }

    @Test
    @Transactional
    @Rollback
    public void existeEquipoEnTorneoDevuelveTrueSiHayEquiposAsociados() {
        // preparación
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo 2026");
        torneo.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusDays(30));
        this.sessionFactory.getCurrentSession().save(torneo);

        Equipo equipo = new Equipo();
        equipo.setTorneo(torneo);
        this.sessionFactory.getCurrentSession().save(equipo);

        // ejecución
        boolean existeEquipo = repositorioEquipo.existeEquipoEnTorneo(torneo.getId());

        // verificación
        assertThat(existeEquipo, equalTo(true));
    }

    @Test
    @Transactional
    @Rollback
    public void existeEquipoEnTorneoDevuelveFalseSiNoHayEquiposAsociados() {
        // preparación
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo 2026");
        torneo.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusDays(30));
        this.sessionFactory.getCurrentSession().save(torneo);

        // ejecución
        boolean existeEquipo = repositorioEquipo.existeEquipoEnTorneo(torneo.getId());

        // verificación
        assertThat(existeEquipo, equalTo(false));
    }

    @Test
    @Transactional
    @Rollback
    public void actualizarEquipoActualizaLosDatosDelEquipo() {
        // preparación
        Equipo equipo = new Equipo();
        equipo.setNombreEquipo("CABJ12");
        this.sessionFactory.getCurrentSession().save(equipo);

        equipo.setNombreEquipo("CABJ2026");

        // ejecución
        repositorioEquipo.actualizarEquipo(equipo);
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        Equipo equipoActualizado = (Equipo) this.sessionFactory.getCurrentSession().get(Equipo.class, equipo.getId());

        // verificación
        assertThat(equipoActualizado.getNombreEquipo(), equalTo("CABJ2026"));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarEquiposPorTorneoDevuelveLosEquiposDeEseTorneo() {
        // preparación
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo 2026");
        torneo.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusDays(30));
        this.sessionFactory.getCurrentSession().save(torneo);

        Equipo equipo1 = new Equipo();
        equipo1.setTorneo(torneo);
        Equipo equipo2 = new Equipo();
        equipo2.setTorneo(torneo);
        Equipo equipo3 = new Equipo();

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecución
        List<Equipo> equiposDelTorneo = repositorioEquipo.buscarEquiposPorTorneo(torneo.getId());

        // verificación
        assertThat(equiposDelTorneo, hasSize(2));
        assertThat(equiposDelTorneo, containsInAnyOrder(equipo1, equipo2));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarEquiposPorTorneoSinEquiposAsociadosDevuelveListaVacia() {
        // preparación
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo 2026");
        torneo.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusDays(30));
        this.sessionFactory.getCurrentSession().save(torneo);

        // ejecución
        List<Equipo> equiposDelTorneo = repositorioEquipo.buscarEquiposPorTorneo(torneo.getId());

        // verificación
        assertThat(equiposDelTorneo, empty());
    }

}