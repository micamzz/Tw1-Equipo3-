package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EquipoNBA;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class ControladorEquipoNBA {


    @RequestMapping("/altaEquipoNBA")
    public ModelAndView irAlFormularioEquipoNBA() {
        ModelMap modelo = new ModelMap();
        EquipoNBA equipoNba = new EquipoNBA();

        modelo.put("equipoNBA", equipoNba);

        return new ModelAndView("admin-alta-equipoNBA", modelo);
    }


    @RequestMapping("/guardar-equipoNBA")
    public ModelAndView guardarEquipoNba(@ModelAttribute("equipoNBA") EquipoNBA equipoNBA) {

//        servicioEquipoNBA.guardar(equipoNBA);


        return new ModelAndView("admin-alta-equipoNBA");
    }


}
