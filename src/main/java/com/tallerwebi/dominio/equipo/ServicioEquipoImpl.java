package com.tallerwebi.dominio.equipo;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.PosicionJugadorEquipo;
import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
import com.tallerwebi.dominio.equipoJugador.RepositorioEquipoJugador;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoImpl implements ServicioEquipo {

    private final RepositorioEquipo repositorioEquipo;
    private final RepositorioJugador repositorioJugador;
    private final RepositorioEquipoJugador repositorioEquipoJugador;
    private final RepositorioTorneo repositorioTorneo;
    private final ServicioPartidoNBA servicioPartidoNBA;
    private static final Double PRESUPUESTO_INICIAL = 2000000D;
    private static final Integer CANTIDAD_JUGADORES_EQUIPO_COMPLETO = 10;
    private final ServicioFecha servicioFecha;
    private final RepositorioEventoPartido repositorioEventoPartido;


    @Autowired
    public ServicioEquipoImpl(RepositorioEquipo repositorioEquipo, RepositorioJugador repositorioJugador,
                              RepositorioEquipoJugador repositorioEquipoJugador, RepositorioTorneo repositorioTorneo,
                              ServicioPartidoNBA servicioPartidoNBA, ServicioFecha servicioFecha,
                              RepositorioEventoPartido repositorioEventoPartido) {
        this.repositorioEquipo = repositorioEquipo;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoJugador = repositorioEquipoJugador;
        this.repositorioTorneo = repositorioTorneo;
        this.servicioPartidoNBA = servicioPartidoNBA;
        this.servicioFecha = servicioFecha;
        this.repositorioEventoPartido = repositorioEventoPartido;
    }


    @Override
    public Equipo guardarEquipo(Equipo equipo) throws TorneoVirtualActualNoEncontradoException {
        equipo.setPresupuesto(PRESUPUESTO_INICIAL); // Presupuesto inicial para cada equipo
        equipo.setPuntaje(0D); // Comienza el torneo con 0 puntos

        Torneo torneoVirtualActual = repositorioTorneo.buscarTorneoActual(TipoTorneo.VIRTUAL);

        if (torneoVirtualActual == null) {
            throw new TorneoVirtualActualNoEncontradoException("No hay ningun torneo en curso");
        }

        equipo.setTorneo(torneoVirtualActual);
        repositorioEquipo.guardarEquipo(equipo);
        return equipo;/* Devuelve Equipo porq necesito recuperar el ID en el controlador  */
    }

    @Override
    public Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException {
        Equipo equipo = repositorioEquipo.buscarEquipoPorId(id);
        if (equipo == null) {
            throw new EquipoNoEncontradoException("No existe el equipo con id: " + id);
        }
        return equipo;
    }

    @Override
    public Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException {
        Equipo equipo = repositorioEquipo.buscarEquipoPorNombre(nombre);
        if (equipo == null) {
            throw new EquipoNoEncontradoException("No existe el equipo con nombre: " + nombre);
        }
        return equipo;
    }

    @Override
    public void validarEquipoCompleto(Long idEquipo) throws EquipoSinCompletarException, FechaNoEncontradaException {

        List<EquipoJugador> listadoDeJugadores = buscarJugadoresDelEquipo(idEquipo);

        if (listadoDeJugadores == null || listadoDeJugadores.size() < CANTIDAD_JUGADORES_EQUIPO_COMPLETO) {
            throw new EquipoSinCompletarException("El equipo debe tener 10 jugadores para poder confirmarlo ");
        }

        Boolean tieneCapitan = false;
        Boolean tieneSextoHombre = false;

        for (EquipoJugador ej : listadoDeJugadores) {
            if (ej.getPosicionDelJugador() == PosicionJugadorEquipo.CAPITAN) {
                tieneCapitan = true;
            }
            if (ej.getPosicionDelJugador() == PosicionJugadorEquipo.SEXTO_HOMBRE) {
                tieneSextoHombre = true;
            }
        }

        if (!tieneCapitan || !tieneSextoHombre) {
            throw new EquipoSinCompletarException("Debés asignar un capitán y un sexto hombre");
        }
    }

    @Override
    public void actualizarEquipo(Equipo equipo) {
        repositorioEquipo.actualizarEquipo(equipo);
    }

    @Override
    public Equipo obtenerEquipoPorIdUsuario(Long usuarioId) {
        return repositorioEquipo.buscarEquipoPorIdUsuario(usuarioId);
    }

    @Override
    public Double obtenerPresupuestoInicial() {
        return PRESUPUESTO_INICIAL;
    }

    @Override
    public void agregarJugadorAlEquipo(Long idEquipo, Long idJugador, Integer numeroDeOrden) throws EquipoNoEncontradoException, PresupuestoInsuficienteException, elJugadorYaExisteEnElEquipoException, FechaNoEncontradaException, NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException {
        validarQueSePuedaModificarEquipo();

        Fecha fechaActual = servicioFecha.obtenerFechaActual();
        Equipo equipo = buscarEquipoPorId(idEquipo);

        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        /* Verifica si el jugador ya está asociado al equipo*/
        validarSiElJugadorYaFueElegido(idEquipo, idJugador, fechaActual.getId());

        // Verifica que el presupuesto alcanze
        siElPresupuestoEsMenorLanzaExcepcion(equipo, jugador);

        // Llama al método que guarda la relación en el repositorioEquipoJugador
        guardarRelacionEntreEquipoYJugador(equipo, jugador, numeroDeOrden, fechaActual);
    }


    @Override
    public void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador)
            throws EquipoNoEncontradoException, FechaNoEncontradaException, NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException {

        validarQueSePuedaModificarEquipo();

        Fecha fechaActual = servicioFecha.obtenerFechaActual();

        Equipo equipo = buscarEquipoPorId(idEquipo);

        EquipoJugador equipoJugador = repositorioEquipoJugador.buscarEquipoYJugadorAsociadoPorFecha(idEquipo, idJugador, fechaActual.getId());

        if (equipoJugador != null) {
            equipo.setPresupuesto(equipo.getPresupuesto() + equipoJugador.getJugador().getPrecio());
            repositorioEquipoJugador.eliminarEquipoJugador(equipoJugador);
        }
    }


    /*DEVUELVE UNA LISTA CON TODOS LOS Jugadores QUE ESTAN ASOCIADOS AL EQUIPO
     * LOS TRAE POR FECHA */
    @Override
    public List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo) throws FechaNoEncontradaException {
        Fecha fechaActual = servicioFecha.obtenerFechaActual();

        return repositorioEquipoJugador.buscarPorEquipoIdYFechaId(idEquipo, fechaActual.getId()
        );
    }

    @Override
    public List<EquipoJugador> buscarTodosLosJugadoresDelEquipo(Long idEquipo) {
        return repositorioEquipoJugador.buscarPorEquipoId(idEquipo);
    }

    /*  GUARDA AL EQUIPO Y AL JUGADOR EN EQUIPOJUGADOR LLAMANDO AL REPOSITORIO */
    private void guardarRelacionEntreEquipoYJugador(Equipo equipo, Jugador jugador, Integer numeroDeOrden, Fecha fechaActual) {
        EquipoJugador equipoJugador = new EquipoJugador();

        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(jugador);
        equipoJugador.setNumeroOrden(numeroDeOrden);
        equipoJugador.setFecha(fechaActual);

        // Primero asigna a jugadores titulares y suplentes
        if (numeroDeOrden <= 5) {
            equipoJugador.setPosicionDelJugador(PosicionJugadorEquipo.TITULAR);
        } else {
            equipoJugador.setPosicionDelJugador(PosicionJugadorEquipo.SUPLENTE);
        }
        repositorioEquipoJugador.guardarEquipoJugador(equipoJugador);

    }

    /* SI EL SALDO DEL EQUIPO ES MENOR AL VALOR DEL JUGADOR EXCEPCION, NO PUEDE COMPRARLO.*/
    private void siElPresupuestoEsMenorLanzaExcepcion(Equipo equipo, Jugador jugador) throws PresupuestoInsuficienteException {
        if (equipo.getPresupuesto() < jugador.getPrecio()) {
            throw new PresupuestoInsuficienteException("Presupuesto insuficiente");
        } else {
            equipo.setPresupuesto(equipo.getPresupuesto() - jugador.getPrecio());
        }
    }

    /* SI EL JUGADOR YA ESTA ASOCIADO AL EQUIPO HAY UNA EXCEPCION*/
    private void validarSiElJugadorYaFueElegido(Long idEquipo, Long idJugador, Long idFecha) throws elJugadorYaExisteEnElEquipoException {
        EquipoJugador equipoJugador = repositorioEquipoJugador.buscarEquipoYJugadorAsociadoPorFecha(idEquipo, idJugador, idFecha);

        if (equipoJugador != null) {
            throw new elJugadorYaExisteEnElEquipoException("El jugador ya está fichado en el equipo para esta fecha");
        }
    }

    //    Actualiza el enum del jugador ya elegido.
    @Override
    public void asignarRolEspecial(Long idEquipo, Long idJugador, PosicionJugadorEquipo rol)
            throws EquipoNoEncontradoException, NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException, FechaNoEncontradaException {

        validarQueSePuedaModificarEquipo();

        validarQueSePuedaModificarEquipo();

        Fecha fechaActual = servicioFecha.obtenerFechaActual();

        List<EquipoJugador> todos = repositorioEquipoJugador.buscarPorEquipoIdYFechaId(idEquipo, fechaActual.getId());

        for (EquipoJugador ej : todos) {

            if (ej.getPosicionDelJugador() == rol) {
                if (ej.getNumeroOrden() <= 5) {
                    ej.setPosicionDelJugador(PosicionJugadorEquipo.TITULAR);
                } else {
                    ej.setPosicionDelJugador(PosicionJugadorEquipo.SUPLENTE);
                }
                repositorioEquipoJugador.actualizarEquipoJugador(ej);
            }
        }

        EquipoJugador equipoJugador = repositorioEquipoJugador.buscarEquipoYJugadorAsociadoPorFecha(idEquipo, idJugador, fechaActual.getId());

        if (equipoJugador == null) {
            throw new EquipoNoEncontradoException("El jugador no pertenece al equipo en la fecha actual");
        }

        equipoJugador.setPosicionDelJugador(rol);
        repositorioEquipoJugador.actualizarEquipoJugador(equipoJugador);
    }


    @Override
    public void validarQueSePuedaModificarEquipo() throws NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException, FechaNoEncontradaException {
        Fecha fechaActual = servicioFecha.obtenerFechaActual();

        if (fechaActual.getEstadoFecha() == EstadoFecha.EN_CURSO) {
            throw new NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException("No se puede modificar el equipo porque la fecha " + fechaActual.getNumeroDeFecha() + " está en curso."
            );
        }
    }

    @Override
    public Boolean puedeModificarEquipo() {
        try {
            validarQueSePuedaModificarEquipo();
            return true;
        } catch (NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException | FechaNoEncontradaException e) {
            return false;
        }
    }

    @Override
    public void copiarEquiposDeUnaFechaAOtra(Fecha fechaAnterior, Fecha fechaNueva) {
        List<EquipoJugador> jugadoresFechaAnterior = repositorioEquipoJugador.buscarPorFechaId(fechaAnterior.getId());

        for (EquipoJugador equipoJugadorAnterior : jugadoresFechaAnterior) {

            EquipoJugador equipoJugadorNuevo = new EquipoJugador();

            equipoJugadorNuevo.setEquipo(equipoJugadorAnterior.getEquipo());

            equipoJugadorNuevo.setJugador(equipoJugadorAnterior.getJugador());

            equipoJugadorNuevo.setNumeroOrden(equipoJugadorAnterior.getNumeroOrden());

            equipoJugadorNuevo.setPosicionDelJugador(equipoJugadorAnterior.getPosicionDelJugador());

            equipoJugadorNuevo.setFecha(fechaNueva);

            repositorioEquipoJugador.guardarEquipoJugador(equipoJugadorNuevo);
        }
    }


    @Override
    public Double calcularPuntajeTotalDelEquipo(Long equipoId) {
        List<EquipoJugador> jugadoresDelEquipo = repositorioEquipoJugador.buscarPorEquipoId(equipoId);
        if (jugadoresDelEquipo == null || jugadoresDelEquipo.isEmpty()) return 0.0;

        Long torneoEquipoId = jugadoresDelEquipo.get(0).getEquipo().getTorneo().getId();
        Torneo torneoEquipo = repositorioTorneo.buscarTorneoPorId(torneoEquipoId);
        Long torneoRealId = torneoEquipoId;
        if (torneoEquipo != null && torneoEquipo.getTipoTorneo() == TipoTorneo.VIRTUAL) {
            List<Torneo> mismos = repositorioTorneo.obtenerTorneosPorTemporada(torneoEquipo.getTemporada().getId());
            for (Torneo t : mismos) {
                if (t.getTipoTorneo() == TipoTorneo.REAL) {
                    torneoRealId = t.getId();
                    break;
                }
            }
        }

        double total = 0.0;
        for (EquipoJugador eqj : jugadoresDelEquipo) {
            RendimientoJugador rend = repositorioJugador.buscarRendimientoPorJugadorYTorneo(eqj.getJugador().getId(), torneoRealId);
            if (rend == null) continue;

            double base = rend.getPuntos()
                    + 1.2 * rend.getRebotes()
                    + 1.5 * rend.getAsistencias()
                    + 3.0 * rend.getRobos()
                    + 3.0 * rend.getBloqueos()
                    - 2.0 * rend.getPerdidas();

            double multiplicador;
            switch (eqj.getPosicionDelJugador()) {
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

            total += base * multiplicador;
        }
        return total;
    }


    @Override
    public List<Equipo> obtenerTopEquiposPorTorneo(Long torneoId, int limite) {
        List<Equipo> equipos = repositorioEquipo.buscarEquiposPorTorneo(torneoId);
        if (equipos == null || equipos.isEmpty()) return List.of();

        equipos.sort((a, b) -> Double.compare(
                calcularPuntajeTotalDelEquipo(b.getId()),
                calcularPuntajeTotalDelEquipo(a.getId())));
        List<Equipo> top = equipos.subList(0, Math.min(limite, equipos.size()));

        for (Equipo e : top) {
            e.setPuntaje(calcularPuntajeTotalDelEquipo(e.getId()));
        }
        return top;
    }

    @Override
    public List<Equipo> obtenerTopEquiposPorFecha(Long fechaId, int limite) {
        Fecha fecha;
        try {
            fecha = servicioFecha.obtenerFechaPorId(fechaId);
        } catch (FechaNoEncontradaException e) {
            return List.of();
        }

        Torneo torneoReal = fecha.getTorneo();
        Torneo torneoVirtual = null;
        List<Torneo> mismos = repositorioTorneo.obtenerTorneosPorTemporada(torneoReal.getTemporada().getId());
        for (Torneo t : mismos) {
            if (t.getTipoTorneo() == TipoTorneo.VIRTUAL) {
                torneoVirtual = t;
                break;
            }
        }
        if (torneoVirtual == null) return List.of();

        List<Equipo> equipos = repositorioEquipo.buscarEquiposPorTorneo(torneoVirtual.getId());
        if (equipos == null || equipos.isEmpty()) return List.of();

        for (Equipo equipo : equipos) {
            List<EquipoJugador> jugadoresEnFecha = repositorioEquipoJugador.buscarPorEquipoIdYFechaId(equipo.getId(), fechaId);
            if (jugadoresEnFecha == null || jugadoresEnFecha.isEmpty()) {
                equipo.setPuntaje(0.0);
                continue;
            }

            double total = 0.0;
            for (EquipoJugador eqj : jugadoresEnFecha) {
                Long jugadorId = eqj.getJugador().getId();
                List<EventoPartido> eventos = repositorioEventoPartido.buscarEventosPorJugadorYFecha(jugadorId, fechaId);
                if (eventos == null || eventos.isEmpty()) continue;

                int puntos = 0, rebotes = 0, asistencias = 0;
                int robos = 0, bloqueos = 0, perdidas = 0;

                for (EventoPartido e : eventos) {
                    switch (e.getTipoEstadistica()) {
                        case TIRO_LIBRE: puntos += 1; break;
                        case DOBLE:      puntos += 2; break;
                        case TRIPLE:     puntos += 3; break;
                        case REBOTE:     rebotes++;   break;
                        case ASISTENCIA: asistencias++; break;
                        case ROBO:       robos++;     break;
                        case TAPA:       bloqueos++;  break;
                        case PERDIDA:
                        case FALTA_PERSONAL: perdidas++; break;
                    }
                }

                double base = puntos + 1.2 * rebotes + 1.5 * asistencias
                        + 3.0 * robos + 3.0 * bloqueos - 2.0 * perdidas;

                double multiplicador;
                switch (eqj.getPosicionDelJugador()) {
                    case CAPITAN:      multiplicador = 2.0; break;
                    case SEXTO_HOMBRE: multiplicador = 0.8; break;
                    case SUPLENTE:     multiplicador = 0.5; break;
                    default:           multiplicador = 1.0; break;
                }

                total += base * multiplicador;
            }

            equipo.setPuntaje(total);
        }

        equipos.sort((a, b) -> Double.compare(b.getPuntaje(), a.getPuntaje()));
        return equipos.subList(0, Math.min(limite, equipos.size()));
    }

}









