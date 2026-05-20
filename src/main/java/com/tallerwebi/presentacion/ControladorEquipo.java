package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EquipoSinNombreException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorEquipo {

    private ServicioEquipo servicioEquipo;
    private ServicioMercado servicioJugador;


    public ControladorEquipo(ServicioEquipo servicioEquipo, ServicioMercado servicioJugador) {
        this.servicioEquipo = servicioEquipo;
        this.servicioJugador = servicioJugador;
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

        if (equipoIngresado.getNombreEquipo() == null || equipoIngresado.getNombreEquipo().isBlank()) {
            throw new EquipoSinNombreException("No se puede crear equipo con nombre vacio");
        }
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

        List<Jugador> jugadoresBase = servicioJugador.buscarBase();
        List<Jugador> jugadoresAlero = servicioJugador.buscarAlero();
        List<Jugador> jugadoresPivot = servicioJugador.buscarPivot();

        modelo.put("listadoBases", jugadoresBase);
        modelo.put("listadoAleros", jugadoresAlero);
        modelo.put("listadoPivots", jugadoresPivot);

        return new ModelAndView("seleccionar-jugadores", modelo);
    }

    // Guarda la seleccion de jugadores elegidos.
    @RequestMapping(value = "/guardar-equipo", method = RequestMethod.POST)
    public ModelAndView guardarEquipoCompleto(@RequestParam Long idEquipo,
                                              @RequestParam("idJugador") List<Long> idsJugadores) {


        servicioEquipo.guardarEquipoCompleto(idEquipo, idsJugadores);

        return new ModelAndView("redirect:/ver-equipo?id=" + idEquipo);
    }

    @RequestMapping("/ver-equipo")
    public ModelAndView verEquipo(@RequestParam Long id) {

        ModelMap modelo = new ModelMap();

        Equipo equipo = servicioEquipo.buscarEquipoPorId(id);

        List<EquipoJugador> listadoDeJugadoresAsociadosAlEquipo = servicioEquipo.buscarJugadoresDelEquipo(id);

        modelo.put("equipo", equipo);
        modelo.put("jugadoresEquipo", listadoDeJugadoresAsociadosAlEquipo);

        return new ModelAndView("ver-equipo", modelo);

    }

}
