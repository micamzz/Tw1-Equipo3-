package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorEquipoNBATest {

    private ControladorEquipoNBA controladorEquipoNBA;
    private ServicioEquipoNBA servicioEquipoNBAMock;
    private ServicioEquipoNBAJugador servicioEquipoNBAJugadorMock;
    private ServicioTemporada servicioTemporadaMock;
    private EquipoNBA equipoNBAmock;


    // Inicializacion de variables
    @BeforeEach
    public void inicializacion() {
        servicioEquipoNBAMock = mock(ServicioEquipoNBA.class);
        servicioEquipoNBAJugadorMock = mock(ServicioEquipoNBAJugador.class);
        equipoNBAmock = mock(EquipoNBA.class);
        servicioTemporadaMock = mock(ServicioTemporada.class);
        controladorEquipoNBA = new ControladorEquipoNBA(servicioEquipoNBAMock, servicioEquipoNBAJugadorMock, servicioTemporadaMock);

    }


    @Test
    public void irACrearEquipoRetornaUnaVistaParaIngresarElNombreDelEquipo() {
        // Ejecucion
        ModelAndView mav = controladorEquipoNBA.irAlFormularioEquipoNBA();

        //Se encuentra el input para poner ingresar el nombre del equipo?
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-alta-nombreEquipoNBA"));

        // Verifica si se crea el objeto vacio
        assertThat(mav.getModel().get("equipoNBA"), instanceOf(EquipoNBA.class));
    }

    @Test
    public void alCrearUnEquipoConNombreVacioTeRedirigeAlAltaEquipo() {

        // Preparacion
        EquipoNBA equipoNBA1 = new EquipoNBA();

        // Ejecucion
        ModelAndView mav = controladorEquipoNBA.guardarEquipoNba(equipoNBA1);
        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-alta-nombreEquipoNBA")
        );
    }


    @Test
    public void alCrearUnEquipoConNombreVacioTeMuestraUnMsjDeError() {

        // Preparacion
        EquipoNBA equipoNBA1 = new EquipoNBA();
        equipoNBA1.setId(1L);

        // Ejecucion
        ModelAndView mav = controladorEquipoNBA.guardarEquipoNba(equipoNBA1);
        // Verificacion
        assertThat(mav.getModel().get("error").toString(),
                IsEqualIgnoringCase.equalToIgnoringCase("El nombre del equipo no puede estar vacío")
        );
    }


    @Test
    public void alGuardarEquipoTeRedirigeAAsignarJugadores() {

        // Preparacion
        EquipoNBA equipoNBA1 = new EquipoNBA();
        equipoNBA1.setNombre("PLM2026");
        equipoNBA1.setId(1L);

        // Ejecucion
        ModelAndView mav = controladorEquipoNBA.guardarEquipoNba(equipoNBA1);
        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/asignar-jugadoresNBA?id=1")
        );

    }

    @Test
    public void asignarJugadoresDeberiaRetornarLaVistaConLosDatosDelEquipo() throws EquipoNoEncontradoException {

        // Preparación
        Long idEquipo = 1L;

        List<Jugador> jugadoresDisponibles = new ArrayList<>();
        List<Jugador> jugadoresEquipo = new ArrayList<>();

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoNBAmock);

        when(servicioEquipoNBAJugadorMock.obtenerJugadoresDisponibles()).thenReturn(jugadoresDisponibles);

        when(servicioEquipoNBAJugadorMock.obtenerJugadoresDelEquipoPorId(idEquipo)).thenReturn(jugadoresEquipo);

        // Ejecución
        ModelAndView mav = controladorEquipoNBA.asignarJugadores(idEquipo, null);

        // Verificación

        assertThat(mav.getModel().get("equipo"), equalTo(equipoNBAmock));
        assertThat(mav.getModel().get("jugadores"), equalTo(jugadoresDisponibles));
        assertThat(mav.getModel().get("plantel"), equalTo(jugadoresEquipo));

        assertThat(mav.getViewName(), equalToIgnoringCase("admin-asignar-jugadores"));
    }

    @Test
    public void siElEquipoNoExisteDebeRedirigirAlAdmin() throws EquipoNoEncontradoException {

        // Preparación
        Long idEquipo = 1L;

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenThrow(new EquipoNoEncontradoException());

        // Ejecución
        ModelAndView mav = controladorEquipoNBA.asignarJugadores(idEquipo, null);

        // Verificación
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin"));
    }

    @Test
    public void agregarJugadoresAlEquipoNBATeRedirigeALaMismaPagina() throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException {

        Long idEquipo = 1L;
        Long idJugador = 2L;

        ModelAndView mav = controladorEquipoNBA.agregarJugadorAlEquipo(idEquipo, idJugador);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/asignar-jugadoresNBA?id=1"));
    }

    @Test
    public void queSePuedaVerElListadoDeEquiposNBA() {

        // Preparación
        List<EquipoNBA> equipos = new ArrayList<>();

        EquipoNBA equipo1 = new EquipoNBA();
        EquipoNBA equipo2 = new EquipoNBA();

        equipos.add(equipo1);
        equipos.add(equipo2);

        when(servicioEquipoNBAMock.obtenerTodosLosEquiposOrdenadosDeMenorAMayor()).thenReturn(equipos);

        // Ejecución
        ModelAndView mav = controladorEquipoNBA.verListadoDeEquiposNBA();

        // Verificación
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-listado-equiposNBA"));
        assertThat(mav.getModel().get("equipos"), equalTo(equipos));
    }

    @Test
    public void queSePuedaVerElDetalleDeUnEquipoNBA()
            throws EquipoNoEncontradoException {

        Long idEquipo = 1L;
        EquipoNBA equipo = new EquipoNBA();

        List<Jugador> plantel = new ArrayList<>();
        plantel.add(new Jugador());
        plantel.add(new Jugador());

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);

        when(servicioEquipoNBAJugadorMock.obtenerJugadoresDelEquipoPorId(idEquipo)).thenReturn(plantel);

        ModelAndView mav = controladorEquipoNBA.verDetalleEquipoNBA(idEquipo);

        assertThat(mav.getViewName(), equalToIgnoringCase("admin-detalle-equipoNBA"));
        assertThat(mav.getModel().get("equipo"), equalTo(equipo));
        assertThat(mav.getModel().get("plantel"), equalTo(plantel));
    }

    @Test
    public void siElEquipoNoExisteAlVerDetalleDebeRedirigirAlListado() throws EquipoNoEncontradoException {
        Long idEquipo = 99L;

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenThrow(new EquipoNoEncontradoException());

        ModelAndView mav = controladorEquipoNBA.verDetalleEquipoNBA(idEquipo);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/listadoEquiposNBA"));
    }


}

