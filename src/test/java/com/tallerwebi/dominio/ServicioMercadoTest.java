package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioMercadoTest {
    @Test
    public void queBuscarPorNombreDevuelvaJugadoresQueCoincidan() {
        RepositorioJugador repositorioMock= mock(RepositorioJugador.class);

        Jugador jugador = new Jugador();
        jugador.setNombre("Stephen");
        jugador.setApellido("Curry");
        when(repositorioMock.buscarJugadores(null,"Stephen")).thenReturn(Arrays.asList(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock);

        List<Jugador> resultado = servicio.obtenerJugadores(null, "Stephen");
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getNombre(),equalTo("Stephen"));

    }
    @Test
    public void queBuscarAleroDeveulvaJugadoresConPosicionAlero(){
        RepositorioJugador repositorioMock= mock(RepositorioJugador.class);
        Jugador jugador = new Jugador();
        jugador.setNombre("LeBron");
        jugador.setApellido("James");
        jugador.setPosicion(Posicion.ALERO);
        when(repositorioMock.buscarJugadores(Posicion.ALERO,null)).thenReturn(Arrays.asList(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock);

        List<Jugador> resultado = servicio.buscarAlero();
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getPosicion(),equalTo(Posicion.ALERO));
    }

    @Test
    public void queBuscarPivotDevuelvaJugadoresConPosicionPivot(){
        RepositorioJugador repositorioMock= mock(RepositorioJugador.class);
        Jugador jugador = new Jugador();
        jugador.setNombre("Nikola");
        jugador.setApellido("Jokic");
        jugador.setPosicion(Posicion.PIVOT);
        when(repositorioMock.buscarJugadores(Posicion.PIVOT,null)).thenReturn(Arrays.asList(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock);

        List<Jugador> resultado = servicio.buscarPivot();
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getPosicion(),equalTo(Posicion.PIVOT));
    }

    @Test
    public void queBuscarBaseDevuevlaJugadoresConPosicionBase(){
        RepositorioJugador repositorioMock= mock(RepositorioJugador.class);
        Jugador jugador = new Jugador();
        jugador.setNombre("Stephen");
        jugador.setApellido("Curry");
        jugador.setPosicion(Posicion.BASE);
        when(repositorioMock.buscarJugadores(Posicion.BASE,null)).thenReturn(Arrays.asList(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock);

        List<Jugador> resultado = servicio.buscarBase();
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getPosicion(),equalTo(Posicion.BASE));
    }
}
