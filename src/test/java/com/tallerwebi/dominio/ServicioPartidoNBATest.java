package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.excepcion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioPartidoNBATest {

    private ServicioPartidoNBA servicioPartidoNBA;
    private RepositorioPartidoNBA repositorioPartidoNBAMock;
    private RepositorioEquipoNBA repositorioEquipoNBAMock;
    private RepositorioJugador repositorioJugadorMock;
    private RepositorioScorePartido repositorioScorePartidoMock;
    private RepositorioCronologiaNBA repositorioCronologiaNBAMock;
    private RepositorioEventoPartido repositorioEventoPartidoMock;

    @BeforeEach
    public void init() {
        repositorioPartidoNBAMock = mock(RepositorioPartidoNBA.class);
        repositorioCronologiaNBAMock = mock(RepositorioCronologiaNBA.class);
        repositorioScorePartidoMock = mock(RepositorioScorePartido.class);
        repositorioEquipoNBAMock = mock(RepositorioEquipoNBA.class);
        repositorioJugadorMock = mock(RepositorioJugador.class);
        repositorioEventoPartidoMock = mock(RepositorioEventoPartido.class);

        servicioPartidoNBA = new ServicioPartidoNBAImpl(
                repositorioPartidoNBAMock,
                repositorioCronologiaNBAMock,
                repositorioScorePartidoMock,
                repositorioEquipoNBAMock,
                repositorioJugadorMock,
                repositorioEventoPartidoMock
        );
    }

    private EquipoNBA crearEquipo(Long id, String nombre) {
        EquipoNBA equipo = new EquipoNBA();
        equipo.setId(id);
        equipo.setNombre(nombre);
        return equipo;
    }

    private Torneo crearTorneo(LocalDate fechaInicio) {
        Torneo torneo = new Torneo();
        torneo.setFechaInicio(fechaInicio);
        return torneo;
    }

    @Test
    public void alAgregarPartidoConFechaAnteriorAlInicioDelTorneoLanzaExcepcion() {
        EquipoNBA local = crearEquipo(1L, "Local");
        EquipoNBA visitante = crearEquipo(2L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 6, 1));
        LocalDateTime horaPartido = LocalDateTime.of(2026, 5, 15, 20, 0);

        assertThrows(FechaAnteriorInvalidaException.class,
                () -> servicioPartidoNBA.agregarPartido(local, visitante, horaPartido, torneo));

        verify(repositorioPartidoNBAMock, never()).guardar(any());
    }

    @Test
    public void alAgregarPartidoConFechaPosteriorAlInicioDelTorneoSeGuardaCorrectamente() throws Exception {
        EquipoNBA local = crearEquipo(1L, "Local");
        EquipoNBA visitante = crearEquipo(2L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 6, 1));
        LocalDateTime horaPartido = LocalDateTime.of(2026, 6, 15, 20, 0);

        when(repositorioPartidoNBAMock.existePartidoEnFecha(horaPartido)).thenReturn(false);

        servicioPartidoNBA.agregarPartido(local, visitante, horaPartido, torneo);

        verify(repositorioPartidoNBAMock, times(1)).guardar(any(PartidoNBA.class));
        verify(repositorioScorePartidoMock, times(2)).guardar(any(ScorePartido.class));
    }

    @Test
    public void alAgregarPartidoConFechaDuplicadaLanzaExcepcion() {
        EquipoNBA local = crearEquipo(1L, "Local");
        EquipoNBA visitante = crearEquipo(2L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 6, 1));
        LocalDateTime horaPartido = LocalDateTime.of(2026, 6, 15, 20, 0);

        when(repositorioPartidoNBAMock.existePartidoEnFecha(horaPartido)).thenReturn(true);

        assertThrows(FechaDuplicadaException.class,
                () -> servicioPartidoNBA.agregarPartido(local, visitante, horaPartido, torneo));

        verify(repositorioPartidoNBAMock, never()).guardar(any());
    }

    @Test
    public void alIniciarPartidoConEquipoLocalYaEnVivoLanzaExcepcion() {
        Long idPartido = 1L;
        EquipoNBA local = crearEquipo(10L, "Local");
        EquipoNBA visitante = crearEquipo(20L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 1, 1));

        PartidoNBA partido = new PartidoNBA();
        partido.setId(idPartido);
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setTorneo(torneo);
        partido.setEstadoPartido(EstadoPartido.PROGRAMADO);

        when(repositorioPartidoNBAMock.buscarPorId(idPartido)).thenReturn(partido);
        when(repositorioPartidoNBAMock.equipoTienePartidoActivo(10L)).thenReturn(true);

        assertThrows(EquipoJugandoException.class,
                () -> servicioPartidoNBA.iniciarPartido(idPartido));

        verify(repositorioPartidoNBAMock, never()).actualizar(any());
    }

    @Test
    public void alIniciarPartidoConEquipoVisitanteYaEnVivoLanzaExcepcion() {
        Long idPartido = 1L;
        EquipoNBA local = crearEquipo(10L, "Local");
        EquipoNBA visitante = crearEquipo(20L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 1, 1));

        PartidoNBA partido = new PartidoNBA();
        partido.setId(idPartido);
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setTorneo(torneo);
        partido.setEstadoPartido(EstadoPartido.PROGRAMADO);

        when(repositorioPartidoNBAMock.buscarPorId(idPartido)).thenReturn(partido);
        when(repositorioPartidoNBAMock.equipoTienePartidoActivo(10L)).thenReturn(false);
        when(repositorioPartidoNBAMock.equipoTienePartidoActivo(20L)).thenReturn(true);

        assertThrows(EquipoJugandoException.class,
                () -> servicioPartidoNBA.iniciarPartido(idPartido));

        verify(repositorioPartidoNBAMock, never()).actualizar(any());
    }

    @Test
    public void alIniciarPartidoSinEquiposEnVivoCambiaEstadoCorrectamente() throws Exception {
        Long idPartido = 1L;
        EquipoNBA local = crearEquipo(10L, "Local");
        EquipoNBA visitante = crearEquipo(20L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 1, 1));

        PartidoNBA partido = new PartidoNBA();
        partido.setId(idPartido);
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setTorneo(torneo);
        partido.setEstadoPartido(EstadoPartido.PROGRAMADO);

        when(repositorioPartidoNBAMock.buscarPorId(idPartido)).thenReturn(partido);
        when(repositorioPartidoNBAMock.equipoTienePartidoActivo(10L)).thenReturn(false);
        when(repositorioPartidoNBAMock.equipoTienePartidoActivo(20L)).thenReturn(false);

        servicioPartidoNBA.iniciarPartido(idPartido);

        verify(repositorioPartidoNBAMock, times(1)).actualizar(partido);
    }

    @Test
    public void alReprogramarPartidoConFechaAnteriorAlInicioDelTorneoLanzaExcepcion() {
        Long idPartido = 1L;
        EquipoNBA local = crearEquipo(10L, "Local");
        EquipoNBA visitante = crearEquipo(20L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 6, 1));

        PartidoNBA partido = new PartidoNBA();
        partido.setId(idPartido);
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setTorneo(torneo);
        partido.setHoraInicio(LocalDateTime.of(2026, 6, 10, 20, 0));
        partido.setEstadoPartido(EstadoPartido.PROGRAMADO);

        when(repositorioPartidoNBAMock.buscarPorId(idPartido)).thenReturn(partido);

        LocalDateTime nuevaHora = LocalDateTime.of(2026, 5, 15, 20, 0);

        assertThrows(FechaAnteriorInvalidaException.class,
                () -> servicioPartidoNBA.reprogramarPartido(idPartido, nuevaHora));

        verify(repositorioPartidoNBAMock, never()).actualizar(any());
    }

    @Test
    public void alReprogramarPartidoConFechaDuplicadaLanzaExcepcion() {
        Long idPartido = 1L;
        EquipoNBA local = crearEquipo(10L, "Local");
        EquipoNBA visitante = crearEquipo(20L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 6, 1));

        PartidoNBA partido = new PartidoNBA();
        partido.setId(idPartido);
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setTorneo(torneo);
        partido.setHoraInicio(LocalDateTime.of(2026, 6, 10, 20, 0));
        partido.setEstadoPartido(EstadoPartido.PROGRAMADO);

        when(repositorioPartidoNBAMock.buscarPorId(idPartido)).thenReturn(partido);
        when(repositorioPartidoNBAMock.existePartidoEnFecha(any())).thenReturn(true);

        LocalDateTime nuevaHora = LocalDateTime.of(2026, 6, 20, 20, 0);

        assertThrows(FechaDuplicadaException.class,
                () -> servicioPartidoNBA.reprogramarPartido(idPartido, nuevaHora));

        verify(repositorioPartidoNBAMock, never()).actualizar(any());
    }

    @Test
    public void alReprogramarPartidoConFechaValidaSeActualizaCorrectamente() throws Exception {
        Long idPartido = 1L;
        EquipoNBA local = crearEquipo(10L, "Local");
        EquipoNBA visitante = crearEquipo(20L, "Visitante");
        Torneo torneo = crearTorneo(LocalDate.of(2026, 6, 1));

        PartidoNBA partido = new PartidoNBA();
        partido.setId(idPartido);
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setTorneo(torneo);
        partido.setHoraInicio(LocalDateTime.of(2026, 6, 10, 20, 0));
        partido.setEstadoPartido(EstadoPartido.PROGRAMADO);

        when(repositorioPartidoNBAMock.buscarPorId(idPartido)).thenReturn(partido);
        when(repositorioPartidoNBAMock.existePartidoEnFecha(any())).thenReturn(false);

        LocalDateTime nuevaHora = LocalDateTime.of(2026, 6, 20, 20, 0);
        servicioPartidoNBA.reprogramarPartido(idPartido, nuevaHora);

        verify(repositorioPartidoNBAMock, times(1)).actualizar(partido);
    }
}
