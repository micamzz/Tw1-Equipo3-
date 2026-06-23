package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.ServicioTorneo;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.ServicioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private EquipoNBA equipoNBAmock;
    private ServicioTorneo servicioTorneoMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;


    @BeforeEach
    public void inicializacion() {
        servicioEquipoNBAMock = mock(ServicioEquipoNBA.class);
        servicioEquipoNBAJugadorMock = mock(ServicioEquipoNBAJugador.class);
        equipoNBAmock = mock(EquipoNBA.class);
        servicioTorneoMock = mock(ServicioTorneo.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        controladorEquipoNBA = new ControladorEquipoNBA(servicioEquipoNBAMock, servicioEquipoNBAJugadorMock, servicioTorneoMock);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("usuario")).thenReturn(new Usuario());
    }


    @Test
    public void irACrearEquipoRetornaUnaVistaParaIngresarElNombreDelEquipo() {
        ModelAndView mav = controladorEquipoNBA.irAlFormularioEquipoNBA(requestMock);

        assertThat(mav.getViewName(), equalToIgnoringCase("admin-alta-nombreEquipoNBA"));
        assertThat(mav.getModel().get("equipoNBA"), instanceOf(EquipoNBA.class));
    }

    @Test
    public void siNoHayUsuarioEnSesionRedirigeAlLogin() {
        when(sessionMock.getAttribute("usuario")).thenReturn(null);

        ModelAndView mav = controladorEquipoNBA.irAlFormularioEquipoNBA(requestMock);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void alCrearUnEquipoConNombreVacioTeRedirigeAlAltaEquipo() {
        EquipoNBA equipoNBA1 = new EquipoNBA();

        ModelAndView mav = controladorEquipoNBA.guardarEquipoNba(requestMock, equipoNBA1);

        assertThat(mav.getViewName(), equalToIgnoringCase("admin-alta-nombreEquipoNBA"));
    }

    @Test
    public void alCrearUnEquipoConNombreVacioTeMuestraUnMsjDeError() {
        EquipoNBA equipoNBA1 = new EquipoNBA();
        equipoNBA1.setId(1L);

        ModelAndView mav = controladorEquipoNBA.guardarEquipoNba(requestMock, equipoNBA1);

        assertThat(mav.getModel().get("error").toString(),
                IsEqualIgnoringCase.equalToIgnoringCase("El nombre del equipo no puede estar vacío"));
    }

    @Test
    public void alGuardarEquipoTeRedirigeAAsignarJugadores() {
        EquipoNBA equipoNBA1 = new EquipoNBA();
        equipoNBA1.setNombre("PLM2026");
        equipoNBA1.setId(1L);

        ModelAndView mav = controladorEquipoNBA.guardarEquipoNba(requestMock, equipoNBA1);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/asignar-jugadoresNBA/1"));
    }

    @Test
    public void asignarJugadoresDeberiaRetornarLaVistaConLosDatosDelEquipo() throws EquipoNoEncontradoException {
        Long idEquipo = 1L;
        List<Jugador> jugadoresDisponibles = new ArrayList<>();
        List<Jugador> jugadoresEquipo = new ArrayList<>();

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoNBAmock);
        when(servicioEquipoNBAJugadorMock.obtenerJugadoresFiltrados(null, null)).thenReturn(jugadoresDisponibles);
        when(servicioEquipoNBAJugadorMock.obtenerJugadoresDelEquipoPorId(idEquipo)).thenReturn(jugadoresEquipo);

        ModelAndView mav = controladorEquipoNBA.asignarJugadores(requestMock, idEquipo, null, null, null);

        assertThat(mav.getViewName(), equalToIgnoringCase("admin-asignar-jugadores"));
        assertThat(mav.getModel().get("equipo"), equalTo(equipoNBAmock));
        assertThat(mav.getModel().get("jugadores"), equalTo(jugadoresDisponibles));
        assertThat(mav.getModel().get("plantel"), equalTo(jugadoresEquipo));
    }

    @Test
    public void siElEquipoNoExisteDebeRedirigirAlAdmin() throws EquipoNoEncontradoException {
        Long idEquipo = 1L;

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo))
                .thenThrow(new EquipoNoEncontradoException("No existe el equipo con id: " + idEquipo));

        ModelAndView mav = controladorEquipoNBA.asignarJugadores(requestMock, idEquipo, null, null, null);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin"));
    }

    @Test
    public void agregarJugadoresAlEquipoNBATeRedirigeALaMismaVista() throws elJugadorYaExisteEnElEquipoException, EquipoNoEncontradoException {
        Long idEquipo = 1L;
        Long idJugador = 2L;

        ModelAndView mav = controladorEquipoNBA.agregarJugadorAlEquipo(requestMock, idEquipo, idJugador);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/asignar-jugadoresNBA/1"));
    }

    @Test
    public void eliminarJugadorDelEquipoNBATeRedirigeAMismaVista() throws EquipoNoEncontradoException {
        Long idEquipo = 1L;
        Long idJugador = 2L;

        ModelAndView mav = controladorEquipoNBA.quitarJugadorAEquipoNBA(requestMock, idEquipo, idJugador);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/asignar-jugadoresNBA/1"));
    }

    @Test
    public void queSePuedaVerElListadoDeEquiposNBA() {
        List<EquipoNBA> equipos = new ArrayList<>();
        equipos.add(new EquipoNBA());
        equipos.add(new EquipoNBA());

        when(servicioEquipoNBAMock.obtenerTodosLosEquiposOrdenadosDeMenorAMayor()).thenReturn(equipos);

        ModelAndView mav = controladorEquipoNBA.verListadoDeEquiposNBA(requestMock, null);

        assertThat(mav.getViewName(), equalToIgnoringCase("admin-listado-equiposNBA"));
        assertThat(mav.getModel().get("equipos"), equalTo(equipos));
    }

    @Test
    public void queSePuedaVerElDetalleDeUnEquipoNBA() throws EquipoNoEncontradoException {
        Long idEquipo = 1L;
        EquipoNBA equipo = new EquipoNBA();
        List<Jugador> plantel = new ArrayList<>();
        plantel.add(new Jugador());
        plantel.add(new Jugador());

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo)).thenReturn(equipo);
        when(servicioEquipoNBAJugadorMock.obtenerJugadoresDelEquipoPorId(idEquipo)).thenReturn(plantel);

        ModelAndView mav = controladorEquipoNBA.verDetalleEquipoNBA(requestMock, idEquipo);

        assertThat(mav.getViewName(), equalToIgnoringCase("admin-detalle-equipoNBA"));
        assertThat(mav.getModel().get("equipo"), equalTo(equipo));
        assertThat(mav.getModel().get("plantel"), equalTo(plantel));
    }

    @Test
    public void siElEquipoNoExisteAlVerDetalleDebeRedirigirAlListado() throws EquipoNoEncontradoException {
        Long idEquipo = 99L;

        when(servicioEquipoNBAMock.buscarEquipoPorId(idEquipo))
                .thenThrow(new EquipoNoEncontradoException("No existe el equipo con id: " + idEquipo));

        ModelAndView mav = controladorEquipoNBA.verDetalleEquipoNBA(requestMock, idEquipo);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/admin/listadoEquiposNBA"));
    }
}