package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.torneo.RepositorioTorneo;
import com.tallerwebi.dominio.enums.TipoTorneo;
import com.tallerwebi.dominio.torneo.Torneo;
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
public class RepositorioTorneoTest {

    @Autowired
    private RepositorioTorneo repositorioTorneo;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void sePuedeGuardarUnTorneoEnLaBdd() {
        // preparación
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo 2026");
        torneo.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));

        // ejecución
        repositorioTorneo.guardarTorneo(torneo);

        // verificación
        assertThat(torneo.getId(), notNullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void buscarTorneoPorIdDevuelveElTorneoBuscado() {
        // preparación
        Torneo torneo1 = new Torneo();
        torneo1.setNombreTorneo("Torneo Uno");
        torneo1.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo1.setFechaInicio(LocalDate.now());
        torneo1.setFechaFin(LocalDate.now().plusMonths(6));

        Torneo torneo2 = new Torneo();
        torneo2.setNombreTorneo("Torneo Dos");
        torneo2.setTipoTorneo(TipoTorneo.REAL);
        torneo2.setFechaInicio(LocalDate.now());
        torneo2.setFechaFin(LocalDate.now().plusMonths(6));

        sessionFactory.getCurrentSession().save(torneo1);
        sessionFactory.getCurrentSession().save(torneo2);

        // ejecución
        Torneo torneoBuscado = repositorioTorneo.buscarTorneoPorId(torneo2.getId());

        // verificación
        assertThat(torneoBuscado, notNullValue());
        assertThat(torneoBuscado.getId(), equalTo(torneo2.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarTorneoPorIdInexistenteDevuelveNull() {
        // preparación
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo 2026");
        torneo.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));

        sessionFactory.getCurrentSession().save(torneo);

        // ejecución
        Torneo torneoBuscado = repositorioTorneo.buscarTorneoPorId(99999L);

        // verificación
        assertNull(torneoBuscado);
    }

    @Test
    @Transactional
    @Rollback
    public void actualizarTorneoActualizaLosDatosEnLaBdd() {
        // preparación
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo Original");
        torneo.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));

        sessionFactory.getCurrentSession().save(torneo);

        torneo.setNombreTorneo("Torneo Actualizado");

        // ejecución
        repositorioTorneo.actualizarTorneo(torneo);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        Torneo torneoActualizado = sessionFactory.getCurrentSession().get(Torneo.class, torneo.getId());

        // verificación
        assertThat(torneoActualizado.getNombreTorneo(), equalTo("Torneo Actualizado"));
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTorneosPorTipoDevuelveLosTorneosDeEseTipo() {
        // preparación
        Torneo torneoVirtual1 = new Torneo();
        torneoVirtual1.setNombreTorneo("Torneo Virtual Uno");
        torneoVirtual1.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneoVirtual1.setFechaInicio(LocalDate.now());
        torneoVirtual1.setFechaFin(LocalDate.now().plusMonths(6));

        Torneo torneoVirtual2 = new Torneo();
        torneoVirtual2.setNombreTorneo("Torneo Virtual Dos");
        torneoVirtual2.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneoVirtual2.setFechaInicio(LocalDate.now().plusMonths(7));
        torneoVirtual2.setFechaFin(LocalDate.now().plusMonths(12));

        Torneo torneoReal = new Torneo();
        torneoReal.setNombreTorneo("Torneo Real");
        torneoReal.setTipoTorneo(TipoTorneo.REAL);
        torneoReal.setFechaInicio(LocalDate.now());
        torneoReal.setFechaFin(LocalDate.now().plusMonths(6));

        sessionFactory.getCurrentSession().save(torneoVirtual1);
        sessionFactory.getCurrentSession().save(torneoVirtual2);
        sessionFactory.getCurrentSession().save(torneoReal);

        // ejecución
        List<Torneo> torneosVirtuales = repositorioTorneo.obtenerTorneosPorTipo(TipoTorneo.VIRTUAL);

        // verificación
        assertThat(torneosVirtuales.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void eliminarTorneoEliminaElTorneoDeLaBdd() {
        // preparación
        Torneo torneo = new Torneo();
        torneo.setNombreTorneo("Torneo A Eliminar");
        torneo.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo.setFechaInicio(LocalDate.now());
        torneo.setFechaFin(LocalDate.now().plusMonths(6));

        sessionFactory.getCurrentSession().save(torneo);

        Long idTorneo = torneo.getId();

        // ejecución
        repositorioTorneo.eliminarTorneo(torneo);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        Torneo torneoEliminado = sessionFactory.getCurrentSession().get(Torneo.class, idTorneo);

        // verificación
        assertNull(torneoEliminado);
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTodosLosTorneosDevuelveTodosLosTorneosGuardados() {
        // preparación
        Torneo torneo1 = new Torneo();
        torneo1.setNombreTorneo("Torneo Uno");
        torneo1.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneo1.setFechaInicio(LocalDate.now());
        torneo1.setFechaFin(LocalDate.now().plusMonths(6));

        Torneo torneo2 = new Torneo();
        torneo2.setNombreTorneo("Torneo Dos");
        torneo2.setTipoTorneo(TipoTorneo.REAL);
        torneo2.setFechaInicio(LocalDate.now());
        torneo2.setFechaFin(LocalDate.now().plusMonths(6));

        sessionFactory.getCurrentSession().save(torneo1);
        sessionFactory.getCurrentSession().save(torneo2);

        // ejecución
        List<Torneo> torneos = repositorioTorneo.obtenerTodosLosTorneos();

        // verificación
        assertThat(torneos.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarTorneoActualDevuelveElTorneoVigenteDeEseTipo() {
        // preparación
        Torneo torneoVencido = new Torneo();
        torneoVencido.setNombreTorneo("Torneo Vencido");
        torneoVencido.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneoVencido.setFechaInicio(LocalDate.now().minusMonths(6));
        torneoVencido.setFechaFin(LocalDate.now().minusMonths(1));

        Torneo torneoVigente = new Torneo();
        torneoVigente.setNombreTorneo("Torneo Vigente");
        torneoVigente.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneoVigente.setFechaInicio(LocalDate.now().minusMonths(1));
        torneoVigente.setFechaFin(LocalDate.now().plusMonths(6));

        sessionFactory.getCurrentSession().save(torneoVencido);
        sessionFactory.getCurrentSession().save(torneoVigente);

        // ejecución
        Torneo torneoObtenido = repositorioTorneo.buscarTorneoActual(TipoTorneo.VIRTUAL);

        // verificación
        assertThat(torneoObtenido, notNullValue());
        assertThat(torneoObtenido.getId(), equalTo(torneoVigente.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarTorneoActualSiNoHayNingunoVigenteDevuelveNull() {
        // preparación
        Torneo torneoVencido = new Torneo();
        torneoVencido.setNombreTorneo("Torneo Vencido");
        torneoVencido.setTipoTorneo(TipoTorneo.VIRTUAL);
        torneoVencido.setFechaInicio(LocalDate.now().minusMonths(6));
        torneoVencido.setFechaFin(LocalDate.now().minusMonths(1));

        sessionFactory.getCurrentSession().save(torneoVencido);

        // ejecución
        Torneo torneoObtenido = repositorioTorneo.buscarTorneoActual(TipoTorneo.VIRTUAL);

        // verificación
        assertNull(torneoObtenido);
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTorneosPorTemporadaDevuelveLosTorneosDeEsaTemporada() {
        // preparación
        Temporada temporada = new Temporada();
        temporada.setNombre("Temporada 2026");
        temporada.setAnio(2026);
        temporada.setFechaInicio(LocalDate.of(2026, 3, 1));
        temporada.setFechaFin(LocalDate.of(2026, 11, 30));

        Temporada otraTemporada = new Temporada();
        otraTemporada.setNombre("Temporada 2025");
        otraTemporada.setAnio(2025);
        otraTemporada.setFechaInicio(LocalDate.of(2025, 3, 1));
        otraTemporada.setFechaFin(LocalDate.of(2025, 11, 30));

        sessionFactory.getCurrentSession().save(temporada);
        sessionFactory.getCurrentSession().save(otraTemporada);

        Torneo torneo1 = new Torneo();
        torneo1.setNombreTorneo("Torneo Uno");
        torneo1.setTipoTorneo(TipoTorneo.REAL);
        torneo1.setFechaInicio(LocalDate.of(2026, 3, 1));
        torneo1.setFechaFin(LocalDate.of(2026, 6, 30));
        torneo1.setTemporada(temporada);

        Torneo torneo2 = new Torneo();
        torneo2.setNombreTorneo("Torneo Dos");
        torneo2.setTipoTorneo(TipoTorneo.REAL);
        torneo2.setFechaInicio(LocalDate.of(2026, 7, 1));
        torneo2.setFechaFin(LocalDate.of(2026, 11, 30));
        torneo2.setTemporada(temporada);

        Torneo torneoDeOtraTemporada = new Torneo();
        torneoDeOtraTemporada.setNombreTorneo("Torneo Otra Temporada");
        torneoDeOtraTemporada.setTipoTorneo(TipoTorneo.REAL);
        torneoDeOtraTemporada.setFechaInicio(LocalDate.of(2025, 3, 1));
        torneoDeOtraTemporada.setFechaFin(LocalDate.of(2025, 11, 30));
        torneoDeOtraTemporada.setTemporada(otraTemporada);

        sessionFactory.getCurrentSession().save(torneo1);
        sessionFactory.getCurrentSession().save(torneo2);
        sessionFactory.getCurrentSession().save(torneoDeOtraTemporada);

        // ejecución
        List<Torneo> torneosDeLaTemporada = repositorioTorneo.obtenerTorneosPorTemporada(temporada.getId());

        // verificación
        assertThat(torneosDeLaTemporada.size(), equalTo(2));
    }
}