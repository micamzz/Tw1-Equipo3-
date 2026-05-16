package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.ServicioMercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMercado {
        private ServicioMercado servicioMercado;

    @Autowired
    public ControladorMercado(ServicioMercado servicioMercado) {
        this.servicioMercado = servicioMercado;
    }

    @RequestMapping(path="/mercado", method = RequestMethod.GET)
    public ModelAndView verMercado(@RequestParam (required=false) Posicion posicion,
                                   @RequestParam(required = false) String nombre) {
        ModelMap modelo = new ModelMap();
        modelo.put("jugadores", servicioMercado.obtenerJugadores(posicion,nombre));
        modelo.put("posicion", posicion);
        modelo.put("nombre", nombre);
        return new ModelAndView("mercado", modelo);
    }
}
