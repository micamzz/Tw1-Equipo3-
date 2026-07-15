package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service("servicioMercado")
@Transactional
public class ServicioMercadoImpl implements ServicioMercado {

    private final RepositorioJugador repositorioJugador;
    private final RepositorioEventoPartido repositorioEventoPartido;

    @Autowired
    public ServicioMercadoImpl(RepositorioJugador repositorioJugador,
                               RepositorioEventoPartido repositorioEventoPartido) {
        this.repositorioJugador = repositorioJugador;
        this.repositorioEventoPartido = repositorioEventoPartido;
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

    @Override
    public RendimientoJugador obtenerRendimiento(long jugadorId) {
        return repositorioJugador.buscarRendimientoPorJugador(jugadorId);
    }

    @Override
    public List<RendimientoJugador> obtenerRendimientosPorPartido(long jugadorId) {
        return repositorioJugador.buscarRendimientosPorJugadorConPartido(jugadorId);
    }

    @Override
    public double calcularPuntajeJugador(RendimientoJugador rendimiento) {
        return rendimiento.getPuntos()
                + 1.2 * rendimiento.getRebotes()
                + 1.5 * rendimiento.getAsistencias()
                + 3.0 * rendimiento.getRobos()
                + 3.0 * rendimiento.getBloqueos()
                - 2.0 * rendimiento.getPerdidas();
    }

    @Override
    public Jugador buscarJugadorPorId(long id) {
        return repositorioJugador.buscarJugadorPorId(id);
    }

    @Override
    public RendimientoJugador obtenerRendimientoPorJugadorYTorneo(Long jugadorId, Long torneoId) {
        return repositorioJugador.buscarRendimientoPorJugadorYTorneo(jugadorId, torneoId);
    }

    @Override
    public List<RendimientoJugador> obtenerRendimientosPorTorneo(Long torneoId) {
        return repositorioJugador.buscarRendimientosPorTorneo(torneoId);
    }

    @Override
    public List<RendimientoJugador> obtenerTopJugadoresPorTorneo(Long torneoId, int limite) {
        List<RendimientoJugador> todos = repositorioJugador.buscarRendimientosPorTorneo(torneoId);
        if (todos == null || todos.isEmpty()) return List.of();
        todos.sort((a, b) -> Double.compare(calcularPuntajeJugador(b), calcularPuntajeJugador(a)));
        return todos.subList(0, Math.min(limite, todos.size()));
    }

    @Override
    public List<EventoPartido> buscarEventosPorJugadorYFecha(Long jugadorId, Long fechaId) {
        return repositorioEventoPartido.buscarEventosPorJugadorYFecha(jugadorId, fechaId);
    }

    @Override
    public List<EventoPartido> buscarEventosPorJugadorTorneo(Long jugadorId, Long torneoId) {
        return repositorioEventoPartido.buscarEventosPorJugadorTorneo(jugadorId, torneoId);
    }
}
