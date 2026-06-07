package com.tallerwebi.dominio;

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

    @Autowired
    public ServicioEquipoNBAJugadorImpl(RepositorioEquipoNBAJugador repositorioEquipoNBAJugador, RepositorioJugador repositorioJugador) {
        this.repositorioEquipoNBAJugador = repositorioEquipoNBAJugador;
        this.repositorioJugador = repositorioJugador;
    }


    @Override
    public List<Jugador> obtenerJugadoresDelEquipoPorId(Long id) {
        List<EquipoNBAJugador> jugadoresAsignados = repositorioEquipoNBAJugador.buscarJugadoresDelEquipoNBA(id);

        List<Jugador> plantel = new ArrayList<>();

        for (EquipoNBAJugador equipoNBAJugador : jugadoresAsignados) {
            plantel.add(equipoNBAJugador.getJugador());
        }
        return plantel;
    }


    @Override
    public List<Jugador> obtenerJugadoresDisponibles() {

        List<Jugador> listadoDeTodosLosJugadores = repositorioJugador.buscarTodosLosJugadores();
        List<EquipoNBAJugador> jugadoresAsignados = repositorioEquipoNBAJugador.buscarTodasLasAsignaciones();

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
}


