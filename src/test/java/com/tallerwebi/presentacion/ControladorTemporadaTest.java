package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.ServicioTorneo;
import com.tallerwebi.dominio.Torneo;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.ServicioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.temporada.ServicioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorTemporadaTest {

    private ControladorTemporada controladorTemporada;
    private ServicioTemporada servicioTemporadaMock;
    private ServicioTorneo servicioTorneoMock;
    private ServicioEquipoNBA servicioEquipoNBAMock;
    private ServicioEquipoNBAJugador servicioEquipoNBAJugadorMock;

    @BeforeEach
    public void inicializacion() {
        servicioTemporadaMock = mock(ServicioTemporada.class);
        servicioTorneoMock = mock(ServicioTorneo.class);
        servicioEquipoNBAMock = mock(ServicioEquipoNBA.class);
        servicioEquipoNBAJugadorMock = mock(ServicioEquipoNBAJugador.class);

        controladorTemporada = new ControladorTemporada(servicioTemporadaMock, servicioTorneoMock, servicioEquipoNBAMock, servicioEquipoNBAJugadorMock);
    }

    @Test
    public void verHistorialTemporadasRetornaLaVistaCorrecta() {

        /* Preparación */
        List<Temporada> temporadas = new ArrayList<>();
        temporadas.add(new Temporada());
        temporadas.add(new Temporada());

        when(servicioTemporadaMock.obtenerTodasLasTemporadas()).thenReturn(temporadas);

        /* Ejecución */
        ModelAndView mav = controladorTemporada.verHistorialTemporadas();

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-historial-temporadas"));
        assertThat(mav.getModel().get("temporadas"), equalTo(temporadas));
    }

    @Test
    public void verTorneosDeTemporadaRetornaLaVistaCorrecta() {

        /* Preparación */
        Long idTemporada = 1L;

        Temporada temporada = new Temporada();
        List<Torneo> torneos = new ArrayList<>();
        torneos.add(new Torneo());

        when(servicioTemporadaMock.obtenerTemporadaPorId(idTemporada)).thenReturn(temporada);
        when(servicioTorneoMock.obtenerTorneosPorTemporada(idTemporada)).thenReturn(torneos);

        /* Ejecución */
        ModelAndView mav = controladorTemporada.verTorneosDeTemporada(idTemporada);

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-historial-detalle-temporada"));
        assertThat(mav.getModel().get("temporada"), equalTo(temporada));
        assertThat(mav.getModel().get("torneos"), equalTo(torneos));
    }

    @Test
    public void verEquiposDeTorneoRetornaLaVistaCorrecta() {

        /* Preparación */
        Long idTorneo = 1L;

        Torneo torneo = new Torneo();
        List<EquipoNBA> equipos = new ArrayList<>();
        equipos.add(new EquipoNBA());

        when(servicioTorneoMock.buscarTorneoPorId(idTorneo)).thenReturn(torneo);
        when(servicioEquipoNBAMock.obtenerTodosLosEquiposOrdenadosDeMenorAMayor()).thenReturn(equipos);

        /* Ejecución */
        ModelAndView mav = controladorTemporada.verEquiposDeTorneo(idTorneo);

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-historial-detalle-torneo"));
        assertThat(mav.getModel().get("torneo"), equalTo(torneo));
        assertThat(mav.getModel().get("equipos"), equalTo(equipos));
    }

    @Test
    public void verJugadoresDeEquipoEnTorneoRetornaLaVistaCorrecta() throws EquipoNoEncontradoException {

        /* Preparación */
        Long idEquipo = 1L;
        Long idTorneo = 1L;

        EquipoNBA equipo = new EquipoNBA();
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador());

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);
        when(servicioEquipoNBAJugadorMock.obtenerJugadoresDelEquipoEnTorneo(idEquipo, idTorneo)).thenReturn(jugadores);

        /* Ejecución */
        ModelAndView mav = controladorTemporada.verJugadoresDeEquipoEnTorneo(idEquipo, idTorneo);

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-historial-equipo-torneo"));
        assertThat(mav.getModel().get("equipo"), equalTo(equipo));
        assertThat(mav.getModel().get("jugadores"), equalTo(jugadores));
        assertThat(mav.getModel().get("idTorneo"), equalTo(idTorneo));
    }

    @Test
    public void siElEquipoNoExisteRedirigeAlHistorialTemporadas() throws EquipoNoEncontradoException {

        /* Preparación */
        Long idEquipo = 1L;
        Long idTorneo = 1L;

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenThrow(new EquipoNoEncontradoException("No existe"));

        /* Ejecución */
        ModelAndView mav = controladorTemporada.verJugadoresDeEquipoEnTorneo(idEquipo, idTorneo);

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/historialTemporadas"));
    }
}