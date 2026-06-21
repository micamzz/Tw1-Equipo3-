package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPartidoNBA;
import com.tallerwebi.dominio.ServicioTorneo;
import com.tallerwebi.dominio.TipoTorneo;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.equipo.Equipo;
import com.tallerwebi.dominio.equipo.ServicioEquipo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorUserHome {

    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioTorneo servicioTorneo;
    private final ServicioEquipo servicioEquipo;

    public ControladorUserHome(ServicioPartidoNBA servicioPartidoNBA, ServicioTorneo servicioTorneo, ServicioEquipo servicioEquipo) {
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioTorneo = servicioTorneo;
        this.servicioEquipo = servicioEquipo;
    }

    /* HOME USUARIO */
    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView iraHome(HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");


        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Equipo equipo = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());


        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        modelo.put("equipo", equipo);
        modelo.put("presupuestoInicial", servicioEquipo.obtenerPresupuestoInicial());
        modelo.put("torneoActual", servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL));
        modelo.put("proximosPartidos", servicioPartidoNBA.obtenerPartidosProgramados());

        System.out.println("Usuario: " + usuario.getId());
        System.out.println("Equipo encontrado: " + equipo);

        return new ModelAndView("home", modelo);
    }

}
