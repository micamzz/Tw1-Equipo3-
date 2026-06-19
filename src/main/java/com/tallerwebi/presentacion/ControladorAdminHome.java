package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class ControladorAdminHome {

    @RequestMapping("/home")
    public ModelAndView iraHome() {
        return new ModelAndView("admin-home");
    }
}
