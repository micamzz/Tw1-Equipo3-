package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.RepositorioEquipo;
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

    /*
    1- Se guarda un equipo en la BDD
    2- Al buscar un equipo por nombre obtiene un resultado exitoso
    3- Al buscar un equipo por Id  obtiene un resultado exitoso
    4- Al buscar un equipo por nombre inexistente devuelve un null
    5-  Al buscar un equipo por Id inexistente devuelve null
     */

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
        // preparacion
        Equipo equipo1 = new Equipo();
        equipo1.setNombreEquipo("CABJ12");
        Equipo equipo2 = new Equipo();
        equipo2.setNombreEquipo("PLM2026");
        Equipo equipo3 = new Equipo();
        equipo3.setNombreEquipo("QueremosLaCopa");

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecucion
        Equipo equipoBuscado = repositorioEquipo.buscarEquipoPorNombre("PLM2026");

        // verificacion
        assertThat(equipoBuscado, notNullValue());
        assertThat(equipoBuscado.getNombreEquipo(), equalToIgnoringCase("PLM2026"));
        assertThat(equipoBuscado.getId(), equalTo(equipo2.getId()));
    }

    @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorIdDevuelveUnEquipo() {
        // preparacion

        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();
        Equipo equipo3 = new Equipo();

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecucion
        Equipo equipoBuscado = repositorioEquipo.buscarEquipoPorId(equipo3.getId());

        assertThat(equipoBuscado, notNullValue());
        assertThat(equipoBuscado.getId(), equalTo(equipo3.getId()));
    }

  /*  @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorIdInexistenteDevuelveNull() {
        // preparacion
        when(sessionMock.get(Equipo.class, 1L)).thenReturn(null);

        // ejecucion
        Equipo equipoObtenido = repositorioEquipo.buscarEquipoPorId(1L);

        // verficacion
        assertThat(equipoObtenido, equalTo(null));
    }*/

    @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorNombreInexistenteDevuelveNull() {
        // preparacion
        Equipo equipo1 = new Equipo();
        equipo1.setNombreEquipo("CABJ12");
        Equipo equipo2 = new Equipo();
        equipo2.setNombreEquipo("PLM2026");
        Equipo equipo3 = new Equipo();
        equipo3.setNombreEquipo("QueremosLaCopa");

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecucion
        Equipo equipoBuscado = repositorioEquipo.buscarEquipoPorNombre("KeLocura");

        // verificacion
        assertNull(equipoBuscado);
    }

    @Test
    @Transactional
    @Rollback
    public void buscarEquipoPorIDInexistenteDevuelveNull() {
        // preparacion
        Equipo equipo1 = new Equipo();
        Equipo equipo2 = new Equipo();
        Equipo equipo3 = new Equipo();

        this.sessionFactory.getCurrentSession().save(equipo1);
        this.sessionFactory.getCurrentSession().save(equipo2);
        this.sessionFactory.getCurrentSession().save(equipo3);

        // ejecucion
        Equipo equipoBuscado = repositorioEquipo.buscarEquipoPorId(2323L);

        // verificacion
        assertNull(equipoBuscado);
    }
}




