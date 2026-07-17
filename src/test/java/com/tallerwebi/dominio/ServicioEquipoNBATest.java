package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.equipoNBA.ServicioEquipoNBAimpl;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.jugador.RepositorioJugador;
import com.tallerwebi.dominio.torneo.ServicioTorneo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioEquipoNBATest {

    private RepositorioEquipoNBA repositorioEquipoNBAMock;
    private RepositorioJugador repositorioJugadorMock;
    private RepositorioEquipoNBAJugador repositorioEquipoNBAJugadorMock;
    private ServicioTorneo servicioTorneoMock;
    private ServicioEquipoNBAimpl servicioEquipoNBA;

    @BeforeEach
    public void inicializacion() {
        repositorioEquipoNBAMock = mock(RepositorioEquipoNBA.class);
        repositorioJugadorMock = mock(RepositorioJugador.class);
        repositorioEquipoNBAJugadorMock = mock(RepositorioEquipoNBAJugador.class);
        servicioTorneoMock = mock(ServicioTorneo.class);

        servicioEquipoNBA = new ServicioEquipoNBAimpl(repositorioEquipoNBAMock, repositorioJugadorMock, repositorioEquipoNBAJugadorMock, servicioTorneoMock
        );
    }

    @Test
    public void cuandoSeGuardaUnEquipoNBASeGuardaCorrectamente() {
        /* Preparación */
        EquipoNBA equipo = new EquipoNBA();
        equipo.setNombre("Golden State Warriors");

        /* Ejecución */
        servicioEquipoNBA.crearEquipoNBA(equipo);

        /* Validación */
        verify(repositorioEquipoNBAMock).crearEquipo(equipo);
    }

    @Test
    public void alBuscarUnEquipoNBAPorIdDevuelveElEquipoCorrecto() throws EquipoNoEncontradoException {
        /* Preparación */
        Long idEquipo = 1L;
        EquipoNBA equipo = new EquipoNBA();
        equipo.setId(idEquipo);

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);

        /* Ejecución */
        EquipoNBA resultado = servicioEquipoNBA.buscarEquipoPorId(idEquipo);

        /* Validación */
        assertThat(resultado, equalTo(equipo));
    }

    @Test
    public void alBuscarUnEquipoNBAPorIdInexistenteLanzaUnaExcepcion() {
        /* Preparación */
        Long idEquipo = 1L;

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(null);

        /* Ejecución - Validación */
        assertThrows(EquipoNoEncontradoException.class, () -> servicioEquipoNBA.buscarEquipoPorId(idEquipo));
    }

    @Test
    public void alObtenerTodosLosEquiposNBADevuelveElListadoCorrespondiente() {
        /* Preparación */
        List<EquipoNBA> equipos = new ArrayList<>();
        equipos.add(new EquipoNBA());
        equipos.add(new EquipoNBA());

        when(repositorioEquipoNBAMock.obtenerTodosLosEquiposOrdenados()).thenReturn(equipos);

        /* Ejecución */
        List<EquipoNBA> resultado = servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor();

        /* Validación */
        assertThat(resultado, equalTo(equipos));
    }

    @Test
    public void cuandoSeEliminaUnEquipoNBASeEliminanLasAsignacionesYElEquipoCorrectamente()
            throws EquipoNoEncontradoException {
        /* Preparación */
        Long idEquipo = 1L;

        EquipoNBA equipo = new EquipoNBA();
        equipo.setId(idEquipo);

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);

        /* Ejecución */
        servicioEquipoNBA.eliminarEquipoNBA(idEquipo);

        /* Validación */
        verify(repositorioEquipoNBAJugadorMock).eliminarTodasLasAsignacionesDelEquipo(idEquipo);

        verify(repositorioEquipoNBAMock).eliminar(equipo);
    }

    @Test
    public void cuandoSeEliminaUnEquipoNBAInexistenteLanzaUnaExcepcion() {
        /* Preparación */
        Long idEquipo = 1L;

        when(repositorioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(null);

        /* Ejecución - Validación */
        assertThrows(EquipoNoEncontradoException.class, () -> servicioEquipoNBA.eliminarEquipoNBA(idEquipo)
        );

        verify(repositorioEquipoNBAJugadorMock, never()).eliminarTodasLasAsignacionesDelEquipo(any());

        verify(repositorioEquipoNBAMock, never()).eliminar(any());
    }
}