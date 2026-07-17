package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.EquipoRol;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.formacion.FormacionPartido;
import com.tallerwebi.dominio.formacion.RepositorioFormacion;
import com.tallerwebi.dominio.formacion.ServicioFormacion;
import com.tallerwebi.dominio.formacion.ServicioFormacionImpl;
import com.tallerwebi.dominio.jugador.Jugador;
import com.tallerwebi.dominio.jugador.RepositorioJugador;
import com.tallerwebi.dominio.partidoNBA.PartidoNBA;
import com.tallerwebi.dominio.partidoNBA.RepositorioPartidoNBA;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
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

    //partidoTieneJugadoresEnFormacion
    private PartidoNBA unPartidoConEquipos() {
        EquipoNBA local = new EquipoNBA();
        local.setId(1L);
        EquipoNBA visitante = new EquipoNBA();
        visitante.setId(2L);

        PartidoNBA p = new PartidoNBA();
        p.setEquipoLocal(local);
        p.setEquipoVisitante(visitante);
        return p;
    }

    private List<FormacionPartido> unaFormacionDe(int cantidad) {
        List<FormacionPartido> lista = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            lista.add(unaFormacion());
        }
        return lista;
    }

    private FormacionPartido unaFormacion() {
        FormacionPartido f = new FormacionPartido();
        f.setJugador(new Jugador());
        f.setEquipo(new EquipoNBA());
        f.setPartido(new PartidoNBA());
        return f;
    }

    @Test
    public void dadoQueAmbosEquiposTienen5JugadoresElPartidoPuedeIniciar() {
        RepositorioFormacion repositorioFormacion = mock(RepositorioFormacion.class);
        RepositorioJugador repositorioJugador = mock(RepositorioJugador.class);
        RepositorioEquipoNBA repositorioEquipo = mock(RepositorioEquipoNBA.class);
        RepositorioPartidoNBA repositorioPartido = mock(RepositorioPartidoNBA.class);

        ServicioFormacion servicio = new ServicioFormacionImpl(
                repositorioFormacion,
                repositorioJugador,
                repositorioEquipo,
                repositorioPartido
        );

        when(repositorioPartido.buscarPorId(1L)).thenReturn(unPartidoConEquipos());
        when(repositorioFormacion.buscarPorPartidoYEquipo(1L, 1L)).thenReturn(unaFormacionDe(5));
        when(repositorioFormacion.buscarPorPartidoYEquipo(1L, 2L)).thenReturn(unaFormacionDe(5));

        boolean resultado = servicio.partidoTieneJugadoresEnFormacion(1L);

        assertThat(resultado, is(true));
    }

    @Test
    public void dadoQueAmbosEquiposTienenMenosDe5JugadoresElPartidoNOPuedeIniciar() {
        RepositorioFormacion repositorioFormacion = mock(RepositorioFormacion.class);
        RepositorioJugador repositorioJugador = mock(RepositorioJugador.class);
        RepositorioEquipoNBA repositorioEquipo = mock(RepositorioEquipoNBA.class);
        RepositorioPartidoNBA repositorioPartido = mock(RepositorioPartidoNBA.class);

        ServicioFormacion servicio = new ServicioFormacionImpl(
                repositorioFormacion,
                repositorioJugador,
                repositorioEquipo,
                repositorioPartido
        );

        when(repositorioPartido.buscarPorId(1L)).thenReturn(unPartidoConEquipos());
        when(repositorioFormacion.buscarPorPartidoYEquipo(1L, 1L)).thenReturn(unaFormacionDe(4));
        when(repositorioFormacion.buscarPorPartidoYEquipo(1L, 2L)).thenReturn(unaFormacionDe(5));

        boolean resultado = servicio.partidoTieneJugadoresEnFormacion(1L);

        assertThat(resultado, is(false));
    }

    @Test
    public void dadoQueNingunoDeLosEquiposTienenAlMenos5JugadoresElPartidoNOPuedeIniciar() {
        RepositorioFormacion repositorioFormacion = mock(RepositorioFormacion.class);
        RepositorioJugador repositorioJugador = mock(RepositorioJugador.class);
        RepositorioEquipoNBA repositorioEquipo = mock(RepositorioEquipoNBA.class);
        RepositorioPartidoNBA repositorioPartido = mock(RepositorioPartidoNBA.class);

        ServicioFormacion servicio = new ServicioFormacionImpl(
                repositorioFormacion,
                repositorioJugador,
                repositorioEquipo,
                repositorioPartido
        );

        when(repositorioPartido.buscarPorId(1L)).thenReturn(unPartidoConEquipos());
        when(repositorioFormacion.buscarPorPartidoYEquipo(1L, 1L)).thenReturn(unaFormacionDe(4));
        when(repositorioFormacion.buscarPorPartidoYEquipo(1L, 2L)).thenReturn(unaFormacionDe(3));

        boolean resultado = servicio.partidoTieneJugadoresEnFormacion(1L);

        assertThat(resultado, is(false));
    }
}
