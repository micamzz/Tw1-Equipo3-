package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Liga;
import com.tallerwebi.dominio.ServicioLiga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorLiga {

    private ServicioLiga servicioLiga;

    public ControladorLiga() {
        // Uso de la demo de liga en caso de no tener la capa de persistencia de datos
        // implementada (como en este caso)
        this.servicioLiga = new ServicioLiga() {
            @Override
            public Liga obtenerLiga() {
                return Liga.crearLigaDemo();
            }
        };
    }

    @Autowired
    public ControladorLiga(ServicioLiga servicioLiga) {
        this.servicioLiga = servicioLiga;
    }

    @RequestMapping("/liga")
    public ModelAndView irALiga() {
        ModelAndView modelAndView = new ModelAndView("liga");
        modelAndView.addObject("liga", servicioLiga.obtenerLiga());
        return modelAndView;
    }
}
