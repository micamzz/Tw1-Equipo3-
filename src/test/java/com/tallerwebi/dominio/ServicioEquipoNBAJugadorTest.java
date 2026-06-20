package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.EquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugadorImpl;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioEquipoNBAJugadorTest {

    private RepositorioEquipoNBAJugador repositorioEquipoNBAJugadorMock;
    private RepositorioEquipoNBA repositorioEquipoNBAMock;
    private RepositorioJugador repositorioJugadorMock;
    private ServicioTorneo servicioTorneoMock;
    private ServicioEquipoNBAJugadorImpl servicio;

    @BeforeEach
    public void inicializacion() {
        repositorioEquipoNBAJugadorMock = mock(RepositorioEquipoNBAJugador.class);
        repositorioJugadorMock = mock(RepositorioJugador.class);
        servicioTorneoMock = mock(ServicioTorneo.class);
        repositorioEquipoNBAMock = mock(RepositorioEquipoNBA.class);
        servicio = new ServicioEquipoNBAJugadorImpl(repositorioEquipoNBAJugadorMock, repositorioEquipoNBAMock, repositorioJugadorMock, servicioTorneoMock);
    }

    @Test
    public void cuandoAgregoUnJugadorAUnEquipoSeGuardaLaAsignacion() throws Exception {

        Long idEquipo = 1L;
        Long idJugador = 2L;
        Long idTorneo = 3L;

        EquipoNBA equipo = new EquipoNBA();
        equipo.setId(idEquipo);

        Jugador jugador = new Jugador();
        jugador.setId(idJugador);

        Torneo torneo = new Torneo();
        torneo.setId(idTorneo);

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);

        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugador);

        when(servicioTorneoMock.obtenerTorneoActual(TipoTorneo.REAL)).thenReturn(torneo);

        when(repositorioEquipoNBAJugadorMock.jugadorPerteneceAUnEquipoEnElTorneo(idJugador, idTorneo)).thenReturn(false);

        servicio.agregarJugadorAlEquipo(idEquipo, idJugador);

        verify(repositorioEquipoNBAJugadorMock).asignarJugadorAUnEquipo(any(EquipoNBAJugador.class));
    }

    @Test
    public void cuandoAgregoUnJugadorAUnEquipoInexistenteLanzaExcepcion() {
        Long idEquipo = 1L;
        Long idJugador = 2L;

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(null);

        assertThrows(EquipoNoEncontradoException.class, () -> servicio.agregarJugadorAlEquipo(idEquipo, idJugador));
    }

    @Test
    public void cuandoAgregoUnJugadorQueYaPerteneceAUnEquipoLanzaExcepcion() throws Exception {
        Long idEquipo = 1L;
        Long idJugador = 2L;
        Long idTorneo = 3L;

        EquipoNBA equipo = new EquipoNBA();
        equipo.setId(idEquipo);

        Jugador jugador = new Jugador();
        jugador.setId(idJugador);

        Torneo torneo = new Torneo();
        torneo.setId(idTorneo);

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);

        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugador);

        when(servicioTorneoMock.obtenerTorneoActual(TipoTorneo.REAL)).thenReturn(torneo);

        when(repositorioEquipoNBAJugadorMock.jugadorPerteneceAUnEquipoEnElTorneo(idJugador, idTorneo)).thenReturn(true);
     
        assertThrows(elJugadorYaExisteEnElEquipoException.class, () -> servicio.agregarJugadorAlEquipo(idEquipo, idJugador)
        );
    }

    @Test
    public void cuandoEliminoUnJugadorDelEquipoSeEliminaLaAsignacion() throws Exception {

        Long idEquipo = 1L;
        Long idJugador = 2L;

        EquipoNBA equipo = new EquipoNBA();
        equipo.setId(idEquipo);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);

        when(repositorioEquipoNBAJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(asignacion);

        servicio.eliminarJugadorDelEquipo(idEquipo, idJugador);

        verify(repositorioEquipoNBAJugadorMock).eliminarJugadorDelEquipo(asignacion);
    }

    @Test
    public void cuandoSeEliminaUnJugadorDeUnEquipoInexistenteLanzaUnaExcepcion() {
        // Preparación
        Long idEquipo = 1L;
        Long idJugador = 2L;

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(null);

        // Ejecución y Verificación
        assertThrows(EquipoNoEncontradoException.class, () -> servicio.eliminarJugadorDelEquipo(idEquipo, idJugador));

    }

    @Test
    public void alObtenerLosJugadoresDeUnEquipoPorIdDevuelveElPlantelCorrectamente() {

        Long idEquipo = 1L;

        Torneo torneo = new Torneo();
        torneo.setId(1L);

        Jugador jugador1 = new Jugador();
        jugador1.setId(1L);

        Jugador jugador2 = new Jugador();
        jugador2.setId(2L);


        EquipoNBAJugador asignacion1 = new EquipoNBAJugador();
        asignacion1.setJugador(jugador1);

        EquipoNBAJugador asignacion2 = new EquipoNBAJugador();
        asignacion2.setJugador(jugador2);

        List<EquipoNBAJugador> asignaciones = new ArrayList<>();
        asignaciones.add(asignacion1);
        asignaciones.add(asignacion2);

        when(servicioTorneoMock.obtenerTorneoActual(TipoTorneo.REAL)).thenReturn(torneo);

        when(repositorioEquipoNBAJugadorMock.buscarJugadoresDelEquipoNBAEnTorneo(idEquipo, torneo.getId())).thenReturn(asignaciones);

        List<Jugador> resultado = servicio.obtenerJugadoresDelEquipoPorId(idEquipo);

        assertThat(resultado.size(), equalTo(2));
        assertThat(resultado.get(0), equalTo(jugador1));
        assertThat(resultado.get(1), equalTo(jugador2));
    }


    @Test
    public void alObtenerLosJugadoresDisponiblesDevuelveSoloLosJugadoresQueNoFueronAsignados() {

        Torneo torneo = new Torneo();
        torneo.setId(1L);

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

        // SE ASIGNA SOLO AL JUGADOR 2 A UN EQUIPO
        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setJugador(jugador2);

        List<EquipoNBAJugador> asignados = new ArrayList<>();
        asignados.add(asignacion);

        when(servicioTorneoMock.obtenerTorneoActual(TipoTorneo.REAL)).thenReturn(torneo);
        when(repositorioJugadorMock.buscarTodosLosJugadores()).thenReturn(listadoJugadoresCompleto);
        when(repositorioEquipoNBAJugadorMock.buscarAsignacionesPorTorneo(torneo.getId())).thenReturn(asignados);

        List<Jugador> listadoJugadoresDisponibles = servicio.obtenerJugadoresDisponibles();

        assertThat(listadoJugadoresDisponibles.size(), equalTo(2));
        assertThat(listadoJugadoresDisponibles.contains(jugador1), equalTo(true));
        assertThat(listadoJugadoresDisponibles.contains(jugador3), equalTo(true));
        assertFalse(listadoJugadoresDisponibles.contains(jugador2));
    }

    @Test
    public void alObtenerJugadoresFiltradosPorPosicionDevuelveSoloLosQueCoinciden() throws TemporadaActualNoEncontradaException {

        Torneo torneo = new Torneo();

        Jugador base = new Jugador();
        base.setId(1L);
        base.setPosicion(Posicion.BASE);

        Jugador pivot = new Jugador();
        pivot.setId(2L);
        pivot.setPosicion(Posicion.PIVOT);

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(base);
        jugadores.add(pivot);

        when(servicioTorneoMock.obtenerTorneoActual(TipoTorneo.REAL)).thenReturn(torneo);
        when(repositorioJugadorMock.buscarTodosLosJugadores()).thenReturn(jugadores);

        List<Jugador> resultado = servicio.obtenerJugadoresFiltrados(Posicion.BASE, null);

        assertThat(resultado.size(), equalTo(1));
        assertThat(resultado.get(0), equalTo(base));
    }

    @Test
    public void alObtenerJugadoresFiltradosPorNombreDevuelveSoloLosQueCoinciden() throws TemporadaActualNoEncontradaException {

        Torneo torneo = new Torneo();

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

        when(servicioTorneoMock.obtenerTorneoActual(TipoTorneo.REAL)).thenReturn(torneo);
        when(repositorioJugadorMock.buscarTodosLosJugadores()).thenReturn(jugadores);

        List<Jugador> jugadorBuscado = servicio.obtenerJugadoresFiltrados(null, "Jordan");

        assertThat(jugadorBuscado.size(), equalTo(1));
        assertThat(jugadorBuscado.get(0), equalTo(jordan));
    }


}
