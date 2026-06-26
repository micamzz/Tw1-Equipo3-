package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipo.RepositorioEquipo;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioFormacionTest {
    @Test
    public void queObtenerRolJugadorEnFormacionDevuelvaLocal() {
        RepositorioFormacion repositorioFormacion = mock(RepositorioFormacion.class);
        RepositorioJugador repositorioJugadpr = mock(RepositorioJugador.class);
        RepositorioEquipoNBA repositorioEquipo = mock(RepositorioEquipoNBA.class);
        RepositorioPartidoNBA repositorioPartido = mock(RepositorioPartidoNBA.class);

        EquipoNBA equipoLocal = new EquipoNBA();
        equipoLocal.setId(1L);

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(equipoLocal);

        FormacionPartido formacion = new FormacionPartido();
        formacion.setEquipo(equipoLocal);

        when(repositorioPartido.buscarPorId(1L)).thenReturn(partido);
        when(repositorioFormacion.buscarPorPartidoYJugador(1L, 10L))
                .thenReturn(formacion);

        ServicioFormacion servicio = new ServicioFormacionImpl(
                repositorioFormacion,
                repositorioJugadpr,
                repositorioEquipo,
                repositorioPartido
        );

        EquipoRol resultado = servicio.obtenerRolJugadorEnFormacion(1L, 10L);
        assertThat(resultado, equalTo(EquipoRol.LOCAL));
    }
    @Test
    public void queObtenerRolJugadorEnFormacionDevuelvaVisitante() {
        RepositorioFormacion repositorioFormacion = mock(RepositorioFormacion.class);
        RepositorioJugador repositorioJugadpr = mock(RepositorioJugador.class);
        RepositorioEquipoNBA repositorioEquipo = mock(RepositorioEquipoNBA.class);
        RepositorioPartidoNBA repositorioPartido = mock(RepositorioPartidoNBA.class);

        EquipoNBA equipoLocal = new EquipoNBA();
        equipoLocal.setId(1L);

        EquipoNBA equipoVisitante = new EquipoNBA();
        equipoVisitante.setId(2L);

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(equipoLocal);

        FormacionPartido formacion = new FormacionPartido();
        formacion.setEquipo(equipoVisitante);

        when(repositorioPartido.buscarPorId(1L)).thenReturn(partido);
        when(repositorioFormacion.buscarPorPartidoYJugador(1L, 10L))
                .thenReturn(formacion);

        ServicioFormacion servicio = new ServicioFormacionImpl(
                repositorioFormacion,
                repositorioJugadpr,
                repositorioEquipo,
                repositorioPartido
        );

        EquipoRol resultado = servicio.obtenerRolJugadorEnFormacion(1L, 10L);
        assertThat(resultado, equalTo(EquipoRol.VISITANTE));
    }

    @Test
    public void queJugadorYaEstaEnFormacionDevuelvaVerdaderoCuandoExiste() {
        RepositorioFormacion repositorioFormacionMock = mock(RepositorioFormacion.class);
        RepositorioJugador repositorioJugadorMock = mock(RepositorioJugador.class);
        RepositorioEquipoNBA repositorioEquipoMock = mock(RepositorioEquipoNBA.class);
        RepositorioPartidoNBA repositorioPartidoMock = mock(RepositorioPartidoNBA.class);

        when(repositorioFormacionMock.jugadorYaEstaEnFormacion(1L, 10L))
                .thenReturn(true);

        ServicioFormacion servicio = new ServicioFormacionImpl(
                repositorioFormacionMock,
                repositorioJugadorMock,
                repositorioEquipoMock,
                repositorioPartidoMock
        );

        Boolean resultado = servicio.jugadorYaEstasEnFormacion(1L, 10L);

        assertThat(resultado, equalTo(true));
    }

    @Test
    public void queJugadorYaEstaEnFormacionDevuelvaFalseCuandoNoExiste() {
        RepositorioFormacion repositorioFormacionMock = mock(RepositorioFormacion.class);
        RepositorioJugador repositorioJugadorMock = mock(RepositorioJugador.class);
        RepositorioEquipoNBA repositorioEquipoMock = mock(RepositorioEquipoNBA.class);
        RepositorioPartidoNBA repositorioPartidoMock = mock(RepositorioPartidoNBA.class);

        when(repositorioFormacionMock.jugadorYaEstaEnFormacion(1L, 10L))
                .thenReturn(false);

        ServicioFormacion servicio = new ServicioFormacionImpl(
                repositorioFormacionMock,
                repositorioJugadorMock,
                repositorioEquipoMock,
                repositorioPartidoMock
        );

        Boolean resultado = servicio.jugadorYaEstasEnFormacion(1L, 10L);

        assertThat(resultado, equalTo(false));
    }
}
