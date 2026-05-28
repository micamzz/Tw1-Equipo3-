package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
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

    private final ServicioEquipo servicioEquipo;
    private final ServicioMercado servicioJugador;
    private final ServicioEquipoJugador servicioEquipoJugador;

    public ControladorEquipo(ServicioEquipo servicioEquipo, ServicioMercado servicioJugador, ServicioEquipoJugador servicioEquipoJugador) {
        this.servicioEquipo = servicioEquipo;
        this.servicioJugador = servicioJugador;
        this.servicioEquipoJugador = servicioEquipoJugador;
    }


    // Muestra la vista html para crear un equipo
    @RequestMapping("/crear-equipo")
    public ModelAndView irACrearEquipo() {
        ModelMap modelo = new ModelMap();
        Equipo equipo = new Equipo();

        /* Agregar verificación que si el usuario ya tiene un equipo creado lo redirija a
        ver equipo */
        // TORNEO LLAMAR AL TORNEO.buscarTorneoActual();
        // Se crea un objeto vacio que luego se va a rellenar con los datos del form.
        modelo.put("equipo", equipo);

        return new ModelAndView("crear-equipo", modelo);
    }


    // Guarda el nombre del equipo en el servicio
    @RequestMapping(value = "/guardarEquipo", method = RequestMethod.POST)
    public ModelAndView guardarNombreEquipo(@ModelAttribute Equipo equipoIngresado) {

        try {
            if (equipoIngresado.getNombreEquipo() == null || equipoIngresado.getNombreEquipo().isBlank()) {
                throw new EquipoSinNombreException();
            }

            Equipo equipoGuardado = servicioEquipo.guardarEquipo(equipoIngresado);
            return new ModelAndView("redirect:/seleccionar-jugadores?id=" + equipoGuardado.getId());

        } catch (EquipoSinNombreException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("equipo", new Equipo());
            modelo.put("error", "El nombre del equipo no puede estar vacío");
            return new ModelAndView("crear-equipo", modelo);
        }
    }

    // Vista con el form para que seleccione a los jugadores.
    // El request recibe por parámetro el id que es obtenido del método anterior
    @RequestMapping("/seleccionar-jugadores")
    public ModelAndView seleccionarJugadores(@RequestParam Long id, @RequestParam(required = false) String error) {

        try {
            ModelMap modelo = new ModelMap();

            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);
            modelo.put("equipo", equipo);

            List<Jugador> jugadoresBase = servicioJugador.buscarBase();
            List<Jugador> jugadoresAlero = servicioJugador.buscarAlero();
            List<Jugador> jugadoresPivot = servicioJugador.buscarPivot();

            List<EquipoJugador> jugadoresDelEquipo = servicioEquipo.buscarJugadoresDelEquipo(id);

            modelo.put("listadoBases", jugadoresBase);
            modelo.put("listadoAleros", jugadoresAlero);
            modelo.put("listadoPivots", jugadoresPivot);
            modelo.put("equipoJugadores", jugadoresDelEquipo);

            modelo.put("base1", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 1));
            modelo.put("base2", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 2));
            modelo.put("alero1", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 3));
            modelo.put("alero2", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 4));
            modelo.put("pivot", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 5));

            modelo.put("supPivot", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 6));
            modelo.put("supAlero1", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 7));
            modelo.put("supAlero2", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 8));
            modelo.put("supBase1", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 9));
            modelo.put("supBase2", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 10));
            /*CAPITAN*/
            modelo.put("capitan", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 11));
            /*SEXTO HOMBRE*/
            modelo.put("sextoHombre", servicioEquipoJugador.buscarJugadorPorNumeroDeOrden(jugadoresDelEquipo, 12));

            if (error != null) {
                modelo.put("error", error);
            }

            return new ModelAndView("seleccionar-jugadores", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/home");
        }
    }

    @RequestMapping(value = "/agregar-jugador", method = RequestMethod.POST)
    public ModelAndView agregarJugadorAlEquipo(@RequestParam Long idEquipo, @RequestParam Long idJugador, @RequestParam Integer numeroDeOrden) throws EquipoNoEncontradoException {
        try {
            servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, numeroDeOrden);
            return new ModelAndView("redirect:/seleccionar-jugadores?id=" + idEquipo);

        } catch (elJugadorYaExisteEnElEquipoException | PresupuestoInsuficienteException e) {
            return new ModelAndView("redirect:/seleccionar-jugadores?id=" + idEquipo + "&error=" + e.getMessage());
        }
    }

    @RequestMapping(value = "/eliminar-jugador", method = RequestMethod.POST)
    public ModelAndView eliminarJugadorDelEquipo(@RequestParam Long idEquipo, @RequestParam Long idJugador) throws EquipoNoEncontradoException {

        servicioEquipo.eliminarJugadorDelEquipo(idEquipo, idJugador);
        return new ModelAndView("redirect:/seleccionar-jugadores?id=" + idEquipo);
    }


    @RequestMapping(value = "/confirmar-equipo", method = RequestMethod.POST)
    public ModelAndView confirmarEquipoCompleto(@RequestParam Long idEquipo) throws EquipoSinCompletarException {

        try {
            servicioEquipo.validarEquipoCompleto(idEquipo);
            return new ModelAndView("redirect:/ver-equipo?id=" + idEquipo);
        } catch (EquipoSinCompletarException e) {
            return new ModelAndView("redirect:/seleccionar-jugadores?id=" + idEquipo + "&error=" + e.getMessage());
        }
    }

    @RequestMapping("/ver-equipo")
    public ModelAndView verEquipo(@RequestParam Long id) {

        try {
            ModelMap modelo = new ModelMap();

            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);
            List<EquipoJugador> listadoDeJugadoresAsociadosAlEquipo = servicioEquipo.buscarJugadoresDelEquipo(id);

            modelo.put("equipo", equipo);
            modelo.put("jugadoresEquipo", listadoDeJugadoresAsociadosAlEquipo);

            return new ModelAndView("ver-equipo", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/crear-equipo");
        }
    }


}
