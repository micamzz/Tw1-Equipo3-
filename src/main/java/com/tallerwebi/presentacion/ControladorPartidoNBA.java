package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
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

@Controller
public class ControladorPartidoNBA {

    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioEquipoNBA servicioEquipoNBA;
    private final ServicioEquipoNBAJugador servicioEquipoNBAJugador;

    @Autowired
    public ControladorPartidoNBA(ServicioPartidoNBA servicioPartidoNBA,
                                 ServicioEquipoNBA servicioEquipoNBA,
                                 ServicioEquipoNBAJugador servicioEquipoNBAJugador) {
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioEquipoNBA = servicioEquipoNBA;
        this.servicioEquipoNBAJugador = servicioEquipoNBAJugador;
    }

    @RequestMapping("/temporada")
    public ModelAndView verTemporada() {
        ModelMap modelo = new ModelMap();
        modelo.put("partidosActivos", servicioPartidoNBA.obtenerPartidosActivosConScore());
        modelo.put("partidosFinalizados", servicioPartidoNBA.obtenerPartidosFinalizadosConScore());
        return new ModelAndView("temporada", modelo);
    }

    @RequestMapping("/partido/cronologia")
    public ModelAndView verCronologia(@RequestParam Long idPartido) {
        ModelMap modelo = new ModelMap();
        PartidoNBA partido = servicioPartidoNBA.obtenerPorId(idPartido);
        List<CronologiaNBA> cronologia = servicioPartidoNBA.obtenerCronologiaDePartido(idPartido);
        ScorePartido scoreLocal = servicioPartidoNBA.obtenerScoreLocal(idPartido);
        ScorePartido scoreVisitante = servicioPartidoNBA.obtenerScoreVisitante(idPartido);

        modelo.put("partido", partido);
        modelo.put("cronologia", cronologia);
        modelo.put("scoreLocal", scoreLocal);
        modelo.put("scoreVisitante", scoreVisitante);
        return new ModelAndView("partido-cronologia", modelo);
    }



    @RequestMapping(value = "/admin/agregarCronologiaPuntaje", method = RequestMethod.POST)
    public ModelAndView agregarCronologiaPuntaje(@RequestParam Long idPartido,
                                                 @RequestParam Integer minuto,
                                                 @RequestParam String descripcion,
                                                 @RequestParam Integer puntos,
                                                 @RequestParam Long idEquipo) {
        try {
            servicioPartidoNBA.agregarCronologiaPuntaje(idPartido, minuto, descripcion, puntos, idEquipo);
        } catch (PartidoFinalizadoException e) {

        }
        return new ModelAndView("redirect:/admin/partido?idPartido=" + idPartido);
    }

    @RequestMapping(value = "/admin/agregarCronologiaPlantel", method = RequestMethod.POST)
    public ModelAndView agregarCronologiaPlantel(@RequestParam Long idPartido,
                                                 @RequestParam Integer minuto,
                                                 @RequestParam Long idJugadorSale,
                                                 @RequestParam Long idJugadorEntra) {
        try {
            servicioPartidoNBA.agregarCronologiaPlantel(idPartido, minuto, idJugadorSale, idJugadorEntra);
        } catch (PartidoFinalizadoException e) {

        }
        return new ModelAndView("redirect:/admin/partido?idPartido=" + idPartido);
    }

    @RequestMapping(value = "/admin/finalizarPartido", method = RequestMethod.POST)
    public ModelAndView finalizarPartido(@RequestParam Long idPartido,
                                         @RequestParam Integer minutoFin) {
        try {
            servicioPartidoNBA.finalizarPartido(idPartido, minutoFin);
        } catch (PartidoFinalizadoException e) {
        }
        return new ModelAndView("redirect:/admin/partidos");
    }
}
