package com.tallerwebi.dominio.equipo;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.enums.PosicionJugadorEquipo;
import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
import com.tallerwebi.dominio.equipoJugador.RepositorioEquipoJugador;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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


    @Autowired
    public ServicioEquipoImpl(RepositorioEquipo repositorioEquipo, RepositorioJugador repositorioJugador,
                              RepositorioEquipoJugador repositorioEquipoJugador, RepositorioTorneo repositorioTorneo, ServicioPartidoNBA servicioPartidoNBA) {
        this.repositorioEquipo = repositorioEquipo;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoJugador = repositorioEquipoJugador;
        this.repositorioTorneo = repositorioTorneo;
        this.servicioPartidoNBA = servicioPartidoNBA;
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
    public void validarEquipoCompleto(Long idEquipo) throws EquipoSinCompletarException {

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
    public void agregarJugadorAlEquipo(Long idEquipo, Long idJugador, Integer numeroDeOrden) throws EquipoNoEncontradoException, PresupuestoInsuficienteException, elJugadorYaExisteEnElEquipoException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException {
        validarQueSePuedaModificarEquipo();

        Equipo equipo = buscarEquipoPorId(idEquipo);

        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        /* Verifica si el jugador ya está asociado al equipo*/
        validarSiElJugadorYaFueElegido(idEquipo, idJugador);

        // Verifica que el presupuesto alcanze
        siElPresupuestoEsMenorLanzaExcepcion(equipo, jugador);

        // Llama al método que guarda la relación en el repositorioEquipoJugador
        guardarRelacionEntreEquipoYJugador(equipo, jugador, numeroDeOrden);
    }


    @Override
    public void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador)
            throws EquipoNoEncontradoException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException {

        validarQueSePuedaModificarEquipo();

        Equipo equipo = buscarEquipoPorId(idEquipo);

        EquipoJugador equipoJugador = repositorioEquipoJugador.buscarEquipoYJugadorAsociado(idEquipo, idJugador);

        if (equipoJugador != null) {
            equipo.setPresupuesto(equipo.getPresupuesto() + equipoJugador.getJugador().getPrecio());
            repositorioEquipoJugador.eliminarEquipoJugador(equipoJugador);
        }
    }


    /*DEVUELVE UNA LISTA CON TODOS LOS Jugadores QUE ESTAN ASOCIADOS AL EQUIPO*/
    @Override
    public List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo) {
        return repositorioEquipoJugador.buscarPorEquipoId(idEquipo);
    }



    /*  GUARDA AL EQUIPO Y AL JUGADOR EN EQUIPOJUGADOR LLAMANDO AL REPOSITORIO */

    private void guardarRelacionEntreEquipoYJugador(Equipo equipo, Jugador jugador, Integer numeroDeOrden) {
        EquipoJugador equipoJugador = new EquipoJugador();

        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(jugador);
        equipoJugador.setNumeroOrden(numeroDeOrden);

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
    private void validarSiElJugadorYaFueElegido(Long idEquipo, Long idJugador) throws elJugadorYaExisteEnElEquipoException {
        EquipoJugador equipoJugador = repositorioEquipoJugador.buscarEquipoYJugadorAsociado(idEquipo, idJugador);

        if (equipoJugador != null) {
            throw new elJugadorYaExisteEnElEquipoException("El jugador ya esta fichado en el equipo");
        }
    }


    //    Actualiza el enum del jugador ya elegido.
    @Override
    public void asignarRolEspecial(Long idEquipo, Long idJugador, PosicionJugadorEquipo rol)
            throws EquipoNoEncontradoException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException {

        validarQueSePuedaModificarEquipo();

        // Si ya había otro con ese rol, lo resetea
        List<EquipoJugador> todos = repositorioEquipoJugador.buscarPorEquipoId(idEquipo);
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

        // Asigna el rol al jugador elegido
        EquipoJugador equipoJugador = repositorioEquipoJugador.buscarEquipoYJugadorAsociado(idEquipo, idJugador);
        if (equipoJugador == null) {
            throw new EquipoNoEncontradoException("El jugador no pertenece al equipo");
        }
        equipoJugador.setPosicionDelJugador(rol);
        repositorioEquipoJugador.actualizarEquipoJugador(equipoJugador);
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
    public void validarQueSePuedaModificarEquipo() throws NoSePuedeModificarEquipoSiHayPartidosEnCursoException {

        List<PartidoNBA> partidosActivos = servicioPartidoNBA.obtenerPartidosActivos();
        if (partidosActivos != null && !partidosActivos.isEmpty()) {
            throw new NoSePuedeModificarEquipoSiHayPartidosEnCursoException("No se puede modificar el equipo mientras haya partidos en curso.");
        }

        /* Si algún partido programado empieza dentro de la próxima hora, también se bloquea */
        LocalDateTime ahora = LocalDateTime.now();
        List<PartidoNBA> programados = servicioPartidoNBA.obtenerPartidosProgramados();

        if (programados != null) {
            for (PartidoNBA partido : programados) {
                LocalDateTime limiteParaModificar = partido.getHoraInicio().minusHours(1);
                if (!ahora.isBefore(limiteParaModificar) && ahora.isBefore(partido.getHoraInicio())) {
                    throw new NoSePuedeModificarEquipoSiHayPartidosEnCursoException(
                            "No se puede modificar el equipo. Las modificaciones se bloquean una hora antes de cada partido."
                    );
                }
            }
        }
    }

    @Override
    public Boolean puedeModificarEquipo() {
        try {
            validarQueSePuedaModificarEquipo();
            return true;
        } catch (NoSePuedeModificarEquipoSiHayPartidosEnCursoException e) {
            return false;
        }
    }


}









