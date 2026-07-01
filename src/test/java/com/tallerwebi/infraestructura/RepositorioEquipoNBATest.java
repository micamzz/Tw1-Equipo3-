package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioEquipoNBATest {

    @Autowired
    private RepositorioEquipoNBA repositorioEquipoNBA;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void sePuedeGuardarUnEquipoNBAEnLaBdd() {

        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Lakers");

        // ejecución
        repositorioEquipoNBA.crearEquipo(equipo);

        // verificación
        assertThat(equipo.getId(), notNullValue());
    }

    @Test
    @Transactional
    @Rollback
    public void buscarEquipoNBAPorIdDevuelveUnEquipo() {

        // preparación
        EquipoNBA equipo1 = new EquipoNBA();
        equipo1.setNombre("Lakers");

        EquipoNBA equipo2 = new EquipoNBA();
        equipo2.setNombre("Bulls");

        EquipoNBA equipo3 = new EquipoNBA();
        equipo3.setNombre("Warriors");

        sessionFactory.getCurrentSession().save(equipo1);
        sessionFactory.getCurrentSession().save(equipo2);
        sessionFactory.getCurrentSession().save(equipo3);

        // ejecución
        EquipoNBA equipoBuscado = repositorioEquipoNBA.buscarEquipoPorId(equipo3.getId());

        // verificación
        assertThat(equipoBuscado, notNullValue());
        assertThat(equipoBuscado.getId(), equalTo(equipo3.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarEquipoNBAPorIdInexistenteDevuelveNull() {

        // preparación
        EquipoNBA equipo1 = new EquipoNBA();
        EquipoNBA equipo2 = new EquipoNBA();

        sessionFactory.getCurrentSession().save(equipo1);
        sessionFactory.getCurrentSession().save(equipo2);

        // ejecución
        EquipoNBA equipoBuscado = repositorioEquipoNBA.buscarEquipoPorId(999L);

        // verificación
        assertNull(equipoBuscado);
    }

    @Test
    @Transactional
    @Rollback
    public void obtenerTodosLosEquiposOrdenadosDevuelveLosEquiposOrdenadosPorNombre() {

        // preparación
        EquipoNBA equipo1 = new EquipoNBA();
        equipo1.setNombre("Warriors");

        EquipoNBA equipo2 = new EquipoNBA();
        equipo2.setNombre("Bulls");

        EquipoNBA equipo3 = new EquipoNBA();
        equipo3.setNombre("Lakers");

        sessionFactory.getCurrentSession().save(equipo1);
        sessionFactory.getCurrentSession().save(equipo2);
        sessionFactory.getCurrentSession().save(equipo3);

        // ejecución
        List<EquipoNBA> equipos = repositorioEquipoNBA.obtenerTodosLosEquiposOrdenados();

        // Verificación
        assertThat(equipos.get(0).getNombre(), equalTo("Bulls"));
        assertThat(equipos.get(1).getNombre(), equalTo("Lakers"));
        assertThat(equipos.get(2).getNombre(), equalTo("Warriors"));
    }

    @Test
    @Transactional
    @Rollback
    public void eliminarEquipoNBAEliminaElEquipoDeLaBaseDeDatos() {

        // preparación
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Lakers");

        sessionFactory.getCurrentSession().save(equipo);

        Long idEquipo = equipo.getId();

        // ejecución
        repositorioEquipoNBA.eliminar(equipo);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        EquipoNBA equipoEliminado = repositorioEquipoNBA.buscarEquipoPorId(idEquipo);

        // verificación
        assertNull(equipoEliminado);
    }

}

