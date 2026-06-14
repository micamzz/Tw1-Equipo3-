package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipo.RepositorioEquipo;
import com.tallerwebi.dominio.excepcion.FechaIncoherenteException;
import com.tallerwebi.dominio.excepcion.FechasSuperpuestasException;
import com.tallerwebi.dominio.excepcion.NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException;
import com.tallerwebi.dominio.excepcion.TorneoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioTorneoImplTest {

    private ServicioTorneo servicioTorneo;
    private RepositorioTorneo repositorioTorneoMock;
    private RepositorioEquipo repositorioEquipoMock;

    @BeforeEach
    public void init() {
        this.repositorioTorneoMock = mock(RepositorioTorneo.class);
        this.repositorioEquipoMock = mock(RepositorioEquipo.class);
        this.servicioTorneo = new ServicioTorneoImpl(this.repositorioTorneoMock, this.repositorioEquipoMock);
    }

    @Test
    public void siCreoUnNuevoTorneoSeGuardaExitosamente() throws FechaIncoherenteException, FechasSuperpuestasException {

        TorneoVirtual nuevoTorneo = new TorneoVirtual();
        nuevoTorneo.setNombreTorneo("Temporada 2026");
        nuevoTorneo.setFechaInicio(LocalDate.now());
        nuevoTorneo.setFechaFin(LocalDate.now().plusDays(365));

        when(repositorioTorneoMock.obtenerTodosLosTorneosVirtuales()).thenReturn(new ArrayList<>());

        this.servicioTorneo.crearTorneo(nuevoTorneo);

        verify(repositorioTorneoMock, times(1)).guardarTorneo(nuevoTorneo);
    }

    @Test
    public void siCreoUnTorneoConFechaFinAnteriorAFechaInicioDebeLanzarExcepcion() {
        TorneoVirtual nuevoTorneo = new TorneoVirtual();
        nuevoTorneo.setNombreTorneo("Temporada 2026");
        nuevoTorneo.setFechaInicio(LocalDate.now().plusDays(1));
        nuevoTorneo.setFechaFin(LocalDate.now());

        assertThrows(
                FechaIncoherenteException.class,
                () -> servicioTorneo.crearTorneo(nuevoTorneo)
        );

        verify(repositorioTorneoMock, never()).guardarTorneo(nuevoTorneo);

    }


    @Test
    public void siCreoUnTorneoConFechasSuperpuestasDebeLanzarExcepcion() {
        TorneoVirtual torneoExistente = new TorneoVirtual();
        torneoExistente.setNombreTorneo("Temporada 2026");
        torneoExistente.setFechaInicio(LocalDate.of(2026, 1, 1));
        torneoExistente.setFechaFin(LocalDate.of(2026, 12, 31));

        TorneoVirtual nuevoTorneo = new TorneoVirtual();
        nuevoTorneo.setNombreTorneo("Temporada 2026");
        nuevoTorneo.setFechaInicio(LocalDate.of(2026, 6, 1));
        nuevoTorneo.setFechaFin(LocalDate.of(2026, 6, 30));


        when(repositorioTorneoMock.obtenerTodosLosTorneosVirtuales()).thenReturn(List.of(torneoExistente));

        assertThrows(FechasSuperpuestasException.class, () -> servicioTorneo.crearTorneo(nuevoTorneo));

        verify(repositorioTorneoMock, never()).guardarTorneo(nuevoTorneo);
    }

    @Test
    public void eliminarTorneoExistenteDeberiaEliminarlo()
            throws Exception {

        Long id = 1L;

        TorneoVirtual torneo = new TorneoVirtual();

        when(repositorioTorneoMock.buscarTorneoPorId(id))
                .thenReturn(torneo);

        when(repositorioEquipoMock.existeEquipoEnTorneo(id))
                .thenReturn(false);

        servicioTorneo.eliminarTorneo(id);

        verify(repositorioTorneoMock, times(1))
                .eliminarTorneo(torneo);
    }

    @Test
    public void eliminarTorneoInexistenteDebeLanzarExcepcion() {

        Long id = 99L;

        when(repositorioTorneoMock.buscarTorneoPorId(id))
                .thenReturn(null);

        assertThrows(
                TorneoNoEncontradoException.class,
                () -> servicioTorneo.eliminarTorneo(id)
        );

        verify(repositorioTorneoMock, never())
                .eliminarTorneo(any());
    }

    @Test
    public void eliminarTorneoConEquiposAsociadosDebeLanzarExcepcion() {

        Long id = 1L;

        TorneoVirtual torneo = new TorneoVirtual();

        when(repositorioTorneoMock.buscarTorneoPorId(id))
                .thenReturn(torneo);

        when(repositorioEquipoMock.existeEquipoEnTorneo(id))
                .thenReturn(true);

        assertThrows(
                NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException.class,
                () -> servicioTorneo.eliminarTorneo(id)
        );

        verify(repositorioTorneoMock, never())
                .eliminarTorneo(any());
    }
}

