package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.PosicionJugadorEquipo;
import com.tallerwebi.dominio.equipo.Equipo;
import com.tallerwebi.dominio.equipo.ServicioEquipo;
import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
import com.tallerwebi.dominio.excepcion.FechaNoEncontradaException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorUserHome {

    private final ServicioPartidoNBA servicioPartidoNBA;
    private final ServicioTorneo servicioTorneo;
    private final ServicioEquipo servicioEquipo;
    private final ServicioMercado servicioMercado;
    private final ServicioFecha servicioFecha;

    public ControladorUserHome(ServicioPartidoNBA servicioPartidoNBA, ServicioTorneo servicioTorneo, ServicioEquipo servicioEquipo, ServicioMercado servicioMercado, ServicioFecha servicioFecha) {
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioTorneo = servicioTorneo;
        this.servicioEquipo = servicioEquipo;
        this.servicioMercado = servicioMercado;
        this.servicioFecha = servicioFecha;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView iraHome(HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Equipo equipo = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());

        if (equipo != null) {
            equipo.setPuntaje(servicioEquipo.calcularPuntajeTotalDelEquipo(equipo.getId()));
        }

        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);

        ModelMap modelo = new ModelMap();

        modelo.put("usuario", usuario);
        modelo.put("equipo", equipo);
        modelo.put("presupuestoInicial", servicioEquipo.obtenerPresupuestoInicial());
        modelo.put("torneoActual", torneoActual);
        modelo.put("proximosPartidos", obtenerProximosPartidosParaHome());
        modelo.put("servicioMercado", servicioMercado);

        /*
         * Se verifica si el usuario ya tiene jugadores asignados
         * para la fecha actual.
         */
        boolean tieneJugadores = false;

        if (equipo != null) {
            try {
                List<EquipoJugador> jugadoresDelEquipo = servicioEquipo.buscarJugadoresDelEquipo(equipo.getId());

                tieneJugadores = jugadoresDelEquipo != null && !jugadoresDelEquipo.isEmpty();

            } catch (FechaNoEncontradaException e) {
                tieneJugadores = false;
            }
        }

        modelo.put("tieneJugadores", tieneJugadores);
        modelo.put("puedeModificar", servicioEquipo.puedeModificarEquipo());

        if (torneoActual != null) {
            List<RendimientoJugador> top3 = servicioMercado.obtenerTopJugadoresPorTorneo(torneoActual.getId(), 3);

            modelo.put("topRendimientos", top3);
        }

        List<Equipo> topEquipos = List.of();

        try {
            Torneo torneoVirtual = servicioTorneo.obtenerTorneoActual(TipoTorneo.VIRTUAL);

            if (torneoVirtual != null) {
                List<Equipo> resultado = servicioEquipo.obtenerTopEquiposPorTorneo(torneoVirtual.getId(), 5);

                if (resultado != null) {
                    topEquipos = resultado;
                }
            }

        } catch (Exception ignored) {
        }

        modelo.put("topEquipos", topEquipos);

        return new ModelAndView("home", modelo);
    }

    @GetMapping("/estadisticas-jugadores-torneo")
    public ModelAndView verEstadisticasJugadoresTorneo(HttpServletRequest request) throws FechaNoEncontradaException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) return new ModelAndView("redirect:/login");

        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
        if (torneoActual == null) return new ModelAndView("redirect:/home");

        Equipo equipo = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());

        List<HashMap<String, Object>> jugadoresConStats = new ArrayList<>();

        if (equipo != null) {
            List<EquipoJugador> equipoJugadores = servicioEquipo.buscarJugadoresDelEquipo(equipo.getId());

            for (EquipoJugador ej : equipoJugadores) {
                RendimientoJugador rend = servicioMercado.obtenerRendimientoPorJugadorYTorneo(ej.getJugador().getId(), torneoActual.getId());
                if (rend == null) continue;

                HashMap<String, Object> item = new HashMap<>();
                item.put("jugador", ej.getJugador());
                item.put("rend", rend);
                PosicionJugadorEquipo rolEnum = ej.getPosicionDelJugador();
                String mostrarNombre;
                switch (rolEnum) {
                    case CAPITAN:
                        mostrarNombre = "Capitán";
                        break;
                    case SEXTO_HOMBRE:
                        mostrarNombre = "Sexto hombre";
                        break;
                    case SUPLENTE:
                        mostrarNombre = "Suplente";
                        break;
                    default:
                        mostrarNombre = "Titular";
                        break;
                }
                item.put("rol", mostrarNombre);
                double base = servicioMercado.calcularPuntajeJugador(rend);
                double multiplicador;
                switch (rolEnum) {
                    case CAPITAN:
                        multiplicador = 2.0;
                        break;
                    case SEXTO_HOMBRE:
                        multiplicador = 0.8;
                        break;
                    case SUPLENTE:
                        multiplicador = 0.5;
                        break;
                    default:
                        multiplicador = 1.0;
                        break;
                }
                item.put("puntaje", base * multiplicador);

                jugadoresConStats.add(item);
            }

            jugadoresConStats.sort((a, b) -> Double.compare((Double) b.get("puntaje"), (Double) a.get("puntaje")));
        }

        List<Torneo> torneosReales = servicioTorneo.obtenerTodosLosTorneos().stream()
                .filter(t -> t.getTipoTorneo() == TipoTorneo.REAL && !t.getId().equals(torneoActual.getId()))
                .collect(Collectors.toList());

        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        modelo.put("equipo", equipo);
        modelo.put("torneoActual", torneoActual);
        modelo.put("jugadoresConStats", jugadoresConStats);
        modelo.put("torneosPasados", torneosReales);
        modelo.put("servicioMercado", servicioMercado);
        return new ModelAndView("estadisticas-jugadores-torneo", modelo);
    }

    @GetMapping("/perfil")
    public ModelAndView verPerfil(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) return new ModelAndView("redirect:/login");

        Equipo equipo = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        modelo.put("equipo", equipo);
        if (equipo != null) {
            try {
                List<EquipoJugador> jugadores = servicioEquipo.buscarJugadoresDelEquipo(equipo.getId());
                modelo.put("jugadores", jugadores);

                double puntaje = servicioEquipo.calcularPuntajeTotalDelEquipo(equipo.getId());
                modelo.put("puntaje", puntaje);

                //Ranking

                Torneo torneoVirtual = servicioTorneo.obtenerTorneoActual(TipoTorneo.VIRTUAL);
                if (torneoVirtual != null) {
                    List<Equipo> todosLosEquipos = servicioEquipo.obtenerTopEquiposPorTorneo(torneoVirtual.getId(), 9999);
                    int posicion = 1;
                    for (Equipo e : todosLosEquipos) {
                        if (e.getId().equals(equipo.getId())) break;
                        posicion++;
                    }
                    modelo.put("rankingTemporada", posicion);
                }
            } catch (Exception e) {
            }
        }

        return new ModelAndView("perfil-usuario", modelo);
    }

    private List<PartidoNBA> obtenerProximosPartidosParaHome() {
        try {
            Fecha fechaActual = servicioFecha.obtenerFechaActual();
            return servicioPartidoNBA.obtenerPartidosProgramadosPorFecha(fechaActual.getId());
        } catch (FechaNoEncontradaException e) {
            return List.of();
        }
    }
}
