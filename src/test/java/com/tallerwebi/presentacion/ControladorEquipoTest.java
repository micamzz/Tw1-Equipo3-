package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.ServicioEquipo;
import com.tallerwebi.dominio.ServicioEquipoJugador;
import com.tallerwebi.dominio.ServicioMercado;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoSinNombreException;
import com.tallerwebi.dominio.excepcion.TorneoVirtualActualNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorEquipoTest {

    private ControladorEquipo controladorEquipo;
    private ServicioEquipo servicioEquipoMock;
    private ServicioMercado servicioJugadorMock;
    private ServicioEquipoJugador servicioEquipoJugadorMock;
    private Equipo equipoMock;


    // Inicializacion de variables
    @BeforeEach
    public void inicializacion() {
        servicioEquipoMock = mock(ServicioEquipo.class);
        servicioJugadorMock = mock(ServicioMercado.class);
        servicioEquipoJugadorMock = mock(ServicioEquipoJugador.class);

        controladorEquipo = new ControladorEquipo(servicioEquipoMock, servicioJugadorMock, servicioEquipoJugadorMock);
        equipoMock = mock(Equipo.class);
    }

    /*
    TEST
    1- CREAR EQUIPO RETORNA VISTA DEL HTML CON EL INPUT
    2- AL PONER EL NOMBRE DEL EQUIPO TE REDIRIGE A SELECCIONAR JUGADORES
    3- SI SE INTENTA APRETAR EL BOTON DE "SUBMIT" Y EL ESTA VACIO LANZA EXCEPCION
    4- SI SE CREO EL NOMBRE DEL EQUIPO CORRECTAMENTE(ID VALIDO) TE LLEVA A SELECCIONAR JUGADORES.
    5- SI NO CREO EL NOMBRE DEL EQUIPO  TE LLEVA Al HOME
    6- VER EQUIPO TE LLEVA  A VISTA VER EQUIPO

     */

    @Test
    public void irACrearEquipoRetornaUnaVistaParaIngresarElNombreDelEquipo() {
        // Ejecucion
        ModelAndView mav = controladorEquipo.irACrearEquipo();

        //Se encuentra el input para poner ingresar el nombre del equipo?
        assertThat(mav.getViewName(), equalToIgnoringCase("crear-equipo"));

        // Verifica si se crea el objeto vacio
        assertThat(mav.getModel().get("equipo"), instanceOf(Equipo.class));
    }


    @Test
    public void alApretarElBotonDeCrearNombreDebeRedirigirASeleccionarJugadores() throws EquipoSinNombreException, TorneoVirtualActualNoEncontradoException {
        // Preparacion
        when(equipoMock.getNombreEquipo()).thenReturn("PLM");
        when(equipoMock.getId()).thenReturn(1L);
        when(servicioEquipoMock.guardarEquipo(equipoMock)).thenReturn(equipoMock);

        // Ejecucion

        ModelAndView mav = controladorEquipo.guardarNombreEquipo(equipoMock);

        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/seleccionar-jugadores?id=1"));
    }

    @Test
    public void guardarNombreDeEquipoVacioLanzaException() throws EquipoSinNombreException {
        Equipo equipo = mock(Equipo.class);

        when(equipo.getNombreEquipo()).thenReturn("");

        ModelAndView mav = controladorEquipo.guardarNombreEquipo(equipo);

        assertThat(mav.getViewName(), equalToIgnoringCase("crear-equipo"));
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase("El nombre del equipo no puede estar vacío")
        );
    }


    @Test
    public void alRedirigirLaVistaConIdValidoDevuelveLaVistaSeleccionarJugadores() throws EquipoNoEncontradoException {

        // preparacion
        Long idEquipo = 1L;
        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);

        //ejecucion
        ModelAndView mav = controladorEquipo.seleccionarJugadores(idEquipo, null);

        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("seleccionar-jugadores"));
    }


    @Test
    public void alRedirigirLaVistaConIdInvalidoRedireccionaACrearEquipo() throws EquipoNoEncontradoException {

        // preparacion
        Long idEquipo = 1L;
        when(servicioEquipoMock.buscarEquipoPorId(1L)).thenThrow(EquipoNoEncontradoException.class);

        //ejecucion
        ModelAndView mav = controladorEquipo.seleccionarJugadores(idEquipo, null);

        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void verEquipoDebeRetornarLaVistaVerEquipo() throws EquipoNoEncontradoException {
        //preparacion
        Long idEquipo = 1L;
        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);

        //ejecucion
        ModelAndView mav = controladorEquipo.verEquipo(idEquipo);

        //verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("ver-equipo"));
    }


}



