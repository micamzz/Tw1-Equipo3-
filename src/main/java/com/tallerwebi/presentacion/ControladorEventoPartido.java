
package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEventoPartido {

    private final ServicioEventoPartido servicioEventoPartido;
    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioFormacion servicioFormacion;

    @Autowired
    public ControladorEventoPartido(
            ServicioEventoPartido servicioEventoPartido,
            ServicioPartidoNBA servicioPartidoNBA,
            ServicioFormacion servicioFormacion) {

        this.servicioEventoPartido = servicioEventoPartido;
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioFormacion = servicioFormacion;
    }

    @GetMapping("/admin/partido/{idPartido}/eventos")
    public ModelAndView irARegistrarEventos(
            @PathVariable Long idPartido) {

        ModelMap modelo = new ModelMap();

        PartidoNBA partido = servicioPartidoNBA.obtenerPorId(idPartido);

        modelo.put("partido", partido);

        modelo.put("evento", new EventoPartido());

        modelo.put("jugadores",
                servicioFormacion.obtenerFormacion(idPartido));

        modelo.put("tipos", TipoEstadistica.values());

        modelo.put("scoreLocal", servicioPartidoNBA.obtenerScoreLocal(idPartido));

        modelo.put("scoreVisitante", servicioPartidoNBA.obtenerScoreVisitante(idPartido));

        modelo.put("formacionLocal", servicioFormacion.obtenerFormacionPorEquipo(idPartido, servicioPartidoNBA.obtenerPorId(idPartido).getEquipoLocal().getId()));

        modelo.put("formacionVisitante", servicioFormacion.obtenerFormacionPorEquipo(idPartido, servicioPartidoNBA.obtenerPorId(idPartido).getEquipoVisitante().getId()));

        modelo.put("eventos", servicioEventoPartido.buscarEventosPorPartido(idPartido));



        return new ModelAndView(
                "admin-estadisticas",
                modelo
        );
    }

    @PostMapping("/admin/partido/{idPartido}/eventos")
    public ModelAndView registrarEvento(
            @PathVariable Long idPartido,
            @ModelAttribute("evento") EventoPartido evento) {

        try {

            servicioEventoPartido.registrarEvento(
                    idPartido,
                    evento.getJugador().getId(),
                    evento.getMomentoPartido(),
                    evento.getTipoEstadistica()
            );

            return new ModelAndView(
                    "redirect:/admin/partido/" + idPartido +
                            "/eventos"
            );

        } catch (PartidoNoEncontradoException | JugadorNoEncontradoException | JugadorNoConvocadoException |
                 MomentoPartidoInvalidoException | PartidoNoEnCursoException e) {

            return new ModelAndView(
                    "redirect:/admin/partido/" + idPartido +
                            "/eventos"
            );
        }
    }
}
