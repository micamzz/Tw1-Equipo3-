package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.RepositorioJugador;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class RepositorioJugadorTest {
    private RepositorioJugador repositorioJugador;
    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private Criteria criteriaMock;

    @BeforeEach
    public void init() {

        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        criteriaMock = mock(Criteria.class);

        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        when(sessionMock.createCriteria(Jugador.class)).thenReturn(criteriaMock);
        when(criteriaMock.add((any(Criterion.class)))).thenReturn(criteriaMock);

        repositorioJugador = new RepositorioJugadorImpl(sessionFactoryMock);
    }

    @Test
    public void queBuscarJugadoresPorNombreDevuelvaResultados() {
        Jugador jugador = new Jugador();
        jugador.setNombre("Stephen Curry");
        when(criteriaMock.list()).thenReturn(List.of(jugador));

        List<Jugador> resultado = repositorioJugador.buscarJugadores(null, "Curry");

        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getNombre(), equalTo("Stephen Curry"));
    }

    @Test
    public void queBuscarJugadoresPorPosicionDevuelvaResultados() {
        Jugador jugador = new Jugador();
        jugador.setPosicion(Posicion.BASE);
        when(criteriaMock.list()).thenReturn(List.of(jugador));

        List<Jugador> resultado = repositorioJugador.buscarJugadores(Posicion.BASE, null);

        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getPosicion(), equalTo(Posicion.BASE));
    }


    @Test
    public void queBuscarJugadoresSinFlitrosDevuelvaTodos() {
        when(criteriaMock.list()).thenReturn(Arrays.asList(new Jugador(), new Jugador()));

        List<Jugador> resultado = repositorioJugador.buscarJugadores(null, null);
        assertThat(resultado, hasSize(2));
    }

    @Test
    public void queBuscarJugadorPorIdDevuelvaElJugadorCorrecto() {
        Jugador jugador = new Jugador();
        jugador.setId(80L);
        jugador.setNombre("Stephen Curry");

        when(criteriaMock.uniqueResult()).thenReturn(jugador);

        Jugador resultado = repositorioJugador.buscarJugadorPorId(80L);

        assertThat(resultado, notNullValue());
        assertThat(resultado.getId(), equalTo(80L));
        assertThat(resultado.getNombre(), equalTo("Stephen Curry"));
    }

    /* hacer test del nuevo metodo buscarjugadoresporPosicion*/

}
