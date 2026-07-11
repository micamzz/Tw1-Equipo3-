package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.RendimientoJugador;
import com.tallerwebi.dominio.ServicioMercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorJugador {
    private final ServicioMercado servicioMercado;

    @Autowired
    public ControladorJugador(ServicioMercado servicioMercado) {
        this.servicioMercado = servicioMercado;
    }

    @GetMapping("/jugador/{id}")
    public ModelAndView verDetalleJugador(@PathVariable Long id) {
        ModelMap modelo = new ModelMap();

        Jugador jugador = servicioMercado.buscarJugadorPorId(id);
        RendimientoJugador rendimiento = servicioMercado.obtenerRendimiento(id);
        double puntaje = servicioMercado.calcularPuntajeJugador(rendimiento);
        List<RendimientoJugador> rendimientosPorPartido = servicioMercado.obtenerRendimientosPorPartido(id);


        modelo.put("jugador", jugador);
        modelo.put("rendimiento", rendimiento);
        modelo.put("puntaje", puntaje);
        modelo.put("rendimientosPorPartido", rendimientosPorPartido);
        return new ModelAndView("detalle-jugador", modelo);
    }
}