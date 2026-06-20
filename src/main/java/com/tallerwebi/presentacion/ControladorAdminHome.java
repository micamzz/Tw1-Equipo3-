package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class ControladorAdminHome {

    @RequestMapping("/home")
    public ModelAndView iraHome(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);

        return new ModelAndView("admin-home", modelo);
    }
}
