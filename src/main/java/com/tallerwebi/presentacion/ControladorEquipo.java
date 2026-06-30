package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.ServicioTorneo;
import com.tallerwebi.dominio.TipoTorneo;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.PosicionJugadorEquipo;
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
/*Se agrego estos import para evitar error ERROR 400 Unable to parse URI query*/
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    /* Encodea el mensaje de error para evitar caracteres invalidos en la URL (tildes, ñ, espacios) */
    private String encodearError(String mensaje) {
        return URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
    }

    @RequestMapping("/crear-equipo")
    public ModelAndView irACrearEquipo(HttpServletRequest request) {

        if (request.getSession().getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/login");
        }
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

        if (request.getSession().getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/login");
        }
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        Equipo equipoExistente = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());

        if (equipoExistente != null) {
            return new ModelAndView("redirect:/equipo/detalle/" + equipoExistente.getId());
        }

        try {
            if (equipoIngresado.getNombreEquipo() == null || equipoIngresado.getNombreEquipo().isBlank()) {
                throw new EquipoSinNombreException("El nombre del equipo no puede estar vacio");
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
        if (request.getSession().getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            ModelMap modelo = new ModelMap();

            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);
            modelo.put("equipo", equipo);

            // Jugadores disponibles para fichar por posicion
            modelo.put("listadoBases", servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.BASE));
            modelo.put("listadoAleros", servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.ALERO));
            modelo.put("listadoPivots", servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.PIVOT));

            // Jugadores ya en el equipo por numero de orden (1-10)
            HashMap<Integer, EquipoJugador> porOrden = servicioEquipoJugador.buscarJugadoresPorEquipoId(id);
            modelo.put("base1", porOrden.get(1));
            modelo.put("base2", porOrden.get(2));
            modelo.put("alero1", porOrden.get(3));
            modelo.put("alero2", porOrden.get(4));
            modelo.put("pivot", porOrden.get(5));
            modelo.put("supPivot", porOrden.get(6));
            modelo.put("supAlero1", porOrden.get(7));
            modelo.put("supAlero2", porOrden.get(8));
            modelo.put("supBase1", porOrden.get(9));
            modelo.put("supBase2", porOrden.get(10));

            // Capitan y sexto hombre — se buscan por rol, no por orden
            List<EquipoJugador> todosLosJugadores = servicioEquipo.buscarJugadoresDelEquipo(id);

            EquipoJugador capitan = null;
            EquipoJugador sextoHombre = null;
            List<EquipoJugador> titulares = new ArrayList<>();
            List<EquipoJugador> suplentes = new ArrayList<>();

            for (EquipoJugador ej : todosLosJugadores) {
                if (ej.getPosicionDelJugador() == PosicionJugadorEquipo.CAPITAN) {
                    capitan = ej;
                } else if (ej.getPosicionDelJugador() == PosicionJugadorEquipo.SEXTO_HOMBRE) {
                    sextoHombre = ej;
                }
                if (ej.getNumeroOrden() <= 5) {
                    titulares.add(ej);
                } else {
                    suplentes.add(ej);
                }
            }

            modelo.put("capitan", capitan);
            modelo.put("sextoHombre", sextoHombre);
            modelo.put("titularesParaCapitan", titulares);
            modelo.put("suplentesParaSextoHombre", suplentes);

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

        if (request.getSession().getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, numeroDeOrden);
            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo);
        } catch (elJugadorYaExisteEnElEquipoException | PresupuestoInsuficienteException e) {
            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + encodearError(e.getMessage()));
        }
    }

    @RequestMapping(value = "/eliminar-jugador/{idEquipo}/{idJugador}", method = RequestMethod.POST)
    public ModelAndView eliminarJugadorDelEquipo(HttpServletRequest request,
                                                 @PathVariable Long idEquipo,
                                                 @PathVariable Long idJugador) throws EquipoNoEncontradoException {

        if (request.getSession().getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/login");
        }
        servicioEquipo.eliminarJugadorDelEquipo(idEquipo, idJugador);
        return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo);
    }

    @RequestMapping(value = "/confirmar-equipo/{idEquipo}", method = RequestMethod.POST)
    public ModelAndView confirmarEquipoCompleto(HttpServletRequest request,
                                                @PathVariable Long idEquipo) {
        if (request.getSession().getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            servicioEquipo.validarEquipoCompleto(idEquipo);
            return new ModelAndView("redirect:/equipo/detalle/" + idEquipo);
        } catch (EquipoSinCompletarException e) {
            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + encodearError(e.getMessage()));
        }
    }

    @RequestMapping("/equipo/detalle/{id}")
    public ModelAndView verEquipo(@PathVariable Long id, HttpServletRequest request) {

        if (request.getSession().getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/login");
        }
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        try {
            ModelMap modelo = new ModelMap();
            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);

            if (!equipo.getUsuario().getId().equals(usuario.getId())) {
                return new ModelAndView("redirect:/");
            }

            HashMap<Integer, EquipoJugador> listadoDeJugadoresAsociadosAlEquipo = servicioEquipoJugador.buscarJugadoresPorEquipoId(id);

//            Capitan y sexto hombre se buscan por rol.
            List<EquipoJugador> todosLosJugadores = servicioEquipo.buscarJugadoresDelEquipo(id);
            EquipoJugador capitan = null;
            EquipoJugador sextoHombre = null;

            for (EquipoJugador ej : todosLosJugadores) {
                if (ej.getPosicionDelJugador() == PosicionJugadorEquipo.CAPITAN) {
                    capitan = ej;
                } else if (ej.getPosicionDelJugador() == PosicionJugadorEquipo.SEXTO_HOMBRE) {
                    sextoHombre = ej;
                }
            }

            modelo.put("equipo", equipo);
            modelo.put("capitan", capitan);
            modelo.put("sextoHombre", sextoHombre);
//            Devuelve values por es un map.
            modelo.put("jugadoresEquipo", listadoDeJugadoresAsociadosAlEquipo.values());

            return new ModelAndView("ver-equipo", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/crear-equipo");
        }
    }

    /* Asigna el rol de CAPITAN o SEXTO_HOMBRE a un jugador ya existente en el equipo.  */
    @RequestMapping(value = "/asignar-rol/{idEquipo}/{idJugador}", method = RequestMethod.POST)
    public ModelAndView asignarRolEspecial(HttpServletRequest request,
                                           @PathVariable Long idEquipo,
                                           @PathVariable Long idJugador,
                                           @RequestParam String rol) {

        if (request.getSession().getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/login");
        }
        try {
            PosicionJugadorEquipo posicion = PosicionJugadorEquipo.valueOf(rol);
            servicioEquipo.asignarRolEspecial(idEquipo, idJugador, posicion);
        } catch (Exception e) {
            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + encodearError(e.getMessage()));
        }

        return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo);
    }
}