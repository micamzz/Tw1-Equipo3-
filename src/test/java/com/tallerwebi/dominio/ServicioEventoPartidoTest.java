/*  package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.mockito.Mockito.*;


public class ServicioEventoPartidoTest {

    private ServicioEventoPartido servicioEventoPartido;
    private RepositorioEventoPartido repositorioEventoPartidoMock;
    private RepositorioPartidoNBA repositorioPartidoNBAMock;
    private RepositorioJugador repositorioJugadorMock;
    private ServicioFormacionSabri servicioFormacionMock;

    @BeforeEach
    public void init() {
        this.repositorioEventoPartidoMock = mock(RepositorioEventoPartido.class);
        this.repositorioPartidoNBAMock = mock(RepositorioPartidoNBA.class);
        this.repositorioJugadorMock = mock(RepositorioJugador.class);
        this.servicioFormacionMock = mock(ServicioFormacionSabri.class);

        this.servicioEventoPartido = new ServicioEventoPartidoImpl(
                repositorioEventoPartidoMock,
                repositorioPartidoNBAMock,
                repositorioJugadorMock,
                servicioFormacionMock
        );
          }

  @Test
    public void puedoRegistrarUnEventoExitosamente() throws Exception {

        Long idPartido = 1L;
        Long idJugador = 10L;

        PartidoNBA partido = new PartidoNBA();
        partido.setMinutoFin(48);

        Jugador jugador = new Jugador();

        when(repositorioPartidoNBAMock.buscarPorId(idPartido))
                .thenReturn(partido);

        when(repositorioJugadorMock.buscarJugadorPorId(idJugador))
                .thenReturn(jugador);

        when(servicioFormacionMock.existeJugadorEnFormacion(
                idPartido,
                idJugador))
                .thenReturn(true);

        servicioEventoPartido.registrarEvento(
                idPartido,
                idJugador,
                LocalTime.of(0, 35, 20),
                TipoEstadistica.TRIPLE
        );

        verify(repositorioEventoPartidoMock, times(1))
                .guardarEventoPartido(any(EventoPartido.class));
    }

}*/

