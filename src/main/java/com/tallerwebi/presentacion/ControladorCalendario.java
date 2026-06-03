package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Calendario;
import com.tallerwebi.dominio.RendimientoJugador;
import com.tallerwebi.dominio.ServicioCalendario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorCalendario{

    private final ServicioCalendario servicioCalendario;

    @Autowired
    public ControladorCalendario(ServicioCalendario servicioCalendario) {
        this.servicioCalendario = servicioCalendario;
    }

    @RequestMapping("/calendario")
    public ModelAndView irACalendarioNBA() {
        ModelMap modelo = new ModelMap();

        Calendario calendario = servicioCalendario.obtenerCalendario();
        List<RendimientoJugador> topJugadores = servicioCalendario.Top6Jugadores();


        modelo.put("calendario", calendario);
        modelo.put("topJugadores", topJugadores);

        return new ModelAndView("calendario", modelo);
    }
}