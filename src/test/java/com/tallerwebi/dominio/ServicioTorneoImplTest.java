package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FechaIncoherenteException;
import com.tallerwebi.dominio.excepcion.FechasSuperpuestasException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

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
    public void siNoExisteTorneoActivoSePuedeCrearNuevo() throws FechaIncoherenteException, FechasSuperpuestasException {

        TorneoVirtual nuevoTorneo = new TorneoVirtual();
        nuevoTorneo.setFechaInicio(LocalDate.now());
        nuevoTorneo.setFechaFin(LocalDate.now().plusDays(365));

        when(repositorioTorneoMock.obtenerTodosLosTorneosVirtuales()).thenReturn(new ArrayList<>());

        this.servicioTorneo.crearTorneo(nuevoTorneo);

        verify(repositorioTorneoMock, times(1)).guardarTorneo(nuevoTorneo);
    }
}