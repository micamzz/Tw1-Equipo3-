package com.tallerwebi.dominio.equipoJugador;

import com.tallerwebi.dominio.*;
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
    private final ServicioFecha servicioFecha;

    @Autowired
    public ServicioEquipoJugadorImpl(RepositorioEquipoJugador repositorioEquipoJugador, RepositorioJugador repositorioJugador, ServicioFecha servicioFecha) {
        this.repositorioEquipoJugador = repositorioEquipoJugador;
        this.repositorioJugador = repositorioJugador;
        this.servicioFecha = servicioFecha;
    }


    @Override
    public HashMap<Integer, EquipoJugador> buscarJugadoresPorEquipoId(Long id) {

        Fecha fechaActual = servicioFecha.obtenerFechaActual();

        HashMap<Integer, EquipoJugador> listaJugadores = new HashMap<>();

        if (fechaActual == null) {
            return listaJugadores;
        }

        List<EquipoJugador> jugadoresPorEquipo = repositorioEquipoJugador.buscarPorEquipoIdYFechaId(id, fechaActual.getId()
        );

        for (EquipoJugador jugador : jugadoresPorEquipo) {
            listaJugadores.put(jugador.getNumeroOrden(), jugador);
        }
        return listaJugadores;
    }

    @Override
    public List<Jugador> obtenerJugadoresDisponiblesPorPosicion(Long idEquipo, Posicion posicion) {

        Fecha fechaActual = servicioFecha.obtenerFechaActual();

        List<Jugador> jugadoresPorPosicion = repositorioJugador.buscarJugadores(posicion, null);

        if (fechaActual == null) {
            return jugadoresPorPosicion;
        }

        List<EquipoJugador> jugadoresDelEquipo = repositorioEquipoJugador.buscarPorEquipoIdYFechaId(idEquipo, fechaActual.getId());

        List<Jugador> jugadoresDisponibles = new ArrayList<>();

        for (Jugador jugador : jugadoresPorPosicion) {
            boolean estaSeleccionado = false;

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






