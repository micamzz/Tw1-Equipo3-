package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.equipo.Equipo;
import com.tallerwebi.dominio.equipo.ServicioEquipo;
import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
import com.tallerwebi.dominio.equipoJugador.ServicioEquipoJugador;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class ControladorEquipo {

    private final ServicioEquipo servicioEquipo;
    private final ServicioEquipoJugador servicioEquipoJugador;
    private final ServicioTorneo servicioTorneo;

    @Autowired
    public ControladorEquipo(ServicioEquipo servicioEquipo, ServicioEquipoJugador servicioEquipoJugador, ServicioTorneo servicioTorneo) {
        this.servicioEquipo = servicioEquipo;
        this.servicioEquipoJugador = servicioEquipoJugador;
        this.servicioTorneo = servicioTorneo;
    }

    private boolean noEstaLogueado(HttpServletRequest request) {
        return request.getSession().getAttribute("usuario") == null;
    }


    @RequestMapping("/crear-equipo")
    public ModelAndView irACrearEquipo(HttpServletRequest request) {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        Equipo equipoExistente = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());

        if (equipoExistente != null) {
            return new ModelAndView("redirect:/equipo/detalle/" + equipoExistente.getId());
        }

        ModelMap modelo = new ModelMap();
        modelo.put("equipo", new Equipo());
        modelo.put("torneoActual", servicioTorneo.obtenerTorneoActual(TipoTorneo.VIRTUAL));
        return new ModelAndView("crear-equipo", modelo);
    }

    @RequestMapping(value = "/guardarEquipo", method = RequestMethod.POST)
    public ModelAndView guardarNombreEquipo(@ModelAttribute Equipo equipoIngresado, HttpServletRequest request) {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        Equipo equipoExistente = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());

        if (equipoExistente != null) {
            return new ModelAndView("redirect:/equipo/detalle/" + equipoExistente.getId());
        }

        try {
            if (equipoIngresado.getNombreEquipo() == null || equipoIngresado.getNombreEquipo().isBlank()) {
                throw new EquipoSinNombreException("El nombre del equipo no puede estar vacío");
            }

            equipoIngresado.setUsuario(usuario);
            Equipo equipoGuardado = servicioEquipo.guardarEquipo(equipoIngresado);
            return new ModelAndView("redirect:/seleccionar-jugadores/" + equipoGuardado.getId());

        } catch (EquipoSinNombreException | TorneoVirtualActualNoEncontradoException e) {
            ModelMap modelo = new ModelMap();
            modelo.put("equipo", new Equipo());
            modelo.put("error", e.getMessage());
            return new ModelAndView("crear-equipo", modelo);
        }
    }

    @RequestMapping("/seleccionar-jugadores/{id}")
    public ModelAndView seleccionarJugadores(HttpServletRequest request,
                                             @PathVariable Long id,
                                             @RequestParam(required = false) String error) {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        try {
            ModelMap modelo = new ModelMap();

            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);
            modelo.put("equipo", equipo);

            List<Jugador> jugadoresBase = servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.BASE);
            List<Jugador> jugadoresAlero = servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.ALERO);
            List<Jugador> jugadoresPivot = servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.PIVOT);

            HashMap<Integer, EquipoJugador> jugadoresDelEquipoPorOrden = servicioEquipoJugador.buscarJugadoresPorEquipoId(id);

            modelo.put("listadoBases", jugadoresBase);
            modelo.put("listadoAleros", jugadoresAlero);
            modelo.put("listadoPivots", jugadoresPivot);

            modelo.put("base1", jugadoresDelEquipoPorOrden.get(1));
            modelo.put("base2", jugadoresDelEquipoPorOrden.get(2));
            modelo.put("alero1", jugadoresDelEquipoPorOrden.get(3));
            modelo.put("alero2", jugadoresDelEquipoPorOrden.get(4));
            modelo.put("pivot", jugadoresDelEquipoPorOrden.get(5));
            modelo.put("supPivot", jugadoresDelEquipoPorOrden.get(6));
            modelo.put("supAlero1", jugadoresDelEquipoPorOrden.get(7));
            modelo.put("supAlero2", jugadoresDelEquipoPorOrden.get(8));
            modelo.put("supBase1", jugadoresDelEquipoPorOrden.get(9));
            modelo.put("supBase2", jugadoresDelEquipoPorOrden.get(10));
            modelo.put("capitan", jugadoresDelEquipoPorOrden.get(11));
            modelo.put("sextoHombre", jugadoresDelEquipoPorOrden.get(12));

            if (error != null) modelo.put("error", error);

            return new ModelAndView("seleccionar-jugadores", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/home");
        }
    }

    @RequestMapping(value = "/agregar-jugador/{idEquipo}/{idJugador}", method = RequestMethod.POST)
    public ModelAndView agregarJugadorAlEquipo(HttpServletRequest request,
                                               @PathVariable Long idEquipo,
                                               @PathVariable Long idJugador,
                                               @RequestParam Integer numeroDeOrden) throws EquipoNoEncontradoException {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        try {
            servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, numeroDeOrden);
            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo);
        } catch (elJugadorYaExisteEnElEquipoException | PresupuestoInsuficienteException e) {
            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + e.getMessage());
        }
    }

    @RequestMapping(value = "/eliminar-jugador/{idEquipo}/{idJugador}", method = RequestMethod.POST)
    public ModelAndView eliminarJugadorDelEquipo(HttpServletRequest request,
                                                 @PathVariable Long idEquipo,
                                                 @PathVariable Long idJugador) throws EquipoNoEncontradoException {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        servicioEquipo.eliminarJugadorDelEquipo(idEquipo, idJugador);
        return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo);
    }

    @RequestMapping(value = "/confirmar-equipo/{idEquipo}", method = RequestMethod.POST)
    public ModelAndView confirmarEquipoCompleto(HttpServletRequest request,
                                                @PathVariable Long idEquipo) {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        try {
            servicioEquipo.validarEquipoCompleto(idEquipo);
            return new ModelAndView("redirect:/equipo/detalle/" + idEquipo);
        } catch (EquipoSinCompletarException e) {
            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + e.getMessage());
        }
    }

    @RequestMapping("/equipo/detalle/{id}")
    public ModelAndView verEquipo(@PathVariable Long id, HttpServletRequest request) {
        if (noEstaLogueado(request)) return new ModelAndView("redirect:/login");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        try {
            ModelMap modelo = new ModelMap();
            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);

            if (!equipo.getUsuario().getId().equals(usuario.getId())) {
                return new ModelAndView("redirect:/");
            }

            HashMap<Integer, EquipoJugador> listadoDeJugadoresAsociadosAlEquipo =
                    servicioEquipoJugador.buscarJugadoresPorEquipoId(id);

            modelo.put("equipo", equipo);
            modelo.put("jugadoresEquipo", listadoDeJugadoresAsociadosAlEquipo.values());

            return new ModelAndView("ver-equipo", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/crear-equipo");
        }
    }
}
