package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LigaTest {

    @Test
    public void crearLigaDemo_deberiaRetornarListasNoVacias() {
        Liga liga = Liga.crearLigaDemo();
        assertNotNull(liga);
        assertFalse(liga.getHistorialPartidos().isEmpty(), "Historial no debe estar vacío");
        assertFalse(liga.getProximosPartidos().isEmpty(), "Proximos partidos no debe estar vacío");

        PartidoJugado pj = liga.getHistorialPartidos().get(0);
        assertTrue(pj.getPuntosLocal() >= 0);
        assertTrue(pj.getPuntosVisitante() >= 0);
        assertFalse(pj.getRendimientoEquipoLocal().isEmpty());
    }
}
