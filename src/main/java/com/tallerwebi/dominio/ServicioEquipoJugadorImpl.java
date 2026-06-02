package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoJugadorImpl implements ServicioEquipoJugador {
    private final RepositorioEquipoJugador repositorioEquipoJugador;
    private final RepositorioJugador repositorioJugador;

    @Autowired
    public ServicioEquipoJugadorImpl(RepositorioEquipoJugador repositorioEquipoJugador, RepositorioJugador repositorioJugador) {
        this.repositorioEquipoJugador = repositorioEquipoJugador;
        this.repositorioJugador = repositorioJugador;
    }

    @Override
    public EquipoJugador guardarEquipoJugador(EquipoJugador equipoJugador) {
        repositorioEquipoJugador.guardarEquipoJugador(equipoJugador);
        return equipoJugador;
    }


    @Override
    public HashMap<Integer, EquipoJugador> buscarJugadoresPorEquipoId(Long id) {
        List<EquipoJugador> jugadoresPorEquipo = repositorioEquipoJugador.buscarPorEquipoId(id);

        HashMap<Integer, EquipoJugador> listaJugadores = new HashMap<>();

        for (EquipoJugador jugador : jugadoresPorEquipo) {
            listaJugadores.put(jugador.getNumeroOrden(), jugador);
        }
        return listaJugadores;
    }

    @Override
    public List<Jugador> obtenerJugadoresDisponiblesPorPosicion(Long idEquipo, Posicion posicion) {
        List<Jugador> jugadoresPorPosicion = repositorioJugador.buscarJugadores(posicion, null);
        List<EquipoJugador> jugadoresDelEquipo = repositorioEquipoJugador.buscarPorEquipoId(idEquipo);

        List<Jugador> jugadoresDisponibles = new ArrayList<>();

        for (Jugador jugador : jugadoresPorPosicion) {
            Boolean estaSeleccionado = false;

            for (EquipoJugador equipoJugador : jugadoresDelEquipo) {
                if (equipoJugador.getJugador().getId().equals(jugador.getId())) {
                    estaSeleccionado = true;
                    break;
                }
            }
            if (!estaSeleccionado) {
                jugadoresDisponibles.add(jugador);
            }
        }
        return jugadoresDisponibles;
    }
}






