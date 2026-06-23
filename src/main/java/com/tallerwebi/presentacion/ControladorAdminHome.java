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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class ControladorAdminHome {

    private final ServicioLogin servicioLogin;

    public ControladorAdminHome(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }

    private boolean noEstaLogueado(HttpServletRequest request) {
        return request.getSession().getAttribute("usuario") == null;
    }

    @RequestMapping("/home")
    public ModelAndView iraHome(HttpServletRequest request) {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        return new ModelAndView("admin-home", modelo);
    }

    @RequestMapping(path = "/nuevo-admin", method = RequestMethod.GET)
    public ModelAndView nuevoAdmin(HttpServletRequest request) {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("admin-nuevo-admin", model);
    }

    @RequestMapping(path = "/crear-admin", method = RequestMethod.POST)
    public ModelAndView crearAdmin(HttpServletRequest request,
                                   @ModelAttribute("usuario") Usuario usuario) {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

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