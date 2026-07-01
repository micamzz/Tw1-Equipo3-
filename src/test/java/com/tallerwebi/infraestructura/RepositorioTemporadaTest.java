package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.temporada.RepositorioTemporada;
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
public class RepositorioTemporadaTest {

    @Autowired
    private RepositorioTemporada repositorioTemporada;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void sePuedeGuardarUnaTemporadaEnLaBdd() {
        // preparación
        Temporada temporada = new Temporada();
        temporada.setNombre("Temporada 2026");
        temporada.setAnio(2026);
        temporada.setFechaInicio(LocalDate.of(2026, 3, 1));
        temporada.setFechaFin(LocalDate.of(2026, 11, 30));

        // ejecución
        repositorioTemporada.guardar(temporada);

        // verificación
        assertThat(temporada.getId(), notNullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void actualizarTemporadaActualizaLosDatosEnLaBdd() {
        // preparación
        Temporada temporada = new Temporada();
        temporada.setNombre("Temporada 2026");
        temporada.setAnio(2026);
        temporada.setFechaInicio(LocalDate.of(2026, 3, 1));
        temporada.setFechaFin(null);

        sessionFactory.getCurrentSession().save(temporada);

        LocalDate nuevaFechaFin = LocalDate.of(2026, 11, 30);
        temporada.setFechaFin(nuevaFechaFin);

        // ejecución
        repositorioTemporada.actualizar(temporada);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        Temporada temporadaActualizada = sessionFactory.getCurrentSession().get(Temporada.class, temporada.getId());

        // verificación
        assertThat(temporadaActualizada.getFechaFin(), equalTo(nuevaFechaFin));
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTemporadaActualDevuelveLaTemporadaVigente() {
        // preparación
        Temporada temporadaVencida = new Temporada();
        temporadaVencida.setNombre("Temporada 2025");
        temporadaVencida.setAnio(2025);
        temporadaVencida.setFechaInicio(LocalDate.now().minusYears(1));
        temporadaVencida.setFechaFin(LocalDate.now().minusMonths(1));

        Temporada temporadaVigente = new Temporada();
        temporadaVigente.setNombre("Temporada 2026");
        temporadaVigente.setAnio(2026);
        temporadaVigente.setFechaInicio(LocalDate.now().minusMonths(1));
        temporadaVigente.setFechaFin(LocalDate.now().plusMonths(6));

        sessionFactory.getCurrentSession().save(temporadaVencida);
        sessionFactory.getCurrentSession().save(temporadaVigente);

        // ejecución
        Temporada temporadaObtenida = repositorioTemporada.obtenerTemporadaActual();

        // verificación
        assertThat(temporadaObtenida, notNullValue());
        assertThat(temporadaObtenida.getId(), equalTo(temporadaVigente.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTemporadaActualSiNoHayNingunaVigenteDevuelveNull() {
        // preparación
        Temporada temporadaVencida = new Temporada();
        temporadaVencida.setNombre("Temporada 2025");
        temporadaVencida.setAnio(2025);
        temporadaVencida.setFechaInicio(LocalDate.now().minusYears(1));
        temporadaVencida.setFechaFin(LocalDate.now().minusMonths(1));

        sessionFactory.getCurrentSession().save(temporadaVencida);

        // ejecución
        Temporada temporadaObtenida = repositorioTemporada.obtenerTemporadaActual();

        // verificación
        assertNull(temporadaObtenida);
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTodasLasTemporadasDevuelveLasTemporadasOrdenadasPorAnioDescendente() {
        // preparación
        Temporada temporada2024 = new Temporada();
        temporada2024.setNombre("Temporada 2024");
        temporada2024.setAnio(2024);
        temporada2024.setFechaInicio(LocalDate.of(2024, 3, 1));
        temporada2024.setFechaFin(LocalDate.of(2024, 11, 30));

        Temporada temporada2026 = new Temporada();
        temporada2026.setNombre("Temporada 2026");
        temporada2026.setAnio(2026);
        temporada2026.setFechaInicio(LocalDate.of(2026, 3, 1));
        temporada2026.setFechaFin(LocalDate.of(2026, 11, 30));

        Temporada temporada2025 = new Temporada();
        temporada2025.setNombre("Temporada 2025");
        temporada2025.setAnio(2025);
        temporada2025.setFechaInicio(LocalDate.of(2025, 3, 1));
        temporada2025.setFechaFin(LocalDate.of(2025, 11, 30));

        sessionFactory.getCurrentSession().save(temporada2024);
        sessionFactory.getCurrentSession().save(temporada2026);
        sessionFactory.getCurrentSession().save(temporada2025);

        // ejecución
        List<Temporada> temporadas = repositorioTemporada.obtenerTodasLasTemporadas();

        // verificación
        assertThat(temporadas.get(0).getAnio(), equalTo(2026));
        assertThat(temporadas.get(1).getAnio(), equalTo(2025));
        assertThat(temporadas.get(2).getAnio(), equalTo(2024));
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTemporadaPorIdDevuelveLaTemporadaBuscada() {
        // preparación
        Temporada temporada1 = new Temporada();
        temporada1.setNombre("Temporada 2025");
        temporada1.setAnio(2025);
        temporada1.setFechaInicio(LocalDate.of(2025, 3, 1));
        temporada1.setFechaFin(LocalDate.of(2025, 11, 30));

        Temporada temporada2 = new Temporada();
        temporada2.setNombre("Temporada 2026");
        temporada2.setAnio(2026);
        temporada2.setFechaInicio(LocalDate.of(2026, 3, 1));
        temporada2.setFechaFin(LocalDate.of(2026, 11, 30));

        sessionFactory.getCurrentSession().save(temporada1);
        sessionFactory.getCurrentSession().save(temporada2);

        // ejecución
        Temporada temporadaBuscada = repositorioTemporada.obtenerTemporadaPorId(temporada2.getId());

        // verificación
        assertThat(temporadaBuscada, notNullValue());
        assertThat(temporadaBuscada.getId(), equalTo(temporada2.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTemporadaPorIdInexistenteDevuelveNull() {
        // preparación
        Temporada temporada = new Temporada();
        temporada.setNombre("Temporada 2026");
        temporada.setAnio(2026);
        temporada.setFechaInicio(LocalDate.of(2026, 3, 1));
        temporada.setFechaFin(LocalDate.of(2026, 11, 30));

        sessionFactory.getCurrentSession().save(temporada);

        // ejecución
        Temporada temporadaBuscada = repositorioTemporada.obtenerTemporadaPorId(99999L);

        // verificación
        assertNull(temporadaBuscada);
    }
}