package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.tallerwebi.dominio.Usuario;
import org.springframework.ui.ModelMap;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorRegla {

    @RequestMapping("/reglas")
    public ModelAndView iraReglas(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        return new ModelAndView("reglas", modelo);
    }
}
