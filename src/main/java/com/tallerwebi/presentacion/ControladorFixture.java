package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.ServicioPartidoNBA;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.ServicioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.dominio.temporada.ServicioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ControladorFixture {

    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioEquipoNBA servicioEquipoNBA;
    private final ServicioEquipoNBAJugador servicioEquipoNBAJugador;
    private final ServicioTemporada servicioTemporada;

    @Autowired
    public ControladorFixture(ServicioPartidoNBA servicioPartidoNBA,
                                 ServicioEquipoNBA servicioEquipoNBA,
                                 ServicioEquipoNBAJugador servicioEquipoNBAJugador,
                                 ServicioTemporada servicioTemporada) {
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioEquipoNBA = servicioEquipoNBA;
        this.servicioEquipoNBAJugador = servicioEquipoNBAJugador;
        this.servicioTemporada = servicioTemporada;
    }

    @RequestMapping("/admin/partidos")
    public ModelAndView adminPartidos() {
        ModelMap modelo = new ModelMap();
        modelo.put("partidosActivos", servicioPartidoNBA.obtenerPartidosActivos());
        modelo.put("partidosFinalizados", servicioPartidoNBA.obtenerPartidosFinalizados());
        return new ModelAndView("admin-partidos", modelo);
    }

    // Formulario para agregar partido
    @RequestMapping(value = "/admin/agregarPartido", method = RequestMethod.GET)
    public ModelAndView formularioAgregarPartido() {
        ModelMap modelo = new ModelMap();
        List<EquipoNBA> equipos = servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor();
        modelo.put("equipos", equipos);
        return new ModelAndView("admin-agregar-partido", modelo);
    }

    // Guardar partido nuevo
    @RequestMapping(value = "/admin/agregarPartido", method = RequestMethod.POST)
    public ModelAndView guardarPartido(@RequestParam Long idLocal,
                                       @RequestParam Long idVisitante) {
        try {
            EquipoNBA local = servicioEquipoNBA.buscarEquipoPorId(idLocal);
            EquipoNBA visitante = servicioEquipoNBA.buscarEquipoPorId(idVisitante);
            Temporada temporada = servicioTemporada.obtenerTemporadaActual();

            // El partido empieza en minuto 0 al crearse
            servicioPartidoNBA.agregarPartido(local, visitante, LocalDateTime.now(), temporada);
            return new ModelAndView("redirect:/admin/partidos");

        } catch (EquiposIgualesException | PartidoYaActivoException | TemporadaActualNoEncontradaException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("error", e.getMessage());
            modelo.put("equipos", servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor());
            return new ModelAndView("admin-agregar-partido", modelo);
        } catch (EquipoNoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    // Panel admin de un partido activo (agregar cronologia, finalizar)
    @RequestMapping("/admin/partido")
    public ModelAndView adminPartido(@RequestParam Long idPartido) {
        ModelMap modelo = new ModelMap();
        PartidoNBA partido = servicioPartidoNBA.obtenerPorId(idPartido);
        List<CronologiaNBA> cronologia = servicioPartidoNBA.obtenerCronologiaDePartido(idPartido);
        ScorePartido scoreLocal = servicioPartidoNBA.obtenerScoreLocal(idPartido);
        ScorePartido scoreVisitante = servicioPartidoNBA.obtenerScoreVisitante(idPartido);

        // Jugadores de cada equipo para el plantel
        List<Jugador> jugadoresLocal = servicioEquipoNBAJugador
                .obtenerJugadoresDelEquipoEnTemporada(partido.getEquipoLocal().getId(), partido.getTemporada().getId());
        List<Jugador> jugadoresVisitante = servicioEquipoNBAJugador
                .obtenerJugadoresDelEquipoEnTemporada(partido.getEquipoVisitante().getId(), partido.getTemporada().getId());

        modelo.put("partido", partido);
        modelo.put("cronologia", cronologia);
        modelo.put("scoreLocal", scoreLocal);
        modelo.put("scoreVisitante", scoreVisitante);
        modelo.put("jugadoresLocal", jugadoresLocal);
        modelo.put("jugadoresVisitante", jugadoresVisitante);
        return new ModelAndView("admin-partido", modelo);
    }
}
