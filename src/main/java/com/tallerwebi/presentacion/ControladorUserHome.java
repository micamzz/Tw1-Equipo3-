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

        List<Equipo> topEquiposFecha = List.of();
        try {
            Fecha fechaActual = servicioFecha.obtenerFechaActual();
            topEquiposFecha = servicioEquipo.obtenerTopEquiposPorFecha(fechaActual.getId(), 5);
        } catch (Exception ignored) {
        }
        modelo.put("topEquiposFecha", topEquiposFecha);

        return new ModelAndView("home", modelo);
    }

    @GetMapping("/estadisticas-jugadores-torneo")
    public ModelAndView verEstadisticasJugadoresTorneo(HttpServletRequest request) throws FechaNoEncontradaException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) return new ModelAndView("redirect:/login");

        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
        if (torneoActual == null) return new ModelAndView("redirect:/home");

        Fecha fechaActual = servicioFecha.obtenerFechaActual();

        Equipo equipo = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());

        List<HashMap<String, Object>> jugadoresConStats = new ArrayList<>();

        if (equipo != null && fechaActual != null) {
            List<EquipoJugador> equipoJugadores = servicioEquipo.buscarJugadoresDelEquipo(equipo.getId());

            for (EquipoJugador ej : equipoJugadores) {
                Long jugadorId = ej.getJugador().getId();
                List<EventoPartido> eventos = servicioMercado.buscarEventosPorJugadorYFecha(jugadorId, fechaActual.getId());

                int p = 0, r = 0, a = 0, ro = 0, bl = 0, pe = 0;
                for (EventoPartido ev : eventos) {
                    switch (ev.getTipoEstadistica()) {
                        case TIRO_LIBRE: p += 1; break;
                        case DOBLE:      p += 2; break;
                        case TRIPLE:     p += 3; break;
                        case REBOTE:     r++;     break;
                        case ASISTENCIA: a++;     break;
                        case ROBO:       ro++;    break;
                        case TAPA:       bl++;    break;
                        case PERDIDA:
                        case FALTA_PERSONAL: pe++; break;
                    }
                }

                HashMap<String, Object> item = new HashMap<>();
                item.put("jugador", ej.getJugador());
                item.put("puntos", p);
                item.put("rebotes", r);
                item.put("asistencias", a);
                item.put("robos", ro);
                item.put("bloqueos", bl);
                item.put("perdidas", pe);

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

                double base = p + 1.2 * r + 1.5 * a + 3.0 * ro + 3.0 * bl - 2.0 * pe;
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

        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        modelo.put("equipo", equipo);
        modelo.put("torneoActual", torneoActual);
        modelo.put("fechaActual", fechaActual);
        modelo.put("jugadoresConStats", jugadoresConStats);
        return new ModelAndView("estadisticas-jugadores-torneo", modelo);
    }

    @GetMapping("/historial-fechas")
    public ModelAndView verHistorialFechas(HttpServletRequest request) throws FechaNoEncontradaException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        if (usuario == null) return new ModelAndView("redirect:/login");

        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
        Equipo equipo = servicioEquipo.obtenerEquipoPorIdUsuario(usuario.getId());

        List<HashMap<String, Object>> secciones = new ArrayList<>();

        if (equipo != null && torneoActual != null) {
            List<Fecha> todasLasFechas = servicioFecha.obtenerTodasLasFechas();
            List<Fecha> fechasTorneo = new ArrayList<>();
            for (Fecha f : todasLasFechas) {
                if (f.getTorneo().getId().equals(torneoActual.getId())
                        && !f.getEstadoFecha().equals(EstadoFecha.PROGRAMADA)) {
                    fechasTorneo.add(f);
                }
            }

            List<EquipoJugador> equipoJugadores = servicioEquipo.buscarTodosLosJugadoresDelEquipo(equipo.getId());

            for (Fecha fecha : fechasTorneo) {
                List<HashMap<String, Object>> filas = new ArrayList<>();

                for (EquipoJugador ej : equipoJugadores) {
                    Long jugadorId = ej.getJugador().getId();
                    List<EventoPartido> eventos = servicioMercado.buscarEventosPorJugadorYFecha(jugadorId, fecha.getId());

                    int p = 0, r = 0, a = 0, ro = 0, bl = 0, pe = 0;
                    for (EventoPartido ev : eventos) {
                        switch (ev.getTipoEstadistica()) {
                            case TIRO_LIBRE: p += 1; break;
                            case DOBLE:      p += 2; break;
                            case TRIPLE:     p += 3; break;
                            case REBOTE:     r++;     break;
                            case ASISTENCIA: a++;     break;
                            case ROBO:       ro++;    break;
                            case TAPA:       bl++;    break;
                            case PERDIDA:
                            case FALTA_PERSONAL: pe++; break;
                        }
                    }

                    HashMap<String, Object> fila = new HashMap<>();
                    fila.put("jugador", ej.getJugador());
                    fila.put("puntos", p);
                    fila.put("rebotes", r);
                    fila.put("asistencias", a);
                    fila.put("robos", ro);
                    fila.put("bloqueos", bl);
                    fila.put("perdidas", pe);
                    filas.add(fila);
                }

                filas.sort((a, b) -> Integer.compare((int) b.get("puntos"), (int) a.get("puntos")));

                HashMap<String, Object> seccion = new HashMap<>();
                seccion.put("fecha", fecha);
                seccion.put("filas", filas);
                secciones.add(seccion);
            }

            secciones.sort((a, b) -> Integer.compare(
                    ((Fecha) a.get("fecha")).getNumeroDeFecha(),
                    ((Fecha) b.get("fecha")).getNumeroDeFecha()));
        }

        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuario);
        modelo.put("equipo", equipo);
        modelo.put("torneoActual", torneoActual);
        modelo.put("secciones", secciones);
        return new ModelAndView("historial-fechas", modelo);
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