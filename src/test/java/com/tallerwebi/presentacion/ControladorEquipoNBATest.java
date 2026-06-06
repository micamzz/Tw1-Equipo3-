package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EquipoNBA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.instanceOf;

public class ControladorEquipoNBATest {

    private ControladorEquipoNBA controladorEquipoNBA;
    private EquipoNBA equipoNBAMock;


    // Inicializacion de variables
    @BeforeEach
    public void inicializacion() {
        controladorEquipoNBA = new ControladorEquipoNBA();
        equipoNBAMock = new EquipoNBA();
    }


    @Test
    public void irACrearEquipoRetornaUnaVistaParaIngresarElNombreDelEquipo() {
        // Ejecucion
        ModelAndView mav = controladorEquipoNBA.irAlFormularioEquipoNBA();

        //Se encuentra el input para poner ingresar el nombre del equipo?
        assertThat(mav.getViewName(), equalToIgnoringCase("admin-alta-equipoNBA"));

        // Verifica si se crea el objeto vacio
        assertThat(mav.getModel().get("equipoNBA"), instanceOf(EquipoNBA.class));
    }

}
