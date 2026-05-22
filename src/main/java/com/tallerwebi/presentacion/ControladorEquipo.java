package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoSinNombreException;
import com.tallerwebi.dominio.excepcion.EquipoTitularSinCompletarException;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
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
    public ModelAndView irACrearEquipo() {
        ModelMap modelo = new ModelMap();

        /* Agregar verificacion que si el usuario ya tiene un equipo creado lo redirija a
        ver equipo */
        // TORNEO LLAMAR AL TORNEO.buscarTorneoActual();
        // Se crea un objeto vacio que luego se va a rellenar con los datos del form.
        modelo.put("equipo", new Equipo());

        return new ModelAndView("crear-equipo", modelo);
    }


    // Guarda el nombre del equipo en el servicio
    @RequestMapping(value = "/guardarEquipo", method = RequestMethod.POST)
    public ModelAndView guardarNombreEquipo(@ModelAttribute Equipo equipoIngresado) throws EquipoSinNombreException {

        try {
            if (equipoIngresado.getNombreEquipo() == null || equipoIngresado.getNombreEquipo().isBlank()) {
                throw new EquipoSinNombreException("No se puede crear equipo con nombre vacio");
            }

            Equipo equipoGuardado = servicioEquipo.guardarEquipo(equipoIngresado);
            return new ModelAndView("redirect:/seleccionar-jugadores?id=" + "equipo=" + equipoGuardado.getId());

        } catch (EquipoSinNombreException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("equipo", new Equipo());
            modelo.put("error", e.getMessage());
            return new ModelAndView("crear-equipo", modelo);
        }
    }

    // Vista con el form para que seleccione a los jugadores.
    // El request recibe por parametro el id que es obtenido del metodo anterior
    @RequestMapping("/seleccionar-jugadores")
    public ModelAndView seleccionarJugadores(@RequestParam Long id) throws EquipoNoEncontradoException {

        try {
            ModelMap modelo = new ModelMap();

            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);
            modelo.put("equipo", equipo);

            List<Jugador> jugadoresBase = servicioJugador.buscarBase();
            List<Jugador> jugadoresAlero = servicioJugador.buscarAlero();
            List<Jugador> jugadoresPivot = servicioJugador.buscarPivot();

            modelo.put("listadoBases", jugadoresBase);
            modelo.put("listadoAleros", jugadoresAlero);
            modelo.put("listadoPivots", jugadoresPivot);

            return new ModelAndView("seleccionar-jugadores", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/crear-equipo");
        }
    }


    // Guarda la seleccion de jugadores elegidos.
    @RequestMapping(value = "/guardar-equipo", method = RequestMethod.POST)
    public ModelAndView guardarEquipoCompleto(@RequestParam Long idEquipo,
                                              @RequestParam("idJugador") List<Long> idsJugadores) throws EquipoTitularSinCompletarException, EquipoNoEncontradoException, PresupuestoInsuficienteException {
        try {
            servicioEquipo.guardarEquipoCompleto(idEquipo, idsJugadores);
            return new ModelAndView("redirect:/ver-equipo?id=" + idEquipo);

        } catch (EquipoTitularSinCompletarException | PresupuestoInsuficienteException e) {
            return new ModelAndView("redirect:/seleccionar-jugadores?id=" + idEquipo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/crear-equipo");
        }
    }

    @RequestMapping("/ver-equipo")
    public ModelAndView verEquipo(@RequestParam Long id) throws EquipoNoEncontradoException {

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
