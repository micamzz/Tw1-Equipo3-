package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.ServicioEquipo;
import com.tallerwebi.dominio.ServicioMercado;
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
    private Equipo equipoMock;


    // Inicializacion de variables
    @BeforeEach
    public void inicializacion() {
        servicioEquipoMock = mock(ServicioEquipo.class);
        servicioJugadorMock = mock(ServicioMercado.class);
        controladorEquipo = new ControladorEquipo(servicioEquipoMock, servicioJugadorMock);
        equipoMock = mock(Equipo.class);
    }


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
        when(equipoMock.getId()).thenReturn(1L);
        when(servicioEquipoMock.guardarEquipo(equipoMock)).thenReturn(equipoMock);

        // Ejecucion

        ModelAndView mav = controladorEquipo.guardarNombreEquipo(equipoMock);

        // Verificacion
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/seleccionar-jugadores?id=1"));
    }

    @Test
    public void alRedirigirLaVistaConIdValidoDevuelveLaNuevaVista() {

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
}



