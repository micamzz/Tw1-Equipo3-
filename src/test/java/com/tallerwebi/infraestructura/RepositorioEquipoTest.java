package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.RepositorioEquipo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class RepositorioEquipoTest {

    private RepositorioEquipo repositorioEquipo;
    private SessionFactory sessionFactoryMock;
    private Session sessionMock;

    /*
    1- Al guardar equipo llama al metodo save que hace que se guarde en la bdd
    2- Al buscar un equipo por Id en la bdd lo devuelve
    3- Al buscar un equipo por Id que no existe en la bdd devuelve null
     */
    /*Inicializacion de mocks para testar base de datos*/
    @BeforeEach
    public void inicializacion() {
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);

        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        repositorioEquipo = new RepositorioEquipoImpl(sessionFactoryMock);
    }

    @Test
    public void guardarEquipoDeberiaLlamarAlMetodoSaveDeLaSesion() {
        // preparacion
        Equipo equipo = new Equipo();

        // ejecucion
        repositorioEquipo.guardarEquipo(equipo);

        // ejecuion
        verify(sessionMock, times(1)).save(equipo);
    }

    @Test
    public void buscarEquipoPorIdDevuelveUnEquipo() {
        // preparacion
        Equipo equipoEsperado = new Equipo();
        when(sessionMock.get(Equipo.class, 1L)).thenReturn(equipoEsperado);

        // ejecucion
        Equipo equipoObtenido = repositorioEquipo.buscarEquipoPorId(1L);

        // verificacion
        assertThat(equipoObtenido, equalTo(equipoEsperado));
    }

    @Test
    public void buscarEquipoPorIdInexistenteDevuelveNull() {
        // preparacion
        when(sessionMock.get(Equipo.class, 1L)).thenReturn(null);

        // ejecucion
        Equipo equipoObtenido = repositorioEquipo.buscarEquipoPorId(1L);

        // verficacion
        assertThat(equipoObtenido, equalTo(null));
    }

}




