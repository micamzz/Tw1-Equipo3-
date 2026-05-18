package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.ServicioEquipo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEquipo {

    private final ServicioEquipo servicioEquipo;

    public ControladorEquipo(ServicioEquipo servicioEquipo) {
        this.servicioEquipo = servicioEquipo;
    }

    // Muestra la vista html para crear un equipo
    @RequestMapping("/crear-equipo")
    public ModelAndView crearNombreDelEquipo() {
        ModelMap modelo = new ModelMap();

        /* Agregar verificacion que si el usuario ya tiene un equipo creado lo redirija a
        ver equipo */

        // Se crea un objeto vacio que luego se va a rellenar con los datos del form.
        modelo.put("equipo", new Equipo());

        return new ModelAndView("crear-equipo", modelo);
    }


    // Guarda el nombre del equipo en el servicio
    @RequestMapping(value = "/guardar-nombre-equipo", method = RequestMethod.POST)
    public ModelAndView guardarNombreEquipo(@ModelAttribute Equipo equipoIngresado) {

        // guarda el nombre que se ingreso en el input.
        Equipo equipoGuardado = servicioEquipo.guardarEquipo(equipoIngresado);

        // redirige al segundo paso que es seleccionar jugadores
        return new ModelAndView("redirect:/seleccionar-jugadores?id=" + equipoGuardado.getId());
    }


    // Vista con el form para que seleccione a los jugadores.
    // El request recibe por parametro el id que es obtenido del metodo anterior
    @RequestMapping("/seleccionar-jugadores")
    public ModelAndView seleccionarJugadores(@RequestParam Long id) {

        ModelMap modelo = new ModelMap();

        Equipo equipo = servicioEquipo.buscarEquipoPorId(id);

        if (equipo == null) {
            return new ModelAndView("redirect:/crear-equipo");
        }
        modelo.put("equipo", equipo);


        return new ModelAndView("seleccionar-jugadores", modelo);
    }

    // Guarda la seleccion de jugadores elegidos.
    @RequestMapping(value = "/guardar-equipo", method = RequestMethod.POST)
    public ModelAndView guardarEquipoCompleto() {

        ModelMap modelo = new ModelMap();

   /* ACA LLAMAR A SERVICIOJUGADOR ?
        PASAR AL MODELO KEY -LISTA BASES/ALEROS/PIVOTS PARA PODER LLAMARLOS DEL HTML
        Y MOSTARLOS EN UN SELECT, NECESITO USAR  SERVICIOJUGADOR Q IMPLEMENTA LOS EMTODOS DE FILTRADO
        dEBE LLAMAR A UN METODO BUSCARBASES(),ETC
        */


        // retornar index o ver equipo ?
        return new ModelAndView("index", modelo);

    }


}
