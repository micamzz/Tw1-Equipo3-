package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.TorneoNoEncontradoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


}
