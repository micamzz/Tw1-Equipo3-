package com.tallerwebi.dominio;


import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ServicioEventoPartidoImpl implements ServicioEventoPartido {

    private final RepositorioEventoPartido repositorioEventoPartido;
    private final RepositorioPartidoNBA repositorioPartidoNBA;
    private final RepositorioJugador repositorioJugador;
    private final RepositorioFormacion repositorioFormacion;


    @Autowired
    public ServicioEventoPartidoImpl(RepositorioEventoPartido repositorioEventoPartido
            , RepositorioPartidoNBA repositorioPartidoNBA
            , RepositorioJugador repositorioJugador
            , RepositorioFormacion repositorioFormacion) {
        this.repositorioEventoPartido = repositorioEventoPartido;
        this.repositorioPartidoNBA = repositorioPartidoNBA;
        this.repositorioJugador = repositorioJugador;
        this.repositorioFormacion = repositorioFormacion;

    }

    // Metodo que voy a usar para registrarEvento, que valida si el momento en el que voy a registrar el evento existe dentro del partido

    private void validarMomentoPartido(LocalTime momentoPartido, PartidoNBA partido) throws MomentoPartidoInvalidoException, PartidoNoEnCursoException {
        if (momentoPartido == null) {
            throw new MomentoPartidoInvalidoException("El momento del partido es necesario");
        }

        /* -- Esta validacion la voy a hacer cuando tenga hecha la validacion de que solo
        puedo agregar eventos si el partido esta EN VIVO, actualmente esta en los
        partidos PROGRAMADOS. */

        if (partido.getEstadoPartido() != EstadoPartido.EN_VIVO) {
            throw new PartidoNoEnCursoException("El partido no esta en curso");
        }

    }

    @Override
    public void registrarEvento(Long idPartido, Long idJugador, LocalTime momentoPartido, TipoEstadistica tipoEstadistica, Boolean esLocal) throws PartidoNoEncontradoException, JugadorNoEncontradoException, JugadorNoConvocadoException, MomentoPartidoInvalidoException, PartidoNoEnCursoException {

        // Valido que exista el partido
        PartidoNBA partidoNBA = repositorioPartidoNBA.buscarPorId(idPartido);

        if (partidoNBA == null) {
            throw new PartidoNoEncontradoException("El partido no existe");
        }

        // Valido que exista el jugador
        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        if (jugador == null) {
            throw new JugadorNoEncontradoException("El jugador no existe");
        }

        // Valido que el jugador este convocado al partido
        if (!repositorioFormacion.jugadorYaEstaEnFormacion(idPartido, idJugador)) {
            throw new JugadorNoConvocadoException("El jugador no forma parte del partido");
        }

        // Valido que el momento del evento no sea mayor a la duracion del partido
        validarMomentoPartido(momentoPartido, partidoNBA);

        EventoPartido evento = new EventoPartido();

        evento.setPartido(partidoNBA);
        evento.setJugador(jugador);
        evento.setMomentoPartido(momentoPartido);
        evento.setTipoEstadistica(tipoEstadistica);
        evento.setEsLocal(esLocal);

        EquipoNBA equipo = repositorioFormacion.buscarEquipo(idPartido, idJugador);

        repositorioEventoPartido.guardarEventoPartido(evento);

    }

    @Override
    public List<EventoPartido> buscarEventosPorPartido(Long idPartido) {
        return repositorioEventoPartido.buscarEventosPorPartido(idPartido);
    }

    @Override
    public void eliminarEventoPorId(Long idEvento) throws EventoNoEncontradoException {
        EventoPartido evento = repositorioEventoPartido.buscarEventoPorId(idEvento);
        if (evento != null) {
            repositorioEventoPartido.eliminarEvento(evento);
        } else {
            throw new EventoNoEncontradoException ("No se encontro el evento con id: " + idEvento);
        }
    }


}
