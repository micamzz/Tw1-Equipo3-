package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.ServicioEquipo;
import com.tallerwebi.dominio.ServicioMercado;
import com.tallerwebi.dominio.excepcion.EquipoSinNombreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    7- G
    7- SELECCIONADO LOS JUGADORES, BOTON CREAR-EQUIPO TE LLEVA A UNA VISTA CON EL DETALLE DE TU EQUIPO.


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
    public void alApretarElBotonDeCrearNombreDebeRedirigirASeleccionarJugadores() {
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
    public void guardarNombreDeEquipoVacioLanzaException() {
        Equipo equipo = mock(Equipo.class);

        when(equipo.getNombreEquipo()).thenReturn("");

        assertThrows(EquipoSinNombreException.class, () -> {
            controladorEquipo.guardarNombreEquipo(equipo);
        });
    }

    @Test
    public void alRedirigirLaVistaConIdValidoDevuelveLaVistaSeleccionarJugadores() {

        // preparacion
        Long idEquipo = 1L;
        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);

        //ejecucion
        ModelAndView mav = controladorEquipo.seleccionarJugadores(idEquipo);

        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("seleccionar-jugadores"));
    }


    @Test
    public void alRedirigirLaVistaConIdInvalidoRedireccionaACrearEquipo() {

        // preparacion
        Long idEquipo = 1L;
        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(null);

        //ejecucion
        ModelAndView mav = controladorEquipo.seleccionarJugadores(idEquipo);

        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/crear-equipo"));
    }

    @Test
    public void verEquipoDebeRetornarLaVistaVerEquipo() {
        Long idEquipo = 1L;
        when(servicioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
        ModelAndView mav = controladorEquipo.verEquipo(idEquipo);
        assertThat(mav.getViewName(), equalToIgnoringCase("ver-equipo"));
    }

    @Test
    public void guardarEquipoCompletoRedirigeAVerEquipo() {
        Long idEquipo = 1L;
        ModelAndView mav = controladorEquipo.guardarEquipoCompleto(idEquipo, 1L,
                2L, 3L, 4L, 5L,
                null, null, null, null, null);

        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/ver-equipo?id=1"));
    }
    

}



