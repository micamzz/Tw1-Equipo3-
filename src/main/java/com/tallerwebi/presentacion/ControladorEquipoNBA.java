package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.ServicioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.ServicioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.JugadorYaExisteEnLaTemporadaException;
import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.tallerwebi.dominio.temporada.ServicioTemporada;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ControladorEquipoNBA {

    private final ServicioEquipoNBA servicioEquipoNBA;
    private final ServicioEquipoNBAJugador servicioEquipoNBAJugador;
    private final ServicioTemporada servicioTemporada;

    @Autowired
    public ControladorEquipoNBA(ServicioEquipoNBA servicioEquipoNBA, ServicioEquipoNBAJugador servicioEquipoNBAJugador, ServicioTemporada servicioTemporada) {
        this.servicioEquipoNBA = servicioEquipoNBA;
        this.servicioEquipoNBAJugador = servicioEquipoNBAJugador;
        this.servicioTemporada = servicioTemporada;
    }


    @RequestMapping("/altaEquipoNBA")
    public ModelAndView irAlFormularioEquipoNBA() {
        ModelMap modelo = new ModelMap();

        modelo.put("equipoNBA", new EquipoNBA());

        try {
            modelo.put("temporadaActual", servicioTemporada.obtenerTemporadaActual());
        } catch (TemporadaActualNoEncontradaException e) {
            modelo.put("error", e.getMessage());
        }

        return new ModelAndView("admin-alta-nombreEquipoNBA", modelo);
    }


    @RequestMapping("/guardarEquipoNBA")
    public ModelAndView guardarEquipoNba(@ModelAttribute("equipoNBA") EquipoNBA equipoNBA) {

        if (equipoNBA.getNombre() == null || equipoNBA.getNombre().isBlank()) {
            ModelMap modelo = new ModelMap();
            modelo.put("equipoNBA", new EquipoNBA());
            try {
                modelo.put("temporadaActual", servicioTemporada.obtenerTemporadaActual());
            } catch (TemporadaActualNoEncontradaException e) {
            }
            modelo.put("error", "El nombre del equipo no puede estar vacío");
            return new ModelAndView("admin-alta-nombreEquipoNBA", modelo);
        }

        servicioEquipoNBA.guardarEquipoNBA(equipoNBA);
        Long idEquipoIngresado = equipoNBA.getId();

        return new ModelAndView("redirect:/admin/asignar-jugadoresNBA?id=" + idEquipoIngresado);

    }

    // Vista con el form para que seleccione a los jugadores.
    // El request recibe por parámetro el id que es obtenido del método anterior
    @RequestMapping("/asignar-jugadoresNBA")
    public ModelAndView asignarJugadores(@RequestParam Long id,
                                         @RequestParam(required = false) String nombre,
                                         @RequestParam(required = false) String posicion,
                                         @RequestParam(required = false) String error) {

        try {
            ModelMap modelo = new ModelMap();

            EquipoNBA equipoNBA = servicioEquipoNBA.buscarEquipoPorId(id);

            /* IGUAL QUE EL MERCADO(JUGADORES) CONTROLADOR*/
            if (nombre != null && nombre.isEmpty()) {
                nombre = null;
            }

            if (posicion != null && posicion.isEmpty()) {
                posicion = null;
            }

            Posicion posicionEnum = null;

            if (posicion != null) {
                posicionEnum = Posicion.valueOf(posicion);
            }

            List<Jugador> listadoJugadores = servicioEquipoNBAJugador.obtenerJugadoresFiltrados(posicionEnum, nombre);

            List<Jugador> plantel = servicioEquipoNBAJugador.obtenerJugadoresDelEquipoPorId(id);

            modelo.put("equipo", equipoNBA);
            modelo.put("jugadores", listadoJugadores);
            modelo.put("plantel", plantel);

            modelo.put("nombre", nombre);
            modelo.put("posicion", posicion);

            if (error != null) {
                modelo.put("error", error);
            }

            return new ModelAndView("admin-asignar-jugadores", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/admin");
        }
    }


    @RequestMapping(value = "/agregarJugadorAEquipoNBA", method = RequestMethod.POST)
    public ModelAndView agregarJugadorAlEquipo(@RequestParam Long idEquipo, @RequestParam Long idJugador) {

        try {
            servicioEquipoNBA.agregarJugadorAlEquipo(idEquipo, idJugador);

        } catch (EquipoNoEncontradoException |
                 elJugadorYaExisteEnElEquipoException |
                 JugadorYaExisteEnLaTemporadaException e) {

            return new ModelAndView(
                    "redirect:/admin/asignar-jugadoresNBA?id=" + idEquipo
                            + "&error=" + e.getMessage()
            );
        }
        return new ModelAndView("redirect:/admin/asignar-jugadoresNBA?id=" + idEquipo);
    }


    @RequestMapping("/listadoEquiposNBA")
    public ModelAndView verListadoDeEquiposNBA() {
        ModelMap modelo = new ModelMap();

        List<EquipoNBA> equipos = servicioEquipoNBA.obtenerTodosLosEquiposOrdenadosDeMenorAMayor();

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

    @RequestMapping(value = "/quitarJugadorAEquipoNBA", method = RequestMethod.POST)
    public ModelAndView quitarJugadorAEquipoNBA(
            @RequestParam Long idEquipo,
            @RequestParam Long idJugador) throws EquipoNoEncontradoException {

        servicioEquipoNBA.eliminarJugadorDelEquipo(idEquipo, idJugador);


        return new ModelAndView("redirect:/admin/asignar-jugadoresNBA?id=" + idEquipo);
    }


    @RequestMapping(value = "/eliminarEquipoNBA", method = RequestMethod.POST)
    public ModelAndView eliminarEquipoNBA(@RequestParam Long idEquipo) {

        try {
            servicioEquipoNBA.eliminarEquipoNBA(idEquipo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/admin/listadoEquiposNBA?error=" + e.getMessage());
        }

        return new ModelAndView("redirect:/admin/listadoEquiposNBA");
    }
}



