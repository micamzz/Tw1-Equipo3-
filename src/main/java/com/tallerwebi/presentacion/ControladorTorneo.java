package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTorneo;
import com.tallerwebi.dominio.TorneoVirtual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

        modelo.put("torneo", servicioTorneo.obtenerTorneoActual());

        return new ModelAndView("torneo", modelo);
    }

    @GetMapping("/admin/torneo/crear")
    public ModelAndView crearTorneo() {

        ModelMap modelo = new ModelMap();

        modelo.put("torneo", new TorneoVirtual());

        return new ModelAndView("crear-torneo", modelo);

    }

    @PostMapping("/admin/torneo/guardar")
    public ModelAndView guardarTorneo(
            @ModelAttribute("torneo") TorneoVirtual torneo) {

        System.out.println("ENTRO AL POST");
        System.out.println("Nombre: " + torneo.getNombreTorneo());
        System.out.println("Inicio: " + torneo.getFechaInicio());
        System.out.println("Fin: " + torneo.getFechaFin());

        servicioTorneo.crearTorneo(torneo);
        return new ModelAndView("redirect:/torneo");
    }

}
