package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.partidoNBA.PartidoNBA;
import com.tallerwebi.dominio.formacion.ServicioFormacion;
import com.tallerwebi.dominio.partidoNBA.ServicioPartidoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorFormacion {

    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioFormacion servicioFormacion;
    private final ServicioEquipoNBAJugador servicioEquipoNBAJugador;

    @Autowired
    public ControladorFormacion(ServicioPartidoNBA servicioPartidoNBA, ServicioFormacion servicioFormacion, ServicioEquipoNBAJugador servicioEquipoNBAJugador) {
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioFormacion = servicioFormacion;
        this.servicioEquipoNBAJugador = servicioEquipoNBAJugador;
    }

    @GetMapping("/admin/formacion")
    public ModelAndView verFormacion(@RequestParam Long idPartido) {
        ModelMap modelo = new ModelMap();
        PartidoNBA partido = servicioPartidoNBA.obtenerPorId(idPartido);
        modelo.put("partido", partido);

        modelo.put(
                "jugadoresLocalesDisponibles",
                servicioEquipoNBAJugador
                        .obtenerJugadoresDelEquipoEnTorneo(
                                partido.getEquipoLocal().getId(),
                                partido.getTorneo().getId()
                        )
        );

        modelo.put("jugadoresVisitantesDisponibles",
                servicioEquipoNBAJugador
                        .obtenerJugadoresDelEquipoEnTorneo(
                                partido.getEquipoVisitante().getId(),
                                partido.getTorneo().getId()
                        )
        );

        modelo.put("formacionLocal",
                servicioFormacion.obtenerFormacionPorEquipo(
                        idPartido,
                        partido.getEquipoLocal().getId()
                )
        );

        modelo.put(
                "formacionVisitante",
                servicioFormacion.obtenerFormacionPorEquipo(
                        idPartido,
                        partido.getEquipoVisitante().getId()
                )
        );

        return new ModelAndView("admin-formacion", modelo);
    }

    @PostMapping("/admin/formacion/agregar")
    public ModelAndView agregarJugador(
            @RequestParam Long idPartido,
            @RequestParam Long idEquipo,
            @RequestParam Long idJugador) {
        servicioFormacion.agregarJugador(idPartido, idEquipo, idJugador);
        return new ModelAndView(
                "redirect:/admin/formacion?idPartido=" + idPartido);
    }

    @PostMapping("/admin/formacion/eliminar")
    public ModelAndView eliminarJugador(
            @RequestParam Long idFormacion,
            @RequestParam Long idPartido
    ) {
        servicioFormacion.quitarJugador(idFormacion);
        return new ModelAndView(
                "redirect:/admin/formacion?idPartido=" + idPartido);
    }

}
