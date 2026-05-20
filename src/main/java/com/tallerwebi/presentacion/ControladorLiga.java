package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Liga;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorLiga {

    @RequestMapping("/liga")
    public ModelAndView irALiga() {
        ModelAndView modelAndView = new ModelAndView("liga");
        modelAndView.addObject("liga", Liga.crearLigaDemo());
        return modelAndView;
    }
}
