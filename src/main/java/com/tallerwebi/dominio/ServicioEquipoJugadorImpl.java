package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoJugadorNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@Transactional
public class ServicioEquipoJugadorImpl implements ServicioEquipoJugador {
    private RepositorioEquipoJugador repositorioEquipoJugador;

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
    public HashMap<Integer, Jugador> buscarJugadoresPorEquipoId(Long id) throws EquipoJugadorNoEncontradoException {
        HashMap <Integer, Jugador> jugadoresPorEquipo = repositorioEquipoJugador.buscarJugadoresPorEquipoId(id);
        if (jugadoresPorEquipo == null) {
            throw new EquipoJugadorNoEncontradoException("No se encuentra el equipo");
        }
        return jugadoresPorEquipo;
    }
}
