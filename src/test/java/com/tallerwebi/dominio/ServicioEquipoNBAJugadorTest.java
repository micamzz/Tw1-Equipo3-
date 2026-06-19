/*
package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBAJugador.EquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugadorImpl;
import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;
import com.tallerwebi.dominio.temporada.ServicioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioEquipoNBAJugadorTest {

    private RepositorioEquipoNBAJugador repositorioEquipoNBAJugadorMock;
    private RepositorioJugador repositorioJugadorMock;
    private ServicioTemporada servicioTemporadaMock;

    private ServicioEquipoNBAJugadorImpl servicio;

    @BeforeEach
    public void inicializacion() {

        repositorioEquipoNBAJugadorMock = mock(RepositorioEquipoNBAJugador.class);
        repositorioJugadorMock = mock(RepositorioJugador.class);
        servicioTemporadaMock = mock(ServicioTemporada.class);

        servicio = new ServicioEquipoNBAJugadorImpl(repositorioEquipoNBAJugadorMock, repositorioJugadorMock, servicioTemporadaMock);
    }

    @Test
    public void alObtenerLosJugadoresDeUnEquipoPorIdDevuelveElPlantelCorrectamente() throws TemporadaActualNoEncontradaException {

        Long idEquipo = 1L;

        Temporada temporada = new Temporada();
        temporada.setId(1L);

        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        EquipoNBAJugador asignacion1 = new EquipoNBAJugador();
        asignacion1.setJugador(jugador1);

        EquipoNBAJugador asignacion2 = new EquipoNBAJugador();
        asignacion2.setJugador(jugador2);

        List<EquipoNBAJugador> asignaciones = new ArrayList<>();
        asignaciones.add(asignacion1);
        asignaciones.add(asignacion2);

        when(servicioTemporadaMock.obtenerTemporadaActual()).thenReturn(temporada);

        when(repositorioEquipoNBAJugadorMock.buscarJugadoresDelEquipoNBAEnTemporada(idEquipo, temporada.getId())).thenReturn(asignaciones);

        List<Jugador> resultado = servicio.obtenerJugadoresDelEquipoPorId(idEquipo);

        assertThat(resultado.size(), equalTo(2));
        assertThat(resultado.get(0), equalTo(jugador1));
        assertThat(resultado.get(1), equalTo(jugador2));
    }

    @Test
    public void alObtenerLosJugadoresDeUnEquipoSinTemporadaActualLanzaUnaExcepcion() {

        when(servicioTemporadaMock.obtenerTemporadaActual()).thenThrow(new TemporadaActualNoEncontradaException("No hay temporada"));

        assertThrows(TemporadaActualNoEncontradaException.class, () -> servicio.obtenerJugadoresDelEquipoPorId(1L));
    }

    @Test
    public void alObtenerLosJugadoresDisponiblesDevuelveSoloLosJugadoresQueNoFueronAsignados() throws TemporadaActualNoEncontradaException {

        Temporada temporada = new Temporada();
        temporada.setId(1L);

        Jugador jugador1 = new Jugador();
        jugador1.setId(1L);

        Jugador jugador2 = new Jugador();
        jugador2.setId(2L);

        Jugador jugador3 = new Jugador();
        jugador3.setId(3L);

        List<Jugador> listadoJugadoresCompleto = new ArrayList<>();
        listadoJugadoresCompleto.add(jugador1);
        listadoJugadoresCompleto.add(jugador2);
        listadoJugadoresCompleto.add(jugador3);

        */
/* SE ASIGNA SOLO AL JUGADOR 2 A UN EQUIPO*//*

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setJugador(jugador2);

        List<EquipoNBAJugador> asignados = new ArrayList<>();
        asignados.add(asignacion);

        when(servicioTemporadaMock.obtenerTemporadaActual()).thenReturn(temporada);

        when(repositorioJugadorMock.buscarTodosLosJugadores()).thenReturn(listadoJugadoresCompleto);

        when(repositorioEquipoNBAJugadorMock.buscarAsignacionesPorTemporada(temporada.getId())).thenReturn(asignados);

        List<Jugador> listadoJugadoresDisponibles = servicio.obtenerJugadoresDisponibles();

        assertThat(listadoJugadoresDisponibles.size(), equalTo(2));
        assertThat(listadoJugadoresDisponibles.contains(jugador1), equalTo(true));
        assertThat(listadoJugadoresDisponibles.contains(jugador3), equalTo(true));
        assertFalse(listadoJugadoresDisponibles.contains(jugador2));
    }

    @Test
    public void alObtenerJugadoresFiltradosPorPosicionDevuelveSoloLosQueCoinciden() throws TemporadaActualNoEncontradaException {

        Temporada temporada = new Temporada();
        temporada.setId(1L);

        Jugador base = new Jugador();
        base.setId(1L);
        base.setPosicion(Posicion.BASE);

        Jugador pivot = new Jugador();
        pivot.setId(2L);
        pivot.setPosicion(Posicion.PIVOT);

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(base);
        jugadores.add(pivot);

        when(servicioTemporadaMock.obtenerTemporadaActual()).thenReturn(temporada);
        when(repositorioJugadorMock.buscarTodosLosJugadores()).thenReturn(jugadores);
        when(repositorioEquipoNBAJugadorMock.buscarAsignacionesPorTemporada(temporada.getId())).thenReturn(new ArrayList<>());

        List<Jugador> resultado = servicio.obtenerJugadoresFiltrados(Posicion.BASE, null);

        assertThat(resultado.size(), equalTo(1));
        assertThat(resultado.get(0), equalTo(base));
    }

    @Test
    public void alObtenerJugadoresFiltradosPorNombreDevuelveSoloLosQueCoinciden() throws TemporadaActualNoEncontradaException {

        Temporada temporada = new Temporada();
        temporada.setId(1L);

        Jugador jordan = new Jugador();
        jordan.setId(1L);
        jordan.setNombre("Michael");
        jordan.setApellido("Jordan");

        Jugador jugador2 = new Jugador();
        jugador2.setId(2L);
        jugador2.setNombre("Kobe");
        jugador2.setApellido("Bryant");

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jordan);
        jugadores.add(jugador2);

        when(servicioTemporadaMock.obtenerTemporadaActual()).thenReturn(temporada);
        when(repositorioJugadorMock.buscarTodosLosJugadores()).thenReturn(jugadores);
        when(repositorioEquipoNBAJugadorMock.buscarAsignacionesPorTemporada(temporada.getId())).thenReturn(new ArrayList<>());

        List<Jugador> jugadorBuscado = servicio.obtenerJugadoresFiltrados(null, "Jordan");

        assertThat(jugadorBuscado.size(), equalTo(1));
        assertThat(jugadorBuscado.get(0), equalTo(jordan));
    }

    @Test
    public void alObtenerLosJugadoresDeUnEquipoEnUnaTemporadaDevuelveLosJugadoresAsignados() {

        Long idEquipo = 1L;
        Long idTemporada = 1L;

        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        EquipoNBAJugador asignacion1 = new EquipoNBAJugador();
        asignacion1.setJugador(jugador1);

        EquipoNBAJugador asignacion2 = new EquipoNBAJugador();
        asignacion2.setJugador(jugador2);

        List<EquipoNBAJugador> asignaciones = new ArrayList<>();
        asignaciones.add(asignacion1);
        asignaciones.add(asignacion2);

        when(repositorioEquipoNBAJugadorMock.buscarJugadoresDelEquipoNBAEnTemporada(idEquipo, idTemporada)).thenReturn(asignaciones);

        List<Jugador> resultado = servicio./obtenerJugadoresDelEquipoEnTemporada(idEquipo, idTemporada);

        assertThat(resultado.size(), equalTo(2));
        assertThat(resultado.get(0), equalTo(jugador1));
        assertThat(resultado.get(1), equalTo(jugador2));
    }

    @Test
    public void alObtenerLosJugadoresDisponiblesSinTemporadaActualLanzaUnaExcepcion() {

        when(servicioTemporadaMock.obtenerTemporadaActual()).thenThrow(new TemporadaActualNoEncontradaException("No hay temporada"));

        assertThrows(TemporadaActualNoEncontradaException.class, () -> servicio.obtenerJugadoresDisponibles()
        );
    }
}*/
