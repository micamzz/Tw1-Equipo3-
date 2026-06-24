
package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.JugadorNoConvocadoException;
import com.tallerwebi.dominio.excepcion.JugadorNoEncontradoException;
import com.tallerwebi.dominio.excepcion.MomentoPartidoInvalidoException;
import com.tallerwebi.dominio.excepcion.PartidoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEventoPartido {

    private final ServicioEventoPartido servicioEventoPartido;
    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioFormacionSabri servicioFormacion;

    @Autowired
    public ControladorEventoPartido(
            ServicioEventoPartido servicioEventoPartido,
            ServicioPartidoNBA servicioPartidoNBA,
            ServicioFormacionSabri servicioFormacion) {

        this.servicioEventoPartido = servicioEventoPartido;
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioFormacion = servicioFormacion;
    }

    @GetMapping("/admin/partido/{idPartido}/eventos")
    public ModelAndView irARegistrarEventos(
            @PathVariable Long idPartido,
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String success) {

        ModelMap modelo = new ModelMap();

        modelo.put("partido", servicioPartidoNBA.obtenerPorId(idPartido));

        modelo.put("evento", new EventoPartido());

        modelo.put("jugadores",
                servicioFormacion.obtenerFormacionPorPartido(idPartido));

        modelo.put("tipos", TipoEstadistica.values());

        modelo.put("scoreLocal", servicioPartidoNBA.obtenerScoreLocal(idPartido));

        modelo.put("scoreVisitante", servicioPartidoNBA.obtenerScoreVisitante(idPartido));

        modelo.put("formacionLocal", servicioFormacion.obtenerFormacionPorPartidoYEquipo(idPartido, servicioPartidoNBA.obtenerPorId(idPartido).getEquipoLocal().getId()));

        modelo.put("formacionVisitante", servicioFormacion.obtenerFormacionPorPartidoYEquipo(idPartido, servicioPartidoNBA.obtenerPorId(idPartido).getEquipoVisitante().getId()));

        modelo.put("eventos", servicioEventoPartido.buscarEventosPorPartido(idPartido));

        modelo.put("error", error);

        modelo.put("success", success);

        return new ModelAndView(
                "admin-estadisticas",
                modelo
        );
    }

    @PostMapping("/partidos/{id}/eventos")
    public ModelAndView registrarEvento(
            @PathVariable Long id,
            @ModelAttribute("evento") EventoPartido evento) {

        try {

            servicioEventoPartido.registrarEvento(
                    id,
                    evento.getJugador().getId(),
                    evento.getMomentoPartido(),
                    evento.getTipoEstadistica()
            );

            return new ModelAndView(
                    "redirect:/partidos/" + id +
                            "/eventos?success=Evento registrado correctamente"
            );

        } catch (PartidoNoEncontradoException
                 | JugadorNoEncontradoException
                 | JugadorNoConvocadoException
                 | MomentoPartidoInvalidoException e) {

            return new ModelAndView(
                    "redirect:/partidos/" + id +
                            "/eventos?error=" + e.getMessage()
            );
        }
    }
}
