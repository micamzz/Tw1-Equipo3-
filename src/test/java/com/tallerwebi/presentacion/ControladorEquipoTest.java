package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.ServicioEquipo;
import com.tallerwebi.dominio.ServicioMercado;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoSinNombreException;
import com.tallerwebi.dominio.excepcion.EquipoTitularSinCompletarException;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorEquipoTest {

    private ControladorEquipo controladorEquipo;
    private ServicioEquipo servicioEquipoMock;
    private ServicioMercado servicioJugadorMock;
    private Equipo equipoMock;


    // Inicializacion de variables
    @BeforeEach
    public void inicializacion() {
        servicioEquipoMock = mock(ServicioEquipo.class);
        servicioJugadorMock = mock(ServicioMercado.class);
        controladorEquipo = new ControladorEquipo(servicioEquipoMock, servicioJugadorMock);
        equipoMock = mock(Equipo.class);
    }

    /*
    TEST
    1- CREAR EQUIPO RETORNA VISTA DEL HTML CON EL INPUT
    2- AL PONER EL NOMBRE DEL EQUIPO TE REDIRIGE A SELECCIONAR JUGADORES
    3- SI SE INTENTA APRETAR EL BOTON DE "SUBMIT" Y EL ESTA VACIO LANZA EXCEPCION
    4- SI SE CREO EL NOMBRE DEL EQUIPO CORRECTAMENTE(ID VALIDO) TE LLEVA A SELECCIONAR JUGADORES.
    5- SI NO CREO EL NOMBRE DEL EQUIPO  TE LLEVA A LA VISTA CREAR-EQUIPO(DONDE VA EL NOMBRE)
    6- VER EQUIPO TE LLEVA  A VISTA VER EQUIPO
    7- SELECCIONADO LOS JUGADORES, BOTON CREAR-EQUIPO TE LLEVA A UNA VISTA CON EL DETALLE DE TU EQUIPO.
    8 -SI SELECCIONA SOLO LOS TITULARES TE LLEVA A VER EQUIPO

     */

    @Test
    public void irACrearEquipoRetornaUnaVistaParaIngresarElNombreDelEquipo() {
        // Ejecucion
        ModelAndView mav = controladorEquipo.crearNombreDelEquipo();

        //Se encuentra el input para poner ingresar el nombre del equipo?
        assertThat(mav.getViewName(), equalToIgnoringCase("crear-equipo"));

        // Verifica si se crea el objeto vacio
        assertThat(mav.getModel().get("equipo"), instanceOf(Equipo.class));
    }


    @Test
    public void alApretarElBotonDeCrearNombreDebeRedirigirASeleccionarJugadores() throws EquipoSinNombreException {
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
        assertThat(mav.getModel().get("error").toString(), equalToIgnoringCase("No se puede crear equipo con nombre vacio")
        );
    }

    @Test
    public void alRedirigirLaVistaConIdValidoDevuelveLaVistaSeleccionarJugadores() throws EquipoNoEncontradoException {

        // preparacion
        Long idEquipo = 1L;
        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);

        //ejecucion
        ModelAndView mav = controladorEquipo.seleccionarJugadores(idEquipo);

        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("seleccionar-jugadores"));
    }


    @Test
    public void alRedirigirLaVistaConIdInvalidoRedireccionaACrearEquipo() throws EquipoNoEncontradoException {

        // preparacion
        Long idEquipo = 1L;
        when(servicioEquipoMock.buscarEquipoPorId(1L)).thenThrow(EquipoNoEncontradoException.class);

        //ejecucion
        ModelAndView mav = controladorEquipo.seleccionarJugadores(idEquipo);

        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/crear-equipo"));
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

    @Test
    public void guardarEquipoCompletoRedirigeAVerEquipo() throws EquipoTitularSinCompletarException, EquipoNoEncontradoException, PresupuestoInsuficienteException {

        //preparacion
        Long idEquipo = 1L;
        List<Long> idsJugadores = new ArrayList<>();

        idsJugadores.add(1L);
        idsJugadores.add(2L);
        idsJugadores.add(3L);
        idsJugadores.add(4L);
        idsJugadores.add(5L);
        /* SUPLENTES*/
        idsJugadores.add(6L);
        idsJugadores.add(7L);
        idsJugadores.add(8L);
        idsJugadores.add(9L);
        idsJugadores.add(10L);

        //ejecuion
        ModelAndView mav = controladorEquipo.guardarEquipoCompleto(idEquipo, idsJugadores);

        //verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/ver-equipo?id=1"));
    }


    @Test
    public void guardarEquipoSoloConTitularesDebeRedigirirAVerEquipo() throws EquipoTitularSinCompletarException, EquipoNoEncontradoException, PresupuestoInsuficienteException {
        //preparacion
        Long idEquipo = 1L;
        List<Long> idsJugadores = new ArrayList<>();

        idsJugadores.add(1L);
        idsJugadores.add(2L);
        idsJugadores.add(3L);
        idsJugadores.add(4L);
        idsJugadores.add(5L);
        /* SUPLENTES*/
        idsJugadores.add(null);
        idsJugadores.add(null);
        idsJugadores.add(null);
        idsJugadores.add(null);
        idsJugadores.add(null);

        //ejecucion
        ModelAndView mav = controladorEquipo.guardarEquipoCompleto(idEquipo, idsJugadores);

        // verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/ver-equipo?id=1"));
    }
}



