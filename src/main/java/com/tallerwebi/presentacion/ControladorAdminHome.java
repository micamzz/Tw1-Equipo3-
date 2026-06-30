package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class ControladorAdminHome {

    private final ServicioLogin servicioLogin;

    public ControladorAdminHome(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/home")
    public ModelAndView iraHome() {
        return new ModelAndView("admin-home");
    }

    @RequestMapping(path = "/nuevo-admin", method = RequestMethod.GET)
    public ModelAndView nuevoAdmin() {

        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());

        return new ModelAndView("admin-nuevo-admin", model);
    }

    @RequestMapping(path = "/crear-admin", method = RequestMethod.POST)
    public ModelAndView crearAdmin(@ModelAttribute("usuario") Usuario usuario) {

        ModelMap model = new ModelMap();

        try {
            servicioLogin.registrarAdmin(usuario);
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
            return new ModelAndView("admin-nuevo-admin", model);
        }

        return new ModelAndView("redirect:/admin/home");
    }
}