package com.tallerwebi.dominio.equipoNBAJugador;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.RepositorioJugador;
import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;
import com.tallerwebi.dominio.temporada.ServicioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoNBAJugadorImpl implements ServicioEquipoNBAJugador {

    private final RepositorioEquipoNBAJugador repositorioEquipoNBAJugador;
    private final RepositorioJugador repositorioJugador;
    private final ServicioTemporada servicioTemporada;

    @Autowired
    public ServicioEquipoNBAJugadorImpl(RepositorioEquipoNBAJugador repositorioEquipoNBAJugador, RepositorioJugador repositorioJugador, ServicioTemporada servicioTemporada) {
        this.repositorioEquipoNBAJugador = repositorioEquipoNBAJugador;
        this.repositorioJugador = repositorioJugador;
        this.servicioTemporada = servicioTemporada;
    }


    @Override
    public List<Jugador> obtenerJugadoresDelEquipoPorId(Long id) throws TemporadaActualNoEncontradaException {
        Temporada temporadaActual = servicioTemporada.obtenerTemporadaActual();
        List<EquipoNBAJugador> jugadoresAsignados = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBAEnTemporada(id, temporadaActual.getId());


        List<Jugador> plantel = new ArrayList<>();

        for (EquipoNBAJugador equipoNBAJugador : jugadoresAsignados) {
            plantel.add(equipoNBAJugador.getJugador());
        }
        return plantel;
    }


    @Override
    public List<Jugador> obtenerJugadoresDisponibles() throws TemporadaActualNoEncontradaException {

        Temporada temporadaActual = servicioTemporada.obtenerTemporadaActual();

        List<Jugador> listadoDeTodosLosJugadores = repositorioJugador.buscarTodosLosJugadores();
        List<EquipoNBAJugador> jugadoresAsignados = repositorioEquipoNBAJugador.buscarAsignacionesPorTemporada(temporadaActual.getId());

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
    public List<Jugador> obtenerJugadoresFiltrados(Posicion posicionEnum, String nombre) throws TemporadaActualNoEncontradaException {

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
    public List<Jugador> obtenerJugadoresDelEquipoEnTemporada(Long idEquipo, Long idTemporada) {

        List<EquipoNBAJugador> asignaciones = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBAEnTemporada(idEquipo, idTemporada);

        List<Jugador> plantel = new ArrayList<>();

        for (EquipoNBAJugador asignacion : asignaciones) {
            plantel.add(asignacion.getJugador());
        }
        return plantel;
    }


}



