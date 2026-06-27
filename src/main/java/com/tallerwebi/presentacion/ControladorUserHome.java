package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.equipo.Equipo;
import com.tallerwebi.dominio.equipo.ServicioEquipo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorUserHome {

    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioTorneo servicioTorneo;
    private final ServicioEquipo servicioEquipo;
    private final ServicioMercado servicioMercado;

    public ControladorUserHome(ServicioPartidoNBA servicioPartidoNBA, ServicioTorneo servicioTorneo, ServicioEquipo servicioEquipo, ServicioMercado servicioMercado) {
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioTorneo = servicioTorneo;
        this.servicioEquipo = servicioEquipo;
        this.servicioMercado = servicioMercado;
    }

    /* HOME USUARIO */
    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView iraHome(HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");


        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Equipo equipo = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());


        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);

        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        modelo.put("equipo", equipo);
        modelo.put("presupuestoInicial", servicioEquipo.obtenerPresupuestoInicial());
        modelo.put("torneoActual", torneoActual);
        modelo.put("proximosPartidos", servicioPartidoNBA.obtenerPartidosProgramados());
        modelo.put("servicioMercado", servicioMercado);

        if (torneoActual != null) {
            List<RendimientoJugador> top3 = servicioMercado.obtenerTopJugadoresPorTorneo(torneoActual.getId(), 3);
            modelo.put("topRendimientos", top3);
        }

        return new ModelAndView("home", modelo);
    }

    @GetMapping("/estadisticas-torneo")
    public ModelAndView verEstadisticasTorneo(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) return new ModelAndView("redirect:/login");

        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
        if (torneoActual == null) return new ModelAndView("redirect:/home");

        List<RendimientoJugador> rendimientos = servicioMercado.obtenerRendimientosPorTorneo(torneoActual.getId());
        rendimientos.sort((a, b) -> Double.compare(servicioMercado.calcularPuntajeJugador(b), servicioMercado.calcularPuntajeJugador(a)));

        List<Torneo> torneosReales = servicioTorneo.obtenerTodosLosTorneos().stream()
                .filter(t -> t.getTipoTorneo() == TipoTorneo.REAL && !t.getId().equals(torneoActual.getId()))
                .collect(Collectors.toList());

        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        modelo.put("torneoActual", torneoActual);
        modelo.put("rendimientos", rendimientos);
        modelo.put("torneosPasados", torneosReales);
        modelo.put("servicioMercado", servicioMercado);
        return new ModelAndView("estadisticas-torneo", modelo);
    }

}
