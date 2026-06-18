package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.ServicioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.temporada.ServicioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ControladorTemporada {

    private final ServicioTemporada servicioTemporada;
    private final ServicioEquipoNBA servicioEquipoNBA;
    private final ServicioEquipoNBAJugador servicioEquipoNBAJugador;

    @Autowired
    public ControladorTemporada(ServicioTemporada servicioTemporada,
                                ServicioEquipoNBA servicioEquipoNBA,
                                ServicioEquipoNBAJugador servicioEquipoNBAJugador) {
        this.servicioTemporada = servicioTemporada;
        this.servicioEquipoNBA = servicioEquipoNBA;
        this.servicioEquipoNBAJugador = servicioEquipoNBAJugador;
    }

    @RequestMapping("/historialTemporadas")
    public ModelAndView verHistorialTemporadas() {
        ModelMap modelo = new ModelMap();
        List<Temporada> temporadas = servicioTemporada.obtenerTodasLasTemporadas();
        modelo.put("temporadas", temporadas);
        return new ModelAndView("admin-historial-temporadas", modelo);
    }

    @RequestMapping("/historialTemporada")
    public ModelAndView verEquiposDeTemporada(@RequestParam Long idTemporada) {
        ModelMap modelo = new ModelMap();
        Temporada temporada = servicioTemporada.obtenerTemporadaPorId(idTemporada);
        List<EquipoNBA> equipos = servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor();
        modelo.put("temporada", temporada);
        modelo.put("equipos", equipos);
        return new ModelAndView("admin-historial-detalle-temporada", modelo);
    }

    @RequestMapping("/historialEquipoEnTemporada")
    public ModelAndView verJugadoresDeEquipoEnTemporada(@RequestParam Long idEquipo, @RequestParam Long idTemporada) {
        try {
            ModelMap modelo = new ModelMap();
            EquipoNBA equipo = servicioEquipoNBA.buscarEquipoPorId(idEquipo);
            List<Jugador> jugadores = servicioEquipoNBAJugador.obtenerJugadoresDelEquipoEnTemporada(idEquipo, idTemporada);
            modelo.put("equipo", equipo);
            modelo.put("jugadores", jugadores);
            modelo.put("idTemporada", idTemporada);
            return new ModelAndView("admin-historial-equipo-temporada", modelo);
        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/admin/historialTemporadas");
        }
    }

    @RequestMapping(value = "/confirmarFinalizarTemporada", method = RequestMethod.GET)
    public ModelAndView confirmarFinalizarTemporada() {
        ModelMap modelo = new ModelMap();
        try {
            Temporada actual = servicioTemporada.obtenerTemporadaActual();
            modelo.put("temporadaActual", actual);
        } catch (TemporadaActualNoEncontradaException e) {
            modelo.put("error", e.getMessage());
        }
        return new ModelAndView("admin-finalizarTemporada", modelo);
    }


    @RequestMapping(value = "/finalizarTemporada", method = RequestMethod.POST)
    public ModelAndView finalizarTemporada(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin) {
        try {
            servicioTemporada.finalizarTemporada(fechaFin);
            return new ModelAndView("redirect:/admin/historialTemporadas");
        } catch (FechaFinAnteriorAInicioException | TemporadaFueraDeRangoException | TemporadaActualNoEncontradaException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("error", e.getMessage());
            try {
                modelo.put("temporadaActual", servicioTemporada.obtenerTemporadaActual());
            } catch (TemporadaActualNoEncontradaException ex) {
                // ya esta en el error
            }
            return new ModelAndView("admin-finalizarTemporada", modelo);
        }
    }
}