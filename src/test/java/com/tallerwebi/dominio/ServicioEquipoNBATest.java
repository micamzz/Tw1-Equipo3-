package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioEquipoNBATest {
    private ServicioEquipoNBAimpl servicioEquipoNBA;
    private RepositorioEquipoNBA repositorioEquipoNBAMock;
    private RepositorioJugador repositorioJugadorMock;
    private RepositorioEquipoNBAJugador repositorioEquipoNBAJugadorMock;

    @BeforeEach
    public void inicializacion() {
        repositorioEquipoNBAMock = mock(RepositorioEquipoNBA.class);
        repositorioJugadorMock = mock(RepositorioJugador.class);
        repositorioEquipoNBAJugadorMock = mock(RepositorioEquipoNBAJugador.class);
        servicioEquipoNBA = new ServicioEquipoNBAimpl(repositorioEquipoNBAMock, repositorioJugadorMock, repositorioEquipoNBAJugadorMock);
    }


    @Test
    public void guardarEquipoNBALlamaAlRepositorio() {
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Lakers");

        servicioEquipoNBA.guardarEquipoNBA(equipo);

        verify(repositorioEquipoNBAMock, times(1)).crearEquipo(equipo);
    }


    @Test
    public void buscarEquipoPorIdRetornaElEquipoCuandoExiste() throws EquipoNoEncontradoException {
        Long idEquipo = 1L;
        EquipoNBA equipo = new EquipoNBA();
        equipo.setId(idEquipo);

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);

        EquipoNBA resultado = servicioEquipoNBA.buscarEquipoPorId(idEquipo);

        assertThat(resultado, equalTo(equipo));
    }

    @Test
    public void buscarEquipoPorIdLanzaExcepcionCuandoNoExiste() {
        Long idEquipo = 99L;

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(null);

        assertThrows(EquipoNoEncontradoException.class, () -> servicioEquipoNBA.buscarEquipoPorId(idEquipo)
        );
    }


    @Test
    public void obtenerTodosLosEquiposRetornaLaListaCompleta() {
        List<EquipoNBA> equipos = new ArrayList<>();
        equipos.add(new EquipoNBA());
        equipos.add(new EquipoNBA());

        when(repositorioEquipoNBAMock.obtenerTodosLosEquipos()).thenReturn(equipos);

        List<EquipoNBA> resultado = servicioEquipoNBA.obtenerTodosLosEquipos();

        assertThat(resultado, equalTo(equipos));

    }


    @Test
    public void agregarJugadorAlEquipoGuardaLaAsignacion() throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException {
        Long idEquipo = 1L;
        Long idJugador = 2L;

        EquipoNBA equipo = new EquipoNBA();
        equipo.setId(idEquipo);

        Jugador jugador = new Jugador();
        jugador.setId(idJugador);

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugador);

        servicioEquipoNBA.agregarJugadorAlEquipo(idEquipo, idJugador);

        verify(repositorioEquipoNBAJugadorMock, times(1)).asignarJugadorAUnEquipo(any(EquipoNBAJugador.class));
    }

    @Test
    public void agregarJugadorAlEquipoLanzaExcepcionSiElEquipoNoExiste() {
        Long idEquipo = 99L;
        Long idJugador = 2L;

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(null);

        assertThrows(EquipoNoEncontradoException.class, () -> servicioEquipoNBA.agregarJugadorAlEquipo(idEquipo, idJugador)
        );
    }
}
