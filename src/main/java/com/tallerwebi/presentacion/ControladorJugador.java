package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.jugador.Jugador;
import com.tallerwebi.dominio.jugador.RendimientoJugador;
import com.tallerwebi.dominio.mercado.ServicioMercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import com.tallerwebi.dominio.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorJugador {
    private final ServicioMercado servicioMercado;

    @Autowired
    public ControladorJugador(ServicioMercado servicioMercado) {
        this.servicioMercado = servicioMercado;
    }

    @GetMapping("/jugador/{id}")
    public ModelAndView verDetalleJugador(@PathVariable Long id, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        Jugador jugador = servicioMercado.buscarJugadorPorId(id);
        RendimientoJugador rendimiento = servicioMercado.obtenerRendimiento(id);

        if (rendimiento == null) {
            rendimiento = new RendimientoJugador();
            rendimiento.setPuntos(0);
            rendimiento.setRebotes(0);
            rendimiento.setAsistencias(0);
            rendimiento.setRobos(0);
            rendimiento.setBloqueos(0);
            rendimiento.setPerdidas(0);
        }

        double puntaje = servicioMercado.calcularPuntajeJugador(rendimiento);
        List<RendimientoJugador> rendimientosPorPartido = servicioMercado.obtenerRendimientosPorPartido(id);

        modelo.put("usuario", usuario);
        modelo.put("jugador", jugador);
        modelo.put("rendimiento", rendimiento);
        modelo.put("puntaje", puntaje);
        modelo.put("rendimientosPorPartido", rendimientosPorPartido);
        return new ModelAndView("detalle-jugador", modelo);
    }
}