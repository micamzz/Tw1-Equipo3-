package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTorneo;
import com.tallerwebi.dominio.TipoTorneo;
import com.tallerwebi.dominio.Torneo;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorTorneo {

    private ServicioTorneo servicioTorneo;

    @Autowired
    public ControladorTorneo(ServicioTorneo servicioTorneo) {
        this.servicioTorneo = servicioTorneo;
    }

    @GetMapping("/torneo")
    public ModelAndView verTorneoActual() {
        ModelMap modelo = new ModelMap();

        modelo.put("torneo", servicioTorneo.obtenerTorneoActual(TipoTorneo.VIRTUAL));

        return new ModelAndView("torneo", modelo);
    }

    @GetMapping("/admin/torneo/crear")
    public ModelAndView crearTorneo() {

        ModelMap modelo = new ModelMap();

        modelo.put("torneo", new Torneo());

        modelo.put("tipos", TipoTorneo.values());

        return new ModelAndView("crear-torneo", modelo);

    }

    @PostMapping("/admin/torneo/guardar")
    public ModelAndView guardarTorneo(
            @ModelAttribute("torneo") Torneo torneo) {

        try {
            System.out.println("Nombre: " + torneo.getNombreTorneo());
            System.out.println("Inicio: " + torneo.getFechaInicio());
            System.out.println("Fin: " + torneo.getFechaFin());

            servicioTorneo.crearTorneo(torneo);
            return new ModelAndView("redirect:/admin/torneos?success=Torneo creado correctamente");
        } catch (FechaIncoherenteException | FechasSuperpuestasException | NombreDeTorneoEnBlancoException | TipoDeTorneoEnBlancoException e) {
            ModelMap modelo = new ModelMap();

            modelo.put("torneo", torneo);
            modelo.put("error", e.getMessage());
            modelo.put("tipos", TipoTorneo.values());
            return new ModelAndView("crear-torneo", modelo);
        }
    }

    @PostMapping("/admin/torneo/eliminar")
    public ModelAndView eliminarTorneo(@RequestParam Long id) {
        try {
            servicioTorneo.eliminarTorneo(id);
        } catch (NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException | TorneoNoEncontradoException e) {
            return new ModelAndView(
                    "redirect:/admin/torneos?error=" + e.getMessage()
            );
        }

        return new ModelAndView("redirect:/admin/torneos?success=Torneo eliminado correctamente");
    }

    @GetMapping("/admin/torneos")
    public ModelAndView verTodosLosTorneos() {
        ModelMap modelo = new ModelMap();
        modelo.put("torneos", servicioTorneo.obtenerTodosLosTorneos()
        );
        return new ModelAndView("admin-torneos", modelo);
    }

}
