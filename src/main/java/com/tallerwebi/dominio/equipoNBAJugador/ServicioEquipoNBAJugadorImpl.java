package com.tallerwebi.dominio.equipoNBAJugador;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoNBAJugadorImpl implements ServicioEquipoNBAJugador {

    private final RepositorioEquipoNBAJugador repositorioEquipoNBAJugador;
    private final RepositorioEquipoNBA repositorioEquipoNBA;
    private final RepositorioJugador repositorioJugador;
    private final ServicioTorneo servicioTorneo;

    @Autowired
    public ServicioEquipoNBAJugadorImpl(RepositorioEquipoNBAJugador repositorioEquipoNBAJugador, RepositorioEquipoNBA repositorioEquipoNBA, RepositorioJugador repositorioJugador, ServicioTorneo servicioTorneo) {
        this.repositorioEquipoNBAJugador = repositorioEquipoNBAJugador;
        this.repositorioEquipoNBA = repositorioEquipoNBA;
        this.repositorioJugador = repositorioJugador;
        this.servicioTorneo = servicioTorneo;
    }

    @Override
    public void agregarJugadorAlEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException {

        EquipoNBA equipoNBA = repositorioEquipoNBA.buscarEquipoPorId(idEquipo);
        if (equipoNBA == null) {
            throw new EquipoNoEncontradoException("El equipo no existe");
        }
        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);

        if (repositorioEquipoNBAJugador.jugadorPerteneceAUnEquipoEnElTorneo(idJugador, torneoActual.getId())) {
            throw new elJugadorYaExisteEnElEquipoException("El jugador ya pertenece a un equipo");
        }

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipoNBA);
        asignacion.setJugador(jugador);
        asignacion.setTorneo(torneoActual);

        repositorioEquipoNBAJugador.asignarJugadorAUnEquipo(asignacion);
    }

    @Override
    public void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException {
        EquipoNBA equipo = repositorioEquipoNBA.buscarEquipoPorId(idEquipo);
        if (equipo == null) {
            throw new EquipoNoEncontradoException("No existe el equipo con id: " + idEquipo);
        }
        EquipoNBAJugador equipoNBAJugador = repositorioEquipoNBAJugador.buscarEquipoYJugadorAsociado(idEquipo, idJugador);

        repositorioEquipoNBAJugador.eliminarJugadorDelEquipo(equipoNBAJugador);
    }


    @Override
    public List<Jugador> obtenerJugadoresDelEquipoPorId(Long id) {

        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);
        List<EquipoNBAJugador> jugadoresAsignados = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBAEnTorneo(id, torneoActual.getId());

        List<Jugador> listadoPlantel = new ArrayList<>();

        for (EquipoNBAJugador equipoNBAJugador : jugadoresAsignados) {
            listadoPlantel.add(equipoNBAJugador.getJugador());
        }
        return listadoPlantel;
    }


    @Override
    public List<Jugador> obtenerJugadoresDisponibles() {

        Torneo torneoActual = servicioTorneo.obtenerTorneoActual(TipoTorneo.REAL);

        List<Jugador> listadoDeTodosLosJugadores = repositorioJugador.buscarTodosLosJugadores();

        List<EquipoNBAJugador> jugadoresAsignados = repositorioEquipoNBAJugador.buscarAsignacionesPorTorneo(torneoActual.getId());

        List<Jugador> jugadoresDisponibles = new ArrayList<>();

        List<Long> idsYaAsignados = new ArrayList<>();

        for (EquipoNBAJugador asignacion : jugadoresAsignados) {
            idsYaAsignados.add(asignacion.getJugador().getId());
        }

        for (Jugador jugador : listadoDeTodosLosJugadores) {
            if (!idsYaAsignados.contains(jugador.getId())) {
                jugadoresDisponibles.add(jugador);
            }
        }

        return jugadoresDisponibles;
    }


    /* Método que devuelve los jugadores que no fueron asignados a ningun equipoNBA
    y que ademas cumplen con los filtros para usar en el buscador o en select */

    @Override
    public List<Jugador> obtenerJugadoresFiltrados(Posicion posicionEnum, String nombre) {

        List<Jugador> jugadoresDisponibles = obtenerJugadoresDisponibles();
        List<Jugador> jugadoresFiltrados = new ArrayList<>();

        for (Jugador jugador : jugadoresDisponibles) {

            if (posicionEnum == null || jugador.getPosicion().equals(posicionEnum)) {

                if (nombre == null || nombre.trim().isEmpty() || jugador.getNombre().toLowerCase().contains(nombre.toLowerCase()) || jugador.getApellido().toLowerCase().contains(nombre.toLowerCase())) {
                    jugadoresFiltrados.add(jugador);
                }
            }
        }

        return jugadoresFiltrados;

    }

    @Override
    public List<Jugador> obtenerJugadoresDelEquipoEnTorneo(Long idEquipo, Long idTorneo) {
        List<EquipoNBAJugador> asignaciones = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBAEnTorneo(idEquipo, idTorneo);

        List<Jugador> plantel = new ArrayList<>();

        for (EquipoNBAJugador asignacion : asignaciones) {
            plantel.add(asignacion.getJugador());
        }
        return plantel;
    }


}



