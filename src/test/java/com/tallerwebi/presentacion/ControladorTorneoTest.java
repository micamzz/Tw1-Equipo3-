package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTorneo;
import com.tallerwebi.dominio.TipoTorneo;
import com.tallerwebi.dominio.Torneo;
import com.tallerwebi.dominio.excepcion.NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException;
import com.tallerwebi.dominio.excepcion.NombreDeTorneoEnBlancoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorTorneoTest {

    private ControladorTorneo controladorTorneo;
    private ServicioTorneo servicioTorneoMock;

    @BeforeEach
    public void inicializacion() {
        servicioTorneoMock = mock(ServicioTorneo.class);
        controladorTorneo = new ControladorTorneo(servicioTorneoMock);
    }

    @Test
    public void verTorneoActualRetornaLaVistaTorneo() {
        /* Preparación */
        Torneo torneo = new Torneo();
        when(servicioTorneoMock.obtenerTorneoActual(TipoTorneo.VIRTUAL)).thenReturn(torneo);

        /* Ejecución */
        ModelAndView mav = controladorTorneo.verTorneoActual();

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("torneo"));
        assertThat(mav.getModel().get("torneo"), equalTo(torneo));
    }

    @Test
    public void crearTorneoRetornaLaVistaCrearTorneo() {
        /* Ejecución */
        ModelAndView mav = controladorTorneo.crearTorneo();

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("crear-torneo"));
        assertThat(mav.getModel().get("torneo"), instanceOf(Torneo.class));
    }

    @Test
    public void guardarTorneoCorrectamenteRedirigeAlListado() {
        /* Preparación */
        Torneo torneo = new Torneo();

        /* Ejecución */
        ModelAndView mav = controladorTorneo.guardarTorneo(torneo);

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/torneos?success=Torneo creado correctamente"));
    }

    @Test
    public void guardarTorneoConNombreVacioRetornaLaVistaCrearTorneo() throws Exception {
        /* Preparación */
        Torneo torneo = new Torneo();

        doThrow(new NombreDeTorneoEnBlancoException("Nombre inválido")).when(servicioTorneoMock).crearTorneo(torneo);

        /* Ejecución */
        ModelAndView mav = controladorTorneo.guardarTorneo(torneo);

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("crear-torneo"));
        assertThat(mav.getModel().get("error"), equalTo("Nombre inválido"));
    }

    @Test
    public void eliminarTorneoCorrectamenteRedirigeAlListado() throws Exception {
        /* Preparación */
        Long id = 1L;

        /* Ejecución */
        ModelAndView mav = controladorTorneo.eliminarTorneo(id);

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/torneos?success=Torneo eliminado correctamente"));
    }

    @Test
    public void eliminarTorneoConEquiposAsociadosRetornaError() throws Exception {
        /* Preparación */
        Long id = 1L;

        doThrow(new NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException("No se puede eliminar")).when(servicioTorneoMock).eliminarTorneo(id);

        /* Ejecución */
        ModelAndView mav = controladorTorneo.eliminarTorneo(id);

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/torneos?error=No se puede eliminar"));
    }

    @Test
    public void verTodosLosTorneosRetornaLaVistaAdminTorneos() {
        /* Preparación */
        List<Torneo> torneos = new ArrayList<>();
        torneos.add(new Torneo());
        torneos.add(new Torneo());

        when(servicioTorneoMock.obtenerTodosLosTorneos()).thenReturn(torneos);

        /* Ejecución */
        ModelAndView mav = controladorTorneo.verTodosLosTorneos();

        /* Validación */
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-torneos"));
        assertThat(mav.getModel().get("torneos"), equalTo(torneos));
    }
}