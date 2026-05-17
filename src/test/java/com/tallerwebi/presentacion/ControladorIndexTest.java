package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ControladorIndexTest {

    private ControladorIndex controladorIndex;


    // Inicializacion de variables
    @BeforeEach
    public void inicializacion() {
        controladorIndex = new ControladorIndex();
    }


    @Test
    public void irACrearEquipoRetornaUnaVistaParaIngresarElNombreDelEquipo() {
        // Ejecucion
        ModelAndView mav = controladorIndex.irAlIndex();

        //Ejecucion -- > localhost:8080/spring/index
        assertThat(mav.getViewName(), equalToIgnoringCase("index"));
    }


}
