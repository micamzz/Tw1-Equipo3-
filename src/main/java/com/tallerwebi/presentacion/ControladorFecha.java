package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.enums.EstadoFecha;
import com.tallerwebi.dominio.excepcion.FechaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.TorneoNoEncontradoException;
import com.tallerwebi.dominio.fecha.Fecha;
import com.tallerwebi.dominio.fecha.ServicioFecha;
import com.tallerwebi.dominio.partidoNBA.ServicioPartidoNBA;
import com.tallerwebi.dominio.torneo.ServicioTorneo;
import com.tallerwebi.dominio.torneo.Torneo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorFecha {

    private final ServicioTorneo servicioTorneo;
    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioFecha servicioFecha;

    public ControladorFecha(ServicioTorneo servicioTorneo, ServicioPartidoNBA servicioPartidoNBA, ServicioFecha servicioFecha) {
        this.servicioTorneo = servicioTorneo;
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioFecha = servicioFecha;
    }

    @GetMapping("/admin/agregarFecha")
    public ModelAndView irAAgregarFecha() {
        ModelMap modelo = new ModelMap();

        // 1. Enviamos todos los torneos disponibles para el select
        List<Torneo> torneos = servicioTorneo.obtenerTodosLosTorneos();
        modelo.put("torneos", torneos);

        // 2. Enviamos los valores del Enum EstadoFecha (ej: PROGRAMADA, EN_CURSO, TERMINADA)
        modelo.put("estados", EstadoFecha.values());

        return new ModelAndView("agregar-fecha", modelo);
    }

    @PostMapping("/admin/agregarFecha")
    public ModelAndView guardarNuevaFecha(
            @RequestParam("numeroFecha") Integer numeroFecha,
            @RequestParam("idTorneo") Long idTorneo,
            @RequestParam("estadoFecha") EstadoFecha estadoFecha) {
        try {
            servicioFecha.registrarFecha(idTorneo, numeroFecha, estadoFecha);

            return new ModelAndView("redirect:/partidos");

        } catch (TorneoNoEncontradoException e) {

            ModelMap modelo = new ModelMap();
            modelo.put("error", e.getMessage());

            modelo.put("torneos", servicioTorneo.obtenerTodosLosTorneos());
            modelo.put("estados", EstadoFecha.values());

            return new ModelAndView("agregar-fecha", modelo);
        }
    }

    @RequestMapping(path = "/admin/fechas", method = RequestMethod.GET)
    public ModelAndView irAGestionarFechas() {

        ModelMap modelo = new ModelMap();

        List<Fecha> fechas = servicioFecha.obtenerTodasLasFechas();

        modelo.put("fechas", fechas);

        return new ModelAndView("admin-fechas", modelo);
    }

    @RequestMapping(path = "/admin/cambiarEstadoFecha", method = RequestMethod.POST)
    public ModelAndView cambiarEstadoFecha(
            @RequestParam("idFecha") Long idFecha,
            @RequestParam("estado") EstadoFecha estado) {

        try {

            Fecha fecha = servicioFecha.obtenerFechaPorId(idFecha);

            servicioFecha.actualizarFecha(idFecha, fecha.getNumeroDeFecha(), estado);

            return new ModelAndView("redirect:/admin/fechas");

        } catch (FechaNoEncontradaException e) {

            return new ModelAndView(
                    "redirect:/admin/fechas?error=" + e.getMessage()
            );
        }
    }

}
