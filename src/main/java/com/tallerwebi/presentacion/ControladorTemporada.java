package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.ServicioTorneo;
import com.tallerwebi.dominio.Torneo;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.ServicioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.temporada.ServicioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ControladorTemporada {

    private final ServicioTemporada servicioTemporada;
    private final ServicioTorneo servicioTorneo;
    private final ServicioEquipoNBA servicioEquipoNBA;
    private final ServicioEquipoNBAJugador servicioEquipoNBAJugador;

    public ControladorTemporada(ServicioTemporada servicioTemporada,
                                ServicioTorneo servicioTorneo,
                                ServicioEquipoNBA servicioEquipoNBA,
                                ServicioEquipoNBAJugador servicioEquipoNBAJugador) {
        this.servicioTemporada = servicioTemporada;
        this.servicioTorneo = servicioTorneo;
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

    @RequestMapping("/historialTemporada/{idTemporada}")
    public ModelAndView verTorneosDeTemporada(@PathVariable Long idTemporada) {

        ModelMap modelo = new ModelMap();

        Temporada temporada = servicioTemporada.obtenerTemporadaPorId(idTemporada);
        List<Torneo> torneos = servicioTorneo.obtenerTorneosPorTemporada(idTemporada);

        modelo.put("temporada", temporada);
        modelo.put("torneos", torneos);

        return new ModelAndView("admin-historial-detalle-temporada", modelo);
    }

    @RequestMapping("/historialTorneo/{idTorneo}")
    public ModelAndView verEquiposDeTorneo(@PathVariable Long idTorneo) {

        ModelMap modelo = new ModelMap();

        Torneo torneo = servicioTorneo.buscarTorneoPorId(idTorneo);
        List<EquipoNBA> equipos =
                servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor();

        modelo.put("torneo", torneo);
        modelo.put("equipos", equipos);

        return new ModelAndView("admin-historial-detalle-torneo", modelo);
    }

    @RequestMapping("/historialEquipoEnTorneo/{idTorneo}/{idEquipo}")
    public ModelAndView verJugadoresDeEquipoEnTorneo(@PathVariable Long idEquipo,
                                                     @PathVariable Long idTorneo) {

        try {
            ModelMap modelo = new ModelMap();

            EquipoNBA equipo = servicioEquipoNBA.buscarEquipoPorId(idEquipo);
            List<Jugador> jugadores =
                    servicioEquipoNBAJugador.obtenerJugadoresDelEquipoEnTorneo(idEquipo, idTorneo);

            modelo.put("equipo", equipo);
            modelo.put("jugadores", jugadores);
            modelo.put("idTorneo", idTorneo);

            return new ModelAndView("admin-historial-equipo-torneo", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/admin/historialTemporadas");
        }
    }
}