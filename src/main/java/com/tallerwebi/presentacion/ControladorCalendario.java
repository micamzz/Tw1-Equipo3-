package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.FuturosPartidos;
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

        FuturosPartidos futurosPartidos = servicioCalendario.obtenerFuturosPartidos();
        List<RendimientoJugador> topJugadores = servicioCalendario.Top6Jugadores();

        modelo.put("futurosPartidos", futurosPartidos);
        modelo.put("topJugadores", topJugadores);

        return new ModelAndView("calendario", modelo);
    }
}
