package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.JugadorNoConvocadoException;
import com.tallerwebi.dominio.excepcion.JugadorNoEncontradoException;
import com.tallerwebi.dominio.excepcion.MomentoPartidoInvalidoException;
import com.tallerwebi.dominio.excepcion.PartidoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ServicioEventoPartidoImpl implements ServicioEventoPartido{

    private RepositorioEventoPartido repositorioEventoPartido;
    private RepositorioPartidoNBA repositorioPartidoNBA;
    private RepositorioJugador repositorioJugador;
    private ServicioFormacion servicioFormacion;


    @Autowired
    public ServicioEventoPartidoImpl(RepositorioEventoPartido repositorioEventoPartido, RepositorioPartidoNBA repositorioPartidoNBA, RepositorioJugador repositorioJugador, ServicioFormacion servicioFormacion) {
        this.repositorioEventoPartido = repositorioEventoPartido;
        this.repositorioPartidoNBA = repositorioPartidoNBA;
        this.repositorioJugador = repositorioJugador;
        this.servicioFormacion = servicioFormacion;
    }

    // Metodo que voy a usar para registrarEvento, que valida si el momento en el que voy a registrar el evento existe dentro del partido

    private void validarMomentoPartido(LocalTime momentoPartido, PartidoNBA partido) throws MomentoPartidoInvalidoException {
        if (momentoPartido == null) {
            throw new MomentoPartidoInvalidoException("El momento del partido es necesario");
        }

        int minutosTotalesPartido = partido.getMinutoFin();

        LocalTime limite = LocalTime.of(
                minutosTotalesPartido / 60,
                minutosTotalesPartido % 60);

        if (momentoPartido.isAfter(limite)) {
            throw new MomentoPartidoInvalidoException("El momento del evento no puede ser mayor a la duracion del partido");
        }
    }

    @Override
    public void registrarEvento(Long idPartido, Long idJugador, LocalTime momentoPartido, TipoEstadistica tipoEstadistica) throws PartidoNoEncontradoException, JugadorNoEncontradoException, JugadorNoConvocadoException, MomentoPartidoInvalidoException {

        // Valido que exista el partido
        PartidoNBA partidoNBA = repositorioPartidoNBA.buscarPorId(idPartido);

        if(partidoNBA == null){
            throw new PartidoNoEncontradoException("El partido no existe");
        }

        // Valido que exista el jugador
        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        if(jugador == null){
            throw new JugadorNoEncontradoException("El jugador no existe");
        }

        // Valido que el jugador este convocado al partido
        if(!servicioFormacion.existeJugadorEnFormacion(idJugador, idPartido)){
            throw new JugadorNoConvocadoException("El jugador no forma parte del partido");
        }

        // Valido que el momento del evento no sea mayor a la duracion del partido
        validarMomentoPartido(momentoPartido, partidoNBA);

        EventoPartido evento = new EventoPartido();

        evento.setPartido(partidoNBA);
        evento.setJugador(jugador);
        evento.setMomentoPartido(momentoPartido);
        evento.setTipoEstadistica(tipoEstadistica);

    }

    @Override
    public List<EventoPartido> buscarEventosPorPartido(Long idPartido) {
        return repositorioEventoPartido.buscarEventosPorPartido(idPartido);
    }

    @Override
    public List<EventoPartido> buscarEventosPorPartidoYJugador(Long idPartido, Long idJugador) {
        return repositorioEventoPartido.buscarEventosPorPartidoYJugador(idPartido, idJugador);
    }
}
