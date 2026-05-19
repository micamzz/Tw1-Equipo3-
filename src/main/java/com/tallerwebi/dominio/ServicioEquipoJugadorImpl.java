package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoJugadorNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

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
        List<EquipoJugador> jugadoresPorEquipo = repositorioEquipoJugador.buscarPorEquipoId(id);
        if (jugadoresPorEquipo == null) {
            throw new EquipoJugadorNoEncontradoException("No se encuentra el equipo");
        }
        HashMap<Integer, Jugador> listaJugadores = new HashMap<>();
            for (EquipoJugador jugador : jugadoresPorEquipo) {
                listaJugadores.put(jugador.getNumeroOrden(), jugador.getJugador());
            }
        return listaJugadores;
    }
}
