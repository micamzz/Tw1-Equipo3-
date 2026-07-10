package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
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
    private final ServicioFecha servicioFecha;

    @Autowired
    public ControladorEquipo(
            ServicioEquipo servicioEquipo,
            ServicioEquipoJugador servicioEquipoJugador,
            ServicioTorneo servicioTorneo,
            ServicioFecha servicioFecha) {

        this.servicioEquipo = servicioEquipo;
        this.servicioEquipoJugador = servicioEquipoJugador;
        this.servicioTorneo = servicioTorneo;
        this.servicioFecha = servicioFecha;
    }

    private String encodearError(String mensaje) {
        return URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
    }

    @RequestMapping("/crear-equipo")
    public ModelAndView irACrearEquipo() {

        ModelMap modelo = new ModelMap();

        modelo.put("equipo", new Equipo());
        modelo.put(
                "torneoActual",
                servicioTorneo.obtenerTorneoActual(TipoTorneo.VIRTUAL)
        );

        return new ModelAndView("crear-equipo", modelo);
    }

    @RequestMapping(value = "/guardarEquipo", method = RequestMethod.POST)
    public ModelAndView guardarNombreEquipo(@ModelAttribute Equipo equipoIngresado, HttpServletRequest request) {

        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }

        Equipo equipoExistente = servicioEquipo.obtenerEquipoPorIdUsuario(usuarioLogueado.getId());

        if (equipoExistente != null) {
            return new ModelAndView("redirect:/equipo/detalle/" + equipoExistente.getId());
        }

        try {
            if (equipoIngresado.getNombreEquipo() == null || equipoIngresado.getNombreEquipo().isBlank()) {

                throw new EquipoSinNombreException("El nombre del equipo no puede estar vacío");
            }

            equipoIngresado.setUsuario(usuarioLogueado);
            Equipo equipoGuardado = servicioEquipo.guardarEquipo(equipoIngresado);

            return new ModelAndView("redirect:/seleccionar-jugadores/" + equipoGuardado.getId());

        } catch (EquipoSinNombreException | TorneoVirtualActualNoEncontradoException e) {

            ModelMap modelo = new ModelMap();

            modelo.put("equipo", equipoIngresado);
            modelo.put("error", e.getMessage());

            return new ModelAndView("crear-equipo", modelo);
        }
    }

    @RequestMapping("/seleccionar-jugadores/{id}")
    public ModelAndView seleccionarJugadores(
            @PathVariable Long id,
            @RequestParam(required = false) String error) {

        try {
            ModelMap modelo = new ModelMap();

            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);
            Fecha fechaActual = servicioFecha.obtenerFechaActual();

            modelo.put("equipo", equipo);
            modelo.put("fechaActual", fechaActual);
            modelo.put("numeroFecha", fechaActual.getNumeroDeFecha());

            modelo.put("listadoBases", servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.BASE));

            modelo.put("listadoAleros", servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.ALERO));

            modelo.put("listadoPivots", servicioEquipoJugador.obtenerJugadoresDisponiblesPorPosicion(id, Posicion.PIVOT));

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

            List<EquipoJugador> todos = servicioEquipo.buscarJugadoresDelEquipo(id);

            modelo.put("equipoSinJugadores", todos.isEmpty());

            EquipoJugador capitan = null;
            EquipoJugador sextoHombre = null;

            List<EquipoJugador> titulares = new ArrayList<>();
            List<EquipoJugador> suplentes = new ArrayList<>();

            for (EquipoJugador equipoJugador : todos) {

                if (equipoJugador.getPosicionDelJugador() == PosicionJugadorEquipo.CAPITAN) {
                    capitan = equipoJugador;

                } else if (equipoJugador.getPosicionDelJugador() == PosicionJugadorEquipo.SEXTO_HOMBRE) {
                    sextoHombre = equipoJugador;
                }

                if (equipoJugador.getNumeroOrden() <= 5) {
                    titulares.add(equipoJugador);
                } else {
                    suplentes.add(equipoJugador);
                }
            }

            modelo.put("puedeModificar", servicioEquipo.puedeModificarEquipo());
            modelo.put("capitan", capitan);
            modelo.put("sextoHombre", sextoHombre);
            modelo.put("titularesParaCapitan", titulares);
            modelo.put("suplentesParaSextoHombre", suplentes);

            if (error != null) {
                modelo.put("error", error);
            }

            return new ModelAndView("seleccionar-jugadores", modelo);

        } catch (EquipoNoEncontradoException e) {
            return new ModelAndView("redirect:/home");

        } catch (FechaNoEncontradaException e) {
            return new ModelAndView("redirect:/home?error=" + encodearError(e.getMessage()));
        }
    }

    @RequestMapping(value = "/agregar-jugador/{idEquipo}/{idJugador}", method = RequestMethod.POST)
    public ModelAndView agregarJugadorAlEquipo(@PathVariable Long idEquipo, @PathVariable Long idJugador, @RequestParam Integer numeroDeOrden) {

        try {
            servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, numeroDeOrden);

            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo);

        } catch (elJugadorYaExisteEnElEquipoException
                 | PresupuestoInsuficienteException
                 | NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException e) {

            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + encodearError(e.getMessage()));

        } catch (EquipoNoEncontradoException
                 | FechaNoEncontradaException e) {

            return new ModelAndView(
                    "redirect:/home?error="
                            + encodearError(e.getMessage())
            );
        }
    }

    @RequestMapping(value = "/eliminar-jugador/{idEquipo}/{idJugador}", method = RequestMethod.POST)
    public ModelAndView eliminarJugadorDelEquipo(@PathVariable Long idEquipo, @PathVariable Long idJugador) {

        try {
            servicioEquipo.eliminarJugadorDelEquipo(idEquipo, idJugador);

            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo);

        } catch (NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException e) {

            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + encodearError(e.getMessage()));

        } catch (EquipoNoEncontradoException | FechaNoEncontradaException e) {

            return new ModelAndView("redirect:/home?error=" + encodearError(e.getMessage()));
        }
    }

    @RequestMapping(value = "/confirmar-equipo/{idEquipo}", method = RequestMethod.POST)
    public ModelAndView confirmarEquipoCompleto(@PathVariable Long idEquipo) {

        try {
            servicioEquipo.validarEquipoCompleto(idEquipo);

            return new ModelAndView("redirect:/equipo/detalle/" + idEquipo);

        } catch (EquipoSinCompletarException e) {

            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + encodearError(e.getMessage())
            );

        } catch (FechaNoEncontradaException e) {

            return new ModelAndView("redirect:/home?error=" + encodearError(e.getMessage())
            );
        }
    }

    @RequestMapping("/equipo/detalle/{id}")
    public ModelAndView verEquipo(@PathVariable Long id) {

        try {
            ModelMap modelo = new ModelMap();

            Equipo equipo = servicioEquipo.buscarEquipoPorId(id);
            Fecha fechaActual = servicioFecha.obtenerFechaActual();

            HashMap<Integer, EquipoJugador> jugadores = servicioEquipoJugador.buscarJugadoresPorEquipoId(id);

            List<EquipoJugador> todos =
                    servicioEquipo.buscarJugadoresDelEquipo(id);

            EquipoJugador capitan = null;
            EquipoJugador sextoHombre = null;

            for (EquipoJugador equipoJugador : todos) {

                if (equipoJugador.getPosicionDelJugador()
                        == PosicionJugadorEquipo.CAPITAN) {

                    capitan = equipoJugador;

                } else if (equipoJugador.getPosicionDelJugador()
                        == PosicionJugadorEquipo.SEXTO_HOMBRE) {

                    sextoHombre = equipoJugador;
                }
            }

            modelo.put("equipo", equipo);
            modelo.put("fechaActual", fechaActual);
            modelo.put("numeroFecha", fechaActual.getNumeroDeFecha());

            modelo.put("puedeModificar", servicioEquipo.puedeModificarEquipo());

            modelo.put("capitan", capitan);
            modelo.put("sextoHombre", sextoHombre);
            modelo.put("jugadoresEquipo", jugadores.values());

            return new ModelAndView("ver-equipo", modelo);

        } catch (EquipoNoEncontradoException e) {

            return new ModelAndView("redirect:/crear-equipo");

        } catch (FechaNoEncontradaException e) {

            return new ModelAndView("redirect:/home?error=" + encodearError(e.getMessage()));
        }
    }

    @RequestMapping(value = "/asignar-rol/{idEquipo}/{idJugador}", method = RequestMethod.POST)
    public ModelAndView asignarRolEspecial(
            @PathVariable Long idEquipo, @PathVariable Long idJugador, @RequestParam String rol) {

        try {
            PosicionJugadorEquipo posicion = PosicionJugadorEquipo.valueOf(rol);

            servicioEquipo.asignarRolEspecial(idEquipo, idJugador, posicion);

            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo);

        } catch (IllegalArgumentException e) {

            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + encodearError("El rol seleccionado no es válido"));

        } catch (NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException e) {

            return new ModelAndView("redirect:/seleccionar-jugadores/" + idEquipo + "?error=" + encodearError(e.getMessage()));

        } catch (EquipoNoEncontradoException
                 | FechaNoEncontradaException e) {

            return new ModelAndView("redirect:/home?error=" + encodearError(e.getMessage())
            );
        }
    }
}