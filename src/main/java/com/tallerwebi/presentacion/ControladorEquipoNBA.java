package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoSinNombreException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ControladorEquipoNBA {

    private final ServicioEquipoNBA servicioEquipoNBA;
    private final ServicioEquipoNBAJugador servicioEquipoNBAJugador;

    @Autowired
    public ControladorEquipoNBA(ServicioEquipoNBA servicioEquipoNBA, ServicioEquipoNBAJugador servicioEquipoNBAJugador) {
        this.servicioEquipoNBA = servicioEquipoNBA;
        this.servicioEquipoNBAJugador = servicioEquipoNBAJugador;
    }

    @RequestMapping("/altaEquipoNBA")
    public ModelAndView irAlFormularioEquipoNBA() {
        ModelMap modelo = new ModelMap();
        EquipoNBA equipoNba = new EquipoNBA();

        modelo.put("equipoNBA", new EquipoNBA());

        return new ModelAndView("admin-alta-nombreEquipoNBA", modelo);
    }


    @RequestMapping("/guardarEquipoNBA")
    public ModelAndView guardarEquipoNba(@ModelAttribute("equipoNBA") EquipoNBA equipoNBA) {

        try {
            if (equipoNBA.getNombre() == null || equipoNBA.getNombre().isBlank()) {
                throw new EquipoSinNombreException("El nombre del equipo no puede estar vacío");
            }

            servicioEquipoNBA.guardarEquipoNBA(equipoNBA);
            Long idEquipoIngresado = equipoNBA.getId();

            return new ModelAndView("redirect:/admin/asignar-jugadoresNBA?id=" + idEquipoIngresado);

        } catch (EquipoSinNombreException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("equipoNBA", new EquipoNBA());
            modelo.put("error", e.getMessage());
            return new ModelAndView("admin-alta-nombreEquipoNBA", modelo);
        }
    }

    // Vista con el form para que seleccione a los jugadores.
    // El request recibe por parámetro el id que es obtenido del método anterior
    @RequestMapping("/asignar-jugadoresNBA")
    public ModelAndView asignarJugadores(@RequestParam Long id, @RequestParam(required = false) String nombre,
                                         @RequestParam(required = false) Posicion posicion,
                                         @RequestParam(required = false) String error) {

        try {
            ModelMap modelo = new ModelMap();

            EquipoNBA equipoNBA = servicioEquipoNBA.buscarEquipoPorId(id);

            List<Jugador> listadoJugadores = servicioEquipoNBAJugador.obtenerJugadoresDisponibles();
            List<Jugador> listadoJugadoresDelEquipo = servicioEquipoNBAJugador.obtenerJugadoresDelEquipoPorId(id);

            modelo.put("equipo", equipoNBA);
            modelo.put("jugadores", listadoJugadores);
            modelo.put("plantel", listadoJugadoresDelEquipo);

            if (error != null) {
                modelo.put("error", error);
            }
            return new ModelAndView("admin-asignar-jugadores", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/admin");
        }
    }


    @RequestMapping(value = "/agregarJugadorAEquipoNBA", method = RequestMethod.POST)
    public ModelAndView agregarJugadorAlEquipo(@RequestParam Long idEquipo, @RequestParam Long idJugador) throws elJugadorYaExisteEnElEquipoException, EquipoNoEncontradoException {

        servicioEquipoNBA.agregarJugadorAlEquipo(idEquipo, idJugador);
        return new ModelAndView("redirect:/admin/asignar-jugadoresNBA?id=" + idEquipo);

    }


    @RequestMapping("/listadoEquiposNBA")
    public ModelAndView verListadoDeEquiposNBA() {
        ModelMap modelo = new ModelMap();

        List<EquipoNBA> equipos = servicioEquipoNBA.obtenerTodosLosEquipos();

        modelo.put("equipos", equipos);

        return new ModelAndView("admin-listado-equiposNBA", modelo);
    }


    @RequestMapping("/detalleEquipoNBA")
    public ModelAndView verDetalleEquipoNBA(@RequestParam Long id) {
        try {
            ModelMap modelo = new ModelMap();
            EquipoNBA equipo = servicioEquipoNBA.buscarEquipoPorId(id);
            List<Jugador> plantel = servicioEquipoNBAJugador.obtenerJugadoresDelEquipoPorId(id);
            modelo.put("equipo", equipo);
            modelo.put("plantel", plantel);
            return new ModelAndView("admin-detalle-equipoNBA", modelo);
        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/admin/listadoEquiposNBA");
        }
    }
}

