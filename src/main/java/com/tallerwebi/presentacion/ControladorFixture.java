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

    @RequestMapping("/admin/partidos")
    public ModelAndView adminPartidos() {
        ModelMap modelo = new ModelMap();
        modelo.put("partidosActivos", servicioPartidoNBA.obtenerPartidosActivos());
        modelo.put("partidosFinalizados", servicioPartidoNBA.obtenerPartidosFinalizados());
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

        } catch (EquiposIgualesException | PartidoYaActivoException e) {
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

        List<FormacionPartido> titularesLocal = servicioFormacion.obtenerTitulares(idPartido, partido.getEquipoLocal().getId());
        List<FormacionPartido> suplentesLocal = servicioFormacion.obtenerSuplentes(idPartido, partido.getEquipoLocal().getId());
        List<FormacionPartido> titularesVisitantes = servicioFormacion.obtenerTitulares(idPartido, partido.getEquipoVisitante().getId());
        List<FormacionPartido> suplentesVisitantes = servicioFormacion.obtenerSuplentes(idPartido, partido.getEquipoVisitante().getId());

        modelo.put("partido", partido);
        modelo.put("cronologia", cronologia);
        modelo.put("scoreLocal", scoreLocal);
        modelo.put("scoreVisitante", scoreVisitante);
        modelo.put("jugadoresLocal", jugadoresLocal);
        modelo.put("jugadoresVisitante", jugadoresVisitante);

        modelo.put("titularesLocal", titularesLocal);
        modelo.put("suplentesLocal", suplentesLocal);
        modelo.put("titularesVisitantes", titularesVisitantes);
        modelo.put("suplentesVisitante", suplentesVisitantes);
        return new ModelAndView("admin-partido", modelo);
    }

    @RequestMapping(value="/admin/agregarJugadorFormacion", method = RequestMethod.POST)
    public ModelAndView agregarJugadorFormacion(@RequestParam Long idPartido, @RequestParam Long idEquipo, @RequestParam Long idJugador, @RequestParam RolFormacion rol) {
        servicioFormacion.agregarJugador(idPartido, idEquipo, idJugador, rol);
        return new ModelAndView("redirect:/admin/partido?idPartido=" + idPartido);
    }

    @RequestMapping(value = "/admin/eliminarJugadorFormacion", method = RequestMethod.POST)
    public ModelAndView eliminarJugadorFormacion(@RequestParam Long idFormacion, Long idPartido) {
        servicioFormacion.quitarJugador(idFormacion);
        return new ModelAndView("redirect:/admin/partido?idPartido=" + idPartido);
    }

}
