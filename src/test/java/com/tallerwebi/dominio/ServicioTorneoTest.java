package com.tallerwebi.dominio;


import com.tallerwebi.dominio.equipo.RepositorioEquipo;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioTorneoTest {

    private ServicioTorneo servicioTorneo;
    private RepositorioTorneo repositorioTorneoMock;
    private RepositorioEquipo repositorioEquipoMock;
    private RepositorioEquipoNBAJugador repositorioEquipoNBAJugadorMock;

    @BeforeEach
    public void init() {
        this.repositorioTorneoMock = mock(RepositorioTorneo.class);
        this.repositorioEquipoMock = mock(RepositorioEquipo.class);
        this.repositorioEquipoNBAJugadorMock = mock(RepositorioEquipoNBAJugador.class);
        this.servicioTorneo = new ServicioTorneoImpl(this.repositorioTorneoMock, this.repositorioEquipoMock, this.repositorioEquipoNBAJugadorMock);
    }


    @Test
    public void siCreoUnNuevoTorneoSeGuardaExitosamente() throws FechaIncoherenteException, FechasSuperpuestasException, NombreDeTorneoEnBlancoException, TipoDeTorneoEnBlancoException {
        /* Preparación */
        Torneo nuevoTorneo = new Torneo();
        nuevoTorneo.setNombreTorneo("Temporada 2026");
        nuevoTorneo.setFechaInicio(LocalDate.now());
        nuevoTorneo.setFechaFin(LocalDate.now().plusDays(365));
        nuevoTorneo.setTipoTorneo(TipoTorneo.VIRTUAL);

        when(repositorioTorneoMock.obtenerTorneosPorTipo(any())).thenReturn(new ArrayList<>());

        /* Ejecución */
        this.servicioTorneo.crearTorneo(nuevoTorneo);

        /* Verificación */
        verify(repositorioTorneoMock, times(1)).guardarTorneo(nuevoTorneo);
    }

    @Test
    public void siCreoUnTorneoConFechaFinAnteriorAFechaInicioDebeLanzarExcepcion() {
        /* Preparación */
        Torneo nuevoTorneo = new Torneo();
        nuevoTorneo.setNombreTorneo("Temporada 2026");
        nuevoTorneo.setFechaInicio(LocalDate.now().plusDays(1));
        nuevoTorneo.setFechaFin(LocalDate.now());
        nuevoTorneo.setTipoTorneo(TipoTorneo.VIRTUAL);

        /* Ejecución y Verificación */
        assertThrows(
                FechaIncoherenteException.class,
                () -> servicioTorneo.crearTorneo(nuevoTorneo)
        );

        verify(repositorioTorneoMock, never()).guardarTorneo(nuevoTorneo);
    }

    @Test
    public void siCreoUnTorneoConFechaInicioOFechaFinNulasDebeLanzarExcepcion() {
        /* Preparación */
        Torneo nuevoTorneo = new Torneo();
        nuevoTorneo.setNombreTorneo("Temporada 2026");
        nuevoTorneo.setFechaInicio(null);
        nuevoTorneo.setFechaFin(LocalDate.now());
        nuevoTorneo.setTipoTorneo(TipoTorneo.VIRTUAL);

        /* Ejecución y Verificación */
        assertThrows(
                FechaIncoherenteException.class,
                () -> servicioTorneo.crearTorneo(nuevoTorneo)
        );

        verify(repositorioTorneoMock, never()).guardarTorneo(nuevoTorneo);
    }

    @Test
    public void siCreoUnTorneoConTipoDeTorneoNuloDebeLanzarExcepcion() {
        /* Preparación */
        Torneo nuevoTorneo = new Torneo();
        nuevoTorneo.setNombreTorneo("Temporada 2026");
        nuevoTorneo.setFechaInicio(LocalDate.now());
        nuevoTorneo.setFechaFin(LocalDate.now().plusDays(365));
        nuevoTorneo.setTipoTorneo(null);

        /* Ejecución y Verificación */
        assertThrows(TipoDeTorneoEnBlancoException.class, () -> servicioTorneo.crearTorneo(nuevoTorneo)
        );

        verify(repositorioTorneoMock, never()).guardarTorneo(nuevoTorneo);
    }

    @Test
    public void siCreoUnTorneoConNombreEnBlancoDebeLanzarExcepcion() {
        /* Preparación */
        Torneo nuevoTorneo = new Torneo();
        nuevoTorneo.setNombreTorneo("   ");
        nuevoTorneo.setFechaInicio(LocalDate.now());
        nuevoTorneo.setFechaFin(LocalDate.now().plusDays(365));
        nuevoTorneo.setTipoTorneo(TipoTorneo.VIRTUAL);

        /* Ejecución y Verificación */
        assertThrows(NombreDeTorneoEnBlancoException.class, () -> servicioTorneo.crearTorneo(nuevoTorneo)
        );

        verify(repositorioTorneoMock, never()).guardarTorneo(nuevoTorneo);
    }

    @Test
    public void siCreoUnTorneoConFechasSuperpuestasDebeLanzarExcepcion() {
        /* Preparación */
        Torneo torneoExistente = new Torneo();
        torneoExistente.setNombreTorneo("Temporada 2026");
        torneoExistente.setFechaInicio(LocalDate.of(2026, 1, 1));
        torneoExistente.setFechaFin(LocalDate.of(2026, 12, 31));
        torneoExistente.setTipoTorneo(TipoTorneo.VIRTUAL);

        Torneo nuevoTorneo = new Torneo();
        nuevoTorneo.setNombreTorneo("Temporada 2026");
        nuevoTorneo.setFechaInicio(LocalDate.of(2026, 6, 1));
        nuevoTorneo.setFechaFin(LocalDate.of(2026, 6, 30));
        nuevoTorneo.setTipoTorneo(TipoTorneo.VIRTUAL);

        when(repositorioTorneoMock.obtenerTorneosPorTipo(any())).thenReturn(List.of(torneoExistente));

        /* Ejecución y Verificación */
        assertThrows(FechasSuperpuestasException.class, () -> servicioTorneo.crearTorneo(nuevoTorneo));

        verify(repositorioTorneoMock, never()).guardarTorneo(nuevoTorneo);
    }

    @Test
    public void eliminarTorneoExistenteDeberiaEliminarlo()
            throws Exception {

        /* Preparación */
        Long id = 1L;

        Torneo torneo = new Torneo();

        when(repositorioTorneoMock.buscarTorneoPorId(id))
                .thenReturn(torneo);

        when(repositorioEquipoMock.existeEquipoEnTorneo(id))
                .thenReturn(false);

        when(repositorioEquipoNBAJugadorMock.existenJugadoresAsignadosEnTorneo(id))
                .thenReturn(false);

        /* Ejecución */
        servicioTorneo.eliminarTorneo(id);

        /* Verificación */
        verify(repositorioTorneoMock, times(1))
                .eliminarTorneo(torneo);
    }

    @Test
    public void eliminarTorneoInexistenteDebeLanzarExcepcion() {

        /* Preparación */
        Long id = 99L;

        when(repositorioTorneoMock.buscarTorneoPorId(id))
                .thenReturn(null);

        /* Ejecución y Verificación */
        assertThrows(
                TorneoNoEncontradoException.class,
                () -> servicioTorneo.eliminarTorneo(id)
        );

        verify(repositorioTorneoMock, never())
                .eliminarTorneo(any());
    }

    @Test
    public void eliminarTorneoConEquiposAsociadosDebeLanzarExcepcion() {

        /* Preparación */
        Long id = 1L;

        Torneo torneo = new Torneo();

        when(repositorioTorneoMock.buscarTorneoPorId(id))
                .thenReturn(torneo);

        when(repositorioEquipoMock.existeEquipoEnTorneo(id))
                .thenReturn(true);

        /* Ejecución y Verificación */
        assertThrows(
                NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException.class,
                () -> servicioTorneo.eliminarTorneo(id)
        );

        verify(repositorioTorneoMock, never()).eliminarTorneo(any());
    }

    @Test
    public void eliminarTorneoConJugadoresNBAAsignadosDebeLanzarExcepcion() {
        /* Preparación */
        Long id = 1L;

        Torneo torneo = new Torneo();

        when(repositorioTorneoMock.buscarTorneoPorId(id)).thenReturn(torneo);

        when(repositorioEquipoMock.existeEquipoEnTorneo(id)).thenReturn(false);

        when(repositorioEquipoNBAJugadorMock.existenJugadoresAsignadosEnTorneo(id)).thenReturn(true);

        /* Ejecución y Verificación */
        assertThrows(NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException.class, () -> servicioTorneo.eliminarTorneo(id)
        );

        verify(repositorioTorneoMock, never()).eliminarTorneo(any());
    }

    @Test
    public void obtenerTorneoActualDevuelveElTorneoVigente() {
        /* Preparación */
        Torneo torneoEsperado = new Torneo();
        torneoEsperado.setNombreTorneo("Temporada 2026");

        when(repositorioTorneoMock.buscarTorneoActual(TipoTorneo.VIRTUAL)).thenReturn(torneoEsperado);

        /* Ejecución */
        Torneo torneoObtenido = servicioTorneo.obtenerTorneoActual(TipoTorneo.VIRTUAL);

        /* Verificación */
        assertEquals(torneoEsperado, torneoObtenido);
        verify(repositorioTorneoMock, times(1)).buscarTorneoActual(TipoTorneo.VIRTUAL);
    }

    @Test
    public void buscarTorneoPorIdDevuelveElTorneoBuscado() {
        /* Preparación */
        Long id = 1L;
        Torneo torneoEsperado = new Torneo();

        when(repositorioTorneoMock.buscarTorneoPorId(id)).thenReturn(torneoEsperado);

        /* Ejecución */
        Torneo torneoObtenido = servicioTorneo.buscarTorneoPorId(id);

        /* Verificación */
        assertEquals(torneoEsperado, torneoObtenido);
        verify(repositorioTorneoMock, times(1)).buscarTorneoPorId(id);
    }

    @Test
    public void obtenerTodosLosTorneosDevuelveLaListaCompletaDeTorneos() {
        /* Preparación */
        List<Torneo> torneosEsperados = List.of(new Torneo(), new Torneo());

        when(repositorioTorneoMock.obtenerTodosLosTorneos()).thenReturn(torneosEsperados);

        /* Ejecución */
        List<Torneo> torneosObtenidos = servicioTorneo.obtenerTodosLosTorneos();

        /* Verificación */
        assertEquals(torneosEsperados, torneosObtenidos);
        verify(repositorioTorneoMock, times(1)).obtenerTodosLosTorneos();
    }

    @Test
    public void obtenerTorneosPorTemporadaDevuelveLosTorneosDeEsaTemporada() {

        /* Preparación */
        Long idTemporada = 5L;
        List<Torneo> torneosEsperados = List.of(new Torneo());

        when(repositorioTorneoMock.obtenerTorneosPorTemporada(idTemporada)).thenReturn(torneosEsperados);

        /* Ejecución */
        List<Torneo> torneosObtenidos = servicioTorneo.obtenerTorneosPorTemporada(idTemporada);

        /* Verificación */
        assertEquals(torneosEsperados, torneosObtenidos);
        verify(repositorioTorneoMock, times(1)).obtenerTorneosPorTemporada(idTemporada);
    }
}