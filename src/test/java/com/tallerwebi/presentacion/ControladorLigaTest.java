package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Liga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;

public class ControladorLigaTest {

    private ControladorLiga controladorLiga;

    @BeforeEach
    public void inicializacion() {
        controladorLiga = new ControladorLiga();
    }

    @Test
    public void irALigaDeberiaRetornarVistaLigaConUnaLigaCargada() {
        ModelAndView modelAndView = controladorLiga.irALiga();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("liga"));
        assertThat(modelAndView.getModel().get("liga"), instanceOf(Liga.class));

        Liga liga = (Liga) modelAndView.getModel().get("liga");
        assertThat(liga.getHistorialPartidos().size(), greaterThan(0));
        assertThat(liga.getProximosPartidos().size(), greaterThan(0));
    }
}