package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.ServicioPartidoNBA;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.ServicioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Controller
public class ControladorFixture {

    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioEquipoNBA servicioEquipoNBA;
    private final ServicioEquipoNBAJugador servicioEquipoNBAJugador;
    private final ServicioTorneo servicioTorneo;
    private final ServicioFormacion servicioFormacion;

    @Autowired
    public ControladorFixture(ServicioPartidoNBA servicioPartidoNBA,
                                 ServicioEquipoNBA servicioEquipoNBA,
                                 ServicioEquipoNBAJugador servicioEquipoNBAJugador,
                                 ServicioTorneo servicioTorneo, ServicioFormacion servicioFormacion) {
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioEquipoNBA = servicioEquipoNBA;
        this.servicioEquipoNBAJugador = servicioEquipoNBAJugador;
        this.servicioTorneo = servicioTorneo;
        this.servicioFormacion = servicioFormacion;
    }

    @RequestMapping("/partidos")
    public ModelAndView verPartidos() {
        ModelMap modelo = new ModelMap();
        modelo.put("partidosActivos", servicioPartidoNBA.obtenerPartidosActivos());
        modelo.put("partidosProgramados", servicioPartidoNBA.obtenerPartidosProgramados());
        modelo.put("partidosFinalizados", servicioPartidoNBA.obtenerPartidosFinalizados());
        try {
            Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
            modelo.put("torneoActual", torneoActual);
        } catch (Exception e) {
        }
        return new ModelAndView("partidos", modelo);
    }

    @RequestMapping("/admin/partidos")
    public ModelAndView adminPartidos() {
        ModelMap modelo = new ModelMap();
        modelo.put("partidosActivos", servicioPartidoNBA.obtenerPartidosActivos());
        modelo.put("partidosProgramados", servicioPartidoNBA.obtenerPartidosProgramados());
        modelo.put("partidosFinalizados", servicioPartidoNBA.obtenerPartidosFinalizados());
        try {
            Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
            modelo.put("torneoActual", torneoActual);
        } catch (Exception e) {
            // si no hay torneo, no se muestra
        }
        return new ModelAndView("admin-partidos", modelo);
    }

    @RequestMapping(value = "/admin/agregarPartido", method = RequestMethod.GET)
    public ModelAndView formularioAgregarPartido() {
        ModelMap modelo = new ModelMap();
        List<EquipoNBA> equipos = servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor();
        modelo.put("equipos", equipos);
        return new ModelAndView("admin-agregarPartido", modelo);
    }

    @RequestMapping(value = "/admin/agregarPartido", method = RequestMethod.POST)
    public ModelAndView guardarPartido(@RequestParam Long idLocal,
                                       @RequestParam Long idVisitante,
                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime horaInicio) {
        try {
            EquipoNBA local = servicioEquipoNBA.buscarEquipoPorId(idLocal);
            EquipoNBA visitante = servicioEquipoNBA.buscarEquipoPorId(idVisitante);
            Torneo torneo = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);

            if (horaInicio == null) {
                horaInicio = LocalDateTime.now();
            }
            servicioPartidoNBA.agregarPartido(local, visitante, horaInicio, torneo);
            return new ModelAndView("redirect:/admin/partidos");

        } catch (EquiposIgualesException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("error", e.getMessage());
            modelo.put("equipos", servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor());
            return new ModelAndView("admin-agregarPartido", modelo);
        } catch (FechaAnteriorInvalidaException | FechaDuplicadaException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("error", e.getMessage());
            modelo.put("equipos", servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor());
            return new ModelAndView("admin-agregarPartido", modelo);
        } catch (EquipoNoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/admin/partido")
    public ModelAndView adminPartido(@RequestParam Long idPartido) {
        ModelMap modelo = new ModelMap();
        PartidoNBA partido = servicioPartidoNBA.obtenerPorId(idPartido);
        List<CronologiaNBA> cronologia = servicioPartidoNBA.obtenerCronologiaDePartido(idPartido);
        ScorePartido scoreLocal = servicioPartidoNBA.obtenerScoreLocal(idPartido);
        ScorePartido scoreVisitante = servicioPartidoNBA.obtenerScoreVisitante(idPartido);

        List<Jugador> jugadoresLocal = servicioEquipoNBAJugador
                .obtenerJugadoresDelEquipoEnTorneo(partido.getEquipoLocal().getId(), partido.getTorneo().getId());
        List<Jugador> jugadoresVisitante = servicioEquipoNBAJugador
                .obtenerJugadoresDelEquipoEnTorneo(partido.getEquipoVisitante().getId(), partido.getTorneo().getId());

        modelo.put("partido", partido);
        modelo.put("cronologia", cronologia);
        modelo.put("scoreLocal", scoreLocal);
        modelo.put("scoreVisitante", scoreVisitante);
        modelo.put("jugadoresLocal", jugadoresLocal);
        modelo.put("jugadoresVisitante", jugadoresVisitante);

        return new ModelAndView("admin-partido", modelo);
    }
//Formacion SOIFI
@RequestMapping(value = "/admin/partido/{idPartido}/formacion/{idEquipo}", method = RequestMethod.GET)
public  ModelAndView verFormacionEquipo(@PathVariable Long idPartido, @PathVariable Long idEquipo) {
        ModelMap modelo = new ModelMap();
try {
    PartidoNBA partido = servicioPartidoNBA.obtenerPorId(idPartido);
    EquipoNBA equipo = servicioEquipoNBA.buscarEquipoPorId(idEquipo);

    List<Jugador> jugadoresDelEquipo = servicioEquipoNBAJugador.obtenerJugadoresDelEquipoEnTorneo(idEquipo, partido.getTorneo().getId());

    List<FormacionPartido> formacionActual = servicioFormacion.obtenerFormacionPorEquipo(idPartido, idEquipo);

    modelo.put("partido", partido);
    modelo.put("equipo", equipo);
    modelo.put("jugadores", jugadoresDelEquipo);
    modelo.put("formacionActual", formacionActual);
    return new ModelAndView("admin-formacion-equipo", modelo);
}
catch (EquipoNoEncontradoException e) {
    return new ModelAndView("redirect:/admin/partidos");
}
}

@RequestMapping(value="/admin/partido/{idPartido}/formacion/{idEquipo}/confirmar", method = RequestMethod.POST)
public ModelAndView confirmarFormacionEquipo(@PathVariable Long idPartido, @PathVariable Long idEquipo, @RequestParam(required = false) List<Long> idsJugadores) {
        if(idsJugadores != null){
            for(Long idJugador : idsJugadores){
                servicioFormacion.agregarJugador(idPartido, idEquipo, idJugador);
            }
        }
        return new ModelAndView("redirect:/admin/partido" +  idPartido + "/formacion/" +  idEquipo);
}
@RequestMapping(value = "/admin/eliminarJugadorFormacion", method = RequestMethod.POST)
public ModelAndView eliminarJugadorFormacion(@RequestParam Long idFormacion, @RequestParam Long idPartido, @RequestParam Long idEquipo) {
        servicioFormacion.quitarJugador(idFormacion);
        return new ModelAndView("redirect:/admin/partido/" +  idPartido + "/formacion" +  idEquipo);
}
    // FIN

    @RequestMapping(value = "/admin/iniciarPartido", method = RequestMethod.POST)
    public ModelAndView iniciarPartido(@RequestParam Long idPartido) {
        try {
            servicioPartidoNBA.iniciarPartido(idPartido);
        } catch (EquipoJugandoException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("error", e.getMessage());
            modelo.put("partidosActivos", servicioPartidoNBA.obtenerPartidosActivos());
            modelo.put("partidosProgramados", servicioPartidoNBA.obtenerPartidosProgramados());
            modelo.put("partidosFinalizados", servicioPartidoNBA.obtenerPartidosFinalizados());
            try {
                Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
                modelo.put("torneoActual", torneoActual);
            } catch (Exception ignored) {}
            return new ModelAndView("admin-partidos", modelo);
        }
        return new ModelAndView("redirect:/admin/partidos");
    }

    @RequestMapping(value = "/admin/reprogramarPartido", method = RequestMethod.POST)
    public ModelAndView reprogramarPartido(@RequestParam Long idPartido,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime nuevaHoraInicio) {
        try {
            servicioPartidoNBA.reprogramarPartido(idPartido, nuevaHoraInicio);
        } catch (FechaAnteriorInvalidaException | FechaDuplicadaException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("error", e.getMessage());
            modelo.put("partidosActivos", servicioPartidoNBA.obtenerPartidosActivos());
            modelo.put("partidosProgramados", servicioPartidoNBA.obtenerPartidosProgramados());
            modelo.put("partidosFinalizados", servicioPartidoNBA.obtenerPartidosFinalizados());
            try {
                Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
                modelo.put("torneoActual", torneoActual);
            } catch (Exception ignored) {}
            return new ModelAndView("admin-partidos", modelo);
        }
        return new ModelAndView("redirect:/admin/partidos");
    }

    @RequestMapping(value = "/admin/cancelarPartido", method = RequestMethod.POST)
    public ModelAndView cancelarPartido(@RequestParam Long idPartido) {
        servicioPartidoNBA.cancelarPartido(idPartido);
        return new ModelAndView("redirect:/admin/partidos");
    }

    @RequestMapping(value = "/admin/finalizarPartidoRapido", method = RequestMethod.POST)
    public ModelAndView finalizarPartidoRapido(@RequestParam Long idPartido,
                                               @RequestParam Integer minutoFin) {
        servicioPartidoNBA.finalizarPartido(idPartido, minutoFin);
        return new ModelAndView("redirect:/admin/partidos");
    }

}
