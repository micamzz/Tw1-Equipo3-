package com.tallerwebi.dominio;

import com.tallerwebi.dominio.enums.TipoEstadistica;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.eventoPartido.EventoPartido;
import com.tallerwebi.dominio.eventoPartido.RepositorioEventoPartido;
import com.tallerwebi.dominio.eventoPartido.ServicioEventoPartido;
import com.tallerwebi.dominio.eventoPartido.ServicioEventoPartidoImpl;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.formacion.RepositorioFormacion;
import com.tallerwebi.dominio.jugador.Jugador;
import com.tallerwebi.dominio.jugador.RepositorioJugador;
import com.tallerwebi.dominio.partidoNBA.PartidoNBA;
import com.tallerwebi.dominio.partidoNBA.RepositorioPartidoNBA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioEventoPartidoTest {

    private ServicioEventoPartido servicioEventoPartido;
    private RepositorioEventoPartido repositorioEventoPartidoMock;
    private RepositorioPartidoNBA repositorioPartidoNBAMock;
    private RepositorioJugador repositorioJugadorMock;
    private RepositorioFormacion repositorioFormacionMock;

    @BeforeEach
    public void init() {
        this.repositorioEventoPartidoMock = mock(RepositorioEventoPartido.class);
        this.repositorioPartidoNBAMock = mock(RepositorioPartidoNBA.class);
        this.repositorioJugadorMock = mock(RepositorioJugador.class);
        this.repositorioFormacionMock = mock(RepositorioFormacion.class);

        // Constructor actualizado con los 4 repositorios actuales
        this.servicioEventoPartido = new ServicioEventoPartidoImpl(
                repositorioEventoPartidoMock,
                repositorioPartidoNBAMock,
                repositorioJugadorMock,
                repositorioFormacionMock
        );
    }

    /*
        @Test
        public void queSePuedaRegistrarUnEventoExitosamente() throws Exception {
            // Preparación
            Long idPartido = 1L;
            Long idJugador = 10L;
            LocalTime momento = LocalTime.of(0, 10, 0);

            PartidoNBA partido = new PartidoNBA();
            partido.setEstadoPartido(EstadoPartido.EN_VIVO);

            Jugador jugador = new Jugador();

            when(repositorioPartidoNBAMock.buscarPorId(idPartido)).thenReturn(partido);
            when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugador);
            when(repositorioFormacionMock.jugadorYaEstaEnFormacion(idPartido, idJugador)).thenReturn(true);

            // Ejecución
            servicioEventoPartido.registrarEvento(idPartido, idJugador, momento, TipoEstadistica.TRIPLE, true);

            // Verificación
            verify(repositorioEventoPartidoMock, times(1)).guardarEventoPartido(any(EventoPartido.class));
        }
    ^*/
    @Test
    public void queLanceExcepcionSiElPartidoNoExiste() {
        when(repositorioPartidoNBAMock.buscarPorId(1L)).thenReturn(null);

        assertThrows(PartidoNoEncontradoException.class, () -> {
            servicioEventoPartido.registrarEvento(1L, 10L, LocalTime.now(), TipoEstadistica.DOBLE, true);
        });
    }

    @Test
    public void queLanceExcepcionSiElJugadorNoExiste() {
        when(repositorioPartidoNBAMock.buscarPorId(1L)).thenReturn(new PartidoNBA());
        when(repositorioJugadorMock.buscarJugadorPorId(10L)).thenReturn(null);

        assertThrows(JugadorNoEncontradoException.class, () -> {
            servicioEventoPartido.registrarEvento(1L, 10L, LocalTime.now(), TipoEstadistica.DOBLE, true);
        });
    }

    @Test
    public void queLanceExcepcionSiElJugadorNoEstaConvocado() {
        when(repositorioPartidoNBAMock.buscarPorId(1L)).thenReturn(new PartidoNBA());
        when(repositorioJugadorMock.buscarJugadorPorId(10L)).thenReturn(new Jugador());
        when(repositorioFormacionMock.jugadorYaEstaEnFormacion(1L, 10L)).thenReturn(false);

        assertThrows(JugadorNoConvocadoException.class, () -> {
            servicioEventoPartido.registrarEvento(1L, 10L, LocalTime.now(), TipoEstadistica.DOBLE, true);
        });
    }

    @Test
    public void queLanceExcepcionSiElMomentoEsNulo() {
        PartidoNBA partido = new PartidoNBA();
        when(repositorioPartidoNBAMock.buscarPorId(1L)).thenReturn(partido);
        when(repositorioJugadorMock.buscarJugadorPorId(10L)).thenReturn(new Jugador());
        when(repositorioFormacionMock.jugadorYaEstaEnFormacion(1L, 10L)).thenReturn(true);

        assertThrows(MomentoPartidoInvalidoException.class, () -> {
            servicioEventoPartido.registrarEvento(1L, 10L, null, TipoEstadistica.DOBLE, true);
        });
    }

    @Test
    public void queLanceExcepcionSiElPartidoNoEstaEnCurso() {
        PartidoNBA partido = new PartidoNBA();
        partido.setEstadoPartido(EstadoPartido.PROGRAMADO); // No es EN_VIVO

        when(repositorioPartidoNBAMock.buscarPorId(1L)).thenReturn(partido);
        when(repositorioJugadorMock.buscarJugadorPorId(10L)).thenReturn(new Jugador());
        when(repositorioFormacionMock.jugadorYaEstaEnFormacion(1L, 10L)).thenReturn(true);

        assertThrows(PartidoNoEnCursoException.class, () -> {
            servicioEventoPartido.registrarEvento(1L, 10L, LocalTime.of(0, 20, 0), TipoEstadistica.DOBLE, true);
        });
    }

    @Test
    public void queSePuedanBuscarEventosPorPartido() {
        Long idPartido = 1L;
        List<EventoPartido> eventosEsperados = new ArrayList<>();
        eventosEsperados.add(new EventoPartido());

        when(repositorioEventoPartidoMock.buscarEventosPorPartido(idPartido)).thenReturn(eventosEsperados);

        List<EventoPartido> eventosObtenidos = servicioEventoPartido.buscarEventosPorPartido(idPartido);

        assertThat(eventosObtenidos, hasSize(1));
        verify(repositorioEventoPartidoMock, times(1)).buscarEventosPorPartido(idPartido);
    }
}
