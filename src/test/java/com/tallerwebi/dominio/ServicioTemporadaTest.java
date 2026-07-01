package com.tallerwebi.dominio.temporada;

import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioTemporadaTest {

    private ServicioTemporada servicioTemporada;
    private RepositorioTemporada repositorioTemporadaMock;

    @BeforeEach
    public void init() {
        this.repositorioTemporadaMock = mock(RepositorioTemporada.class);
        this.servicioTemporada = new ServicioTemporadaImpl(this.repositorioTemporadaMock);
    }

    @Test
    public void siGuardoUnaTemporadaSeGuardaExitosamente() {
        /* Preparación */
        Temporada temporada = new Temporada();
        temporada.setNombre("Temporada 2026");

        /* Ejecución */
        servicioTemporada.guardarTemporada(temporada);

        /* Verificación */
        verify(repositorioTemporadaMock, times(1)).guardar(temporada);
    }

    @Test
    public void obtenerTemporadaActualDevuelveLaTemporadaVigente() throws TemporadaActualNoEncontradaException {
        /* Preparación */
        Temporada temporadaEsperada = new Temporada();
        temporadaEsperada.setNombre("Temporada 2026");

        when(repositorioTemporadaMock.obtenerTemporadaActual()).thenReturn(temporadaEsperada);

        /* Ejecución */
        Temporada temporadaObtenida = servicioTemporada.obtenerTemporadaActual();

        /* Verificación */
        assertEquals(temporadaEsperada, temporadaObtenida);
        verify(repositorioTemporadaMock, times(1)).obtenerTemporadaActual();
    }

    @Test
    public void obtenerTemporadaActualSiNoHayNingunaVigenteDebeLanzarExcepcion() {
        /* Preparación */
        when(repositorioTemporadaMock.obtenerTemporadaActual()).thenReturn(null);

        /* Ejecución y Verificación */
        assertThrows(TemporadaActualNoEncontradaException.class, () -> servicioTemporada.obtenerTemporadaActual());
    }

    @Test
    public void obtenerTodasLasTemporadasDevuelveLaListaCompletaDeTemporadas() {
        /* Preparación */
        List<Temporada> temporadasEsperadas = List.of(new Temporada(), new Temporada());

        when(repositorioTemporadaMock.obtenerTodasLasTemporadas()).thenReturn(temporadasEsperadas);

        /* Ejecución */
        List<Temporada> temporadasObtenidas = servicioTemporada.obtenerTodasLasTemporadas();

        /* Verificación */
        assertEquals(temporadasEsperadas, temporadasObtenidas);
        verify(repositorioTemporadaMock, times(1)).obtenerTodasLasTemporadas();
    }

    @Test
    public void obtenerTemporadaPorIdDevuelveLaTemporadaBuscada() {
        /* Preparación */
        Long id = 1L;
        Temporada temporadaEsperada = new Temporada();

        when(repositorioTemporadaMock.obtenerTemporadaPorId(id)).thenReturn(temporadaEsperada);

        /* Ejecución */
        Temporada temporadaObtenida = servicioTemporada.obtenerTemporadaPorId(id);

        /* Verificación */
        assertEquals(temporadaEsperada, temporadaObtenida);
        verify(repositorioTemporadaMock, times(1)).obtenerTemporadaPorId(id);
    }


}