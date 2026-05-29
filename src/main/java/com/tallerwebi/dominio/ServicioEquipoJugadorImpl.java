package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoJugadorImpl implements ServicioEquipoJugador {
    private final RepositorioEquipoJugador repositorioEquipoJugador;

    @Autowired
    public ServicioEquipoJugadorImpl(RepositorioEquipoJugador repositorioEquipoJugador) {
        this.repositorioEquipoJugador = repositorioEquipoJugador;
    }

    @Override
    public EquipoJugador guardarEquipoJugador(EquipoJugador equipoJugador) {
        repositorioEquipoJugador.guardarEquipoJugador(equipoJugador);
        return equipoJugador;
    }

    @Override
    public EquipoJugador buscarJugadorPorNumeroDeOrden(List<EquipoJugador> jugadores, Integer orden) {
        for (EquipoJugador juga : jugadores) {

            if (juga.getNumeroOrden().equals(orden)) {
                return juga;
            }
        }
        return null;
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
}
