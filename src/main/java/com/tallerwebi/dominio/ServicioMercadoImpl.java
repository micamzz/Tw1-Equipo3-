package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service("servicioMercado")
@Transactional
public class ServicioMercadoImpl implements ServicioMercado {

    private RepositorioJugador repositorioJugador;

    @Autowired
    public ServicioMercadoImpl(RepositorioJugador repositorioJugador) {

        this.repositorioJugador = repositorioJugador;
    }

    @Override
    public List<Jugador> obtenerJugadores(Posicion posicion, String nombre) {
        return repositorioJugador.buscarJugadores(posicion, nombre);
    }

    @Override
    public List<Jugador> buscarAlero() {
        return repositorioJugador.buscarJugadores(Posicion.ALERO, null);
    }

    @Override
    public List<Jugador> buscarPivot() {
        return repositorioJugador.buscarJugadores(Posicion.PIVOT, null);
    }

    @Override
    public List<Jugador> buscarBase() {
        return repositorioJugador.buscarJugadores(Posicion.BASE, null);
    }

<<<<<<< HEAD
    @Override
    public RendimientoJugador obtenerRendimiento(long jugadorId) {
        return repositorioJugador.buscarRendimientoPorJugador(jugadorId);
    }

    @Override
    public double calcularPuntajeJugador(RendimientoJugador rendimiento) {
        return rendimiento.getPuntos()
                +1.2 * rendimiento.getRebotes()
                +1.5 * rendimiento.getAsistencias()
                +3.0 * rendimiento.getRobos()
                +3.0 * rendimiento.getBloqueos()
                -2.0 * rendimiento.getPerdidas();
    }

    @Override
    public Jugador buscarJugadorPorId(long id) {
        return repositorioJugador.buscarJugadorPorId(id);
    }
=======

>>>>>>> origin/main
}
