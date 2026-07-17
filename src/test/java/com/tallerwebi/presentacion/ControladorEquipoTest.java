//package com.tallerwebi.presentacion;
//
//import com.tallerwebi.dominio.enums.Posicion;
//import com.tallerwebi.dominio.torneo.ServicioTorneo;
//import com.tallerwebi.dominio.usuario.Usuario;
//import com.tallerwebi.dominio.equipo.Equipo;
//import com.tallerwebi.dominio.equipo.ServicioEquipo;
//import com.tallerwebi.dominio.equipoJugador.ServicioEquipoJugador;
//import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
//import com.tallerwebi.dominio.excepcion.TorneoVirtualActualNoEncontradoException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class ControladorEquipoTest {
//
//    private ControladorEquipo controladorEquipo;
//    private ServicioEquipo servicioEquipoMock;
//    private ServicioEquipoJugador servicioEquipoJugadorMock;
//    private ServicioTorneo servicioTorneoMock;
//    private Equipo equipoMock;
//
//    @BeforeEach
//    public void inicializacion() {
//        servicioEquipoMock = mock(ServicioEquipo.class);
//        servicioEquipoJugadorMock = mock(ServicioEquipoJugador.class);
//        servicioTorneoMock = mock(ServicioTorneo.class);
//
//        controladorEquipo = new ControladorEquipo(
//                servicioEquipoMock,
//                servicioEquipoJugadorMock,
//                servicioTorneoMock
//        );
//
//        equipoMock = new Equipo();
//    }
//
//    @Test
//    public void irACrearEquipoRetornaUnaVistaParaIngresarElNombreDelEquipo() {
//
//        /* Ejecución */
//        ModelAndView mav = controladorEquipo.irACrearEquipo();
//
//        /* Validación */
//        assertThat(mav.getViewName(), equalToIgnoringCase("crear-equipo"));
//        assertThat(mav.getModel().get("equipo"), instanceOf(Equipo.class));
//    }
//
//    @Test
//    public void alGuardarNombreDeEquipoConNombreValidoRedirigeASeleccionarJugadores() throws TorneoVirtualActualNoEncontradoException {
//
//        /* Preparación */
//        Usuario usuario = new Usuario();
//        usuario.setId(1L);
//
//        equipoMock.setUsuario(usuario);
//        equipoMock.setNombreEquipo("PLM");
//        equipoMock.setId(1L);
//
//        when(servicioEquipoMock.obtenerEquipoPorIdUsuario(1L)).thenReturn(null);
//        when(servicioEquipoMock.guardarEquipo(equipoMock)).thenReturn(equipoMock);
//
//        /* Ejecución */
//        ModelAndView mav = controladorEquipo.guardarNombreEquipo(equipoMock);
//
//        /* Validación */
//        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/seleccionar-jugadores/1"));
//    }
//
//    @Test
//    public void guardarNombreDeEquipoVacioRetornaLaVistaCrearEquipo() {
//
//        Usuario usuario = new Usuario();
//        usuario.setId(1L);
//
//        Equipo equipo = new Equipo();
//        equipo.setUsuario(usuario);
//        equipo.setNombreEquipo("");
//
//        when(servicioEquipoMock.obtenerEquipoPorIdUsuario(1L)).thenReturn(null);
//
//        /* Ejecución */
//        ModelAndView mav = controladorEquipo.guardarNombreEquipo(equipo);
//
//        /* Validación */
//        assertThat(mav.getViewName(), equalToIgnoringCase("crear-equipo"));
//        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase("El nombre del equipo no puede estar vacio")
//        );
//    }
//
//    @Test
//    public void seleccionarJugadoresConIdValidoRetornaLaVistaSeleccionarJugadores() throws EquipoNoEncontradoException {
//
//        /* Preparación */
//        Long idEquipo = 1L;
//
//        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
//
//        when(servicioEquipoJugadorMock.obtenerJugadoresDisponiblesPorPosicion(idEquipo, Posicion.BASE)).thenReturn(new ArrayList<>());
//
//        when(servicioEquipoJugadorMock.obtenerJugadoresDisponiblesPorPosicion(idEquipo, Posicion.ALERO)).thenReturn(new ArrayList<>());
//
//        when(servicioEquipoJugadorMock.obtenerJugadoresDisponiblesPorPosicion(idEquipo, Posicion.PIVOT)).thenReturn(new ArrayList<>());
//
//        when(servicioEquipoJugadorMock.buscarJugadoresPorEquipoId(idEquipo)).thenReturn(new HashMap<>());
//
//        when(servicioEquipoMock.buscarJugadoresDelEquipo(idEquipo)).thenReturn(new ArrayList<>());
//
//        /* Ejecución */
//        ModelAndView mav = controladorEquipo.seleccionarJugadores(idEquipo, null);
//
//        /* Validación */
//        assertThat(mav.getViewName(), equalToIgnoringCase("seleccionar-jugadores"));
//        assertThat(mav.getModel().get("equipo"), equalTo(equipoMock));
//    }
//
//    @Test
//    public void seleccionarJugadoresConIdInvalidoRedireccionaAlHome() throws EquipoNoEncontradoException {
//
//        /* Preparación */
//        Long idEquipo = 1L;
//
//        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenThrow(new EquipoNoEncontradoException(""));
//
//        /* Ejecución */
//        ModelAndView mav = controladorEquipo.seleccionarJugadores(idEquipo, null);
//
//        /* Validación */
//        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/home"));
//    }
//
//    @Test
//    public void verEquipoDebeRetornarLaVistaVerEquipo() throws EquipoNoEncontradoException {
//
//        /* Preparación */
//        Long idEquipo = 1L;
//
//        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
//
//        when(servicioEquipoJugadorMock.buscarJugadoresPorEquipoId(idEquipo)).thenReturn(new HashMap<>());
//
//        when(servicioEquipoMock.buscarJugadoresDelEquipo(idEquipo)).thenReturn(new ArrayList<>());
//
//        /* Ejecución */
//        ModelAndView mav = controladorEquipo.verEquipo(idEquipo);
//
//        /* Validación */
//        assertThat(mav.getViewName(), equalToIgnoringCase("ver-equipo"));
//        assertThat(mav.getModel().get("equipo"), equalTo(equipoMock));
//    }
//
//    @Test
//    public void verEquipoConIdInvalidoRedireccionaACrearEquipo() throws EquipoNoEncontradoException {
//
//        /* Preparación */
//        Long idEquipo = 1L;
//
//        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenThrow(new EquipoNoEncontradoException(""));
//
//        /* Ejecución */
//        ModelAndView mav = controladorEquipo.verEquipo(idEquipo);
//
//        /* Validación */
//        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/crear-equipo"));
//    }
//}