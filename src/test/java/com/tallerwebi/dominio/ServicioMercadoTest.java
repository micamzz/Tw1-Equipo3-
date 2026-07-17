package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.Posicion;
import com.tallerwebi.dominio.eventoPartido.RepositorioEventoPartido;
import com.tallerwebi.dominio.jugador.Jugador;
import com.tallerwebi.dominio.jugador.RendimientoJugador;
import com.tallerwebi.dominio.jugador.RepositorioJugador;
import com.tallerwebi.dominio.mercado.ServicioMercado;
import com.tallerwebi.dominio.mercado.ServicioMercadoImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioMercadoTest {
    @Test
    public void queBuscarPorNombreDevuelvaJugadoresQueCoincidan() {
        RepositorioJugador repositorioMock = mock(RepositorioJugador.class);

        Jugador jugador = new Jugador();
        jugador.setNombre("Stephen");
        jugador.setApellido("Curry");
        when(repositorioMock.buscarJugadores(null, "Stephen")).thenReturn(List.of(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock, mock(RepositorioEventoPartido.class));

        List<Jugador> resultado = servicio.obtenerJugadores(null, "Stephen");
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getNombre(), equalTo("Stephen"));

    }

    @Test
    public void queBuscarAleroDeveulvaJugadoresConPosicionAlero() {
        RepositorioJugador repositorioMock = mock(RepositorioJugador.class);
        Jugador jugador = new Jugador();
        jugador.setNombre("LeBron");
        jugador.setApellido("James");
        jugador.setPosicion(Posicion.ALERO);
        when(repositorioMock.buscarJugadores(Posicion.ALERO, null)).thenReturn(List.of(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock, mock(RepositorioEventoPartido.class));

        List<Jugador> resultado = servicio.buscarAlero();
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getPosicion(), equalTo(Posicion.ALERO));
    }

    @Test
    public void queBuscarPivotDevuelvaJugadoresConPosicionPivot() {
        RepositorioJugador repositorioMock = mock(RepositorioJugador.class);
        Jugador jugador = new Jugador();
        jugador.setNombre("Nikola");
        jugador.setApellido("Jokic");
        jugador.setPosicion(Posicion.PIVOT);
        when(repositorioMock.buscarJugadores(Posicion.PIVOT, null)).thenReturn(List.of(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock, mock(RepositorioEventoPartido.class));

        List<Jugador> resultado = servicio.buscarPivot();
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getPosicion(), equalTo(Posicion.PIVOT));
    }

    @Test
    public void queBuscarBaseDevuevlaJugadoresConPosicionBase() {
        RepositorioJugador repositorioMock = mock(RepositorioJugador.class);
        Jugador jugador = new Jugador();
        jugador.setNombre("Stephen");
        jugador.setApellido("Curry");
        jugador.setPosicion(Posicion.BASE);
        when(repositorioMock.buscarJugadores(Posicion.BASE, null)).thenReturn(List.of(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock, mock(RepositorioEventoPartido.class));

        List<Jugador> resultado = servicio.buscarBase();
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getPosicion(), equalTo(Posicion.BASE));
    }

    @Test
    public void calcularPuntajeJugadorCorrectamente() {
        RendimientoJugador rendimiento = new RendimientoJugador();
        rendimiento.setPuntos(28);
        rendimiento.setRebotes(8);
        rendimiento.setAsistencias(9);
        rendimiento.setRobos(1);
        rendimiento.setBloqueos(0);
        rendimiento.setPerdidas(4);

        ServicioMercado servicio = new ServicioMercadoImpl(mock(RepositorioJugador.class), mock(RepositorioEventoPartido.class));
        double puntaje = servicio.calcularPuntajeJugador(rendimiento);

        assertThat(puntaje, equalTo(46.1));
    }

    @Test
    public void queBuscarJugadorPorIdDevuelvaJugadorCorrecto() {
        RepositorioJugador repositorioMock = mock(RepositorioJugador.class);
        Jugador jugador = new Jugador();
        jugador.setNombre("Luka");
        jugador.setApellido("Doncic");
        when(repositorioMock.buscarJugadorPorId(1L)).thenReturn(jugador);

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock, mock(RepositorioEventoPartido.class));
        Jugador resultado = servicio.buscarJugadorPorId(1L);

        assertThat(resultado.getNombre(), equalTo("Luka"));
    }
}
