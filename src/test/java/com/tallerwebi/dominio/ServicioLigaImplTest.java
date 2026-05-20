package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ServicioLigaImplTest {

    private RepositorioJugador repositorioMock;
    private ServicioLigaImpl servicioLiga;

    @BeforeEach
    public void setup() {
        repositorioMock = Mockito.mock(RepositorioJugador.class);
        servicioLiga = new ServicioLigaImpl(repositorioMock);
    }

    @Test
    public void obtenerLiga_deberiaRetornarLigaPopuladaDelRepositorioJugadores() {
        Jugador j1 = new Jugador();
        j1.setNombre("Luka");
        j1.setApellido("Doncic");
        j1.setPosicion(Posicion.BASE);

        Jugador j2 = new Jugador();
        j2.setNombre("LeBron");
        j2.setApellido("James");
        j2.setPosicion(Posicion.ALERO);

        Jugador j3 = new Jugador();
        j3.setNombre("Nikola");
        j3.setApellido("Jokic");
        j3.setPosicion(Posicion.PIVOT);

        Mockito.when(repositorioMock.buscarJugadores(null, null)).thenReturn(Arrays.asList(j1, j2, j3));

        Liga liga = servicioLiga.obtenerLiga();

        assertNotNull(liga);
        assertFalse(liga.getHistorialPartidos().isEmpty(), "Historial de partidos no debe estar vacío");

        PartidoJugado pj = liga.getHistorialPartidos().get(0);
        assertNotNull(pj.getRendimientoEquipoLocal());
        assertNotNull(pj.getRendimientoEquipoVisitante());

        // Verify that player names from repository appear in rendimientos
        boolean found = pj.getRendimientoEquipoLocal().stream()
                .anyMatch(r -> r.getNombreCompleto().contains("Luka Doncic"));
        assertTrue(found || pj.getRendimientoEquipoVisitante().stream()
                .anyMatch(r -> r.getNombreCompleto().contains("Luka Doncic")));
    }

    @Test
    public void obtenerLiga_conListaDeJugadoresVacia_deberiaRetornarListaDeRendimientosVacia() {
        Mockito.when(repositorioMock.buscarJugadores(null, null)).thenReturn(Collections.emptyList());

        Liga liga = servicioLiga.obtenerLiga();

        assertNotNull(liga);
        assertFalse(liga.getHistorialPartidos().isEmpty(), "Se sigue creando un partido aun con lista vacía");

        PartidoJugado pj = liga.getHistorialPartidos().get(0);
        assertTrue(pj.getRendimientoEquipoLocal().isEmpty());
        assertTrue(pj.getRendimientoEquipoVisitante().isEmpty());
        assertEquals(0, pj.getPuntosLocal());
        assertEquals(0, pj.getPuntosVisitante());
    }
}
