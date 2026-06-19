package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.EquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioFormacionSabriImpl implements ServicioFormacionSabri {

    private RepositorioFormacionSabri repositorioFormacionSabri;
    private RepositorioPartidoNBA repositorioPartidoNBA;
    private RepositorioJugador repositorioJugador;
    private RepositorioEquipoNBA repositorioEquipoNBA;
    private RepositorioEquipoNBAJugador repositorioEquipoNBAJugador;


    @Autowired
    public ServicioFormacionSabriImpl(
            RepositorioFormacionSabri repositorioFormacionSabri,
            RepositorioPartidoNBA repositorioPartidoNBA,
            RepositorioJugador repositorioJugador,
            RepositorioEquipoNBA repositorioEquipoNBA,
            RepositorioEquipoNBAJugador repositorioEquipoNBAJugador) {

        this.repositorioFormacionSabri = repositorioFormacionSabri;
        this.repositorioPartidoNBA = repositorioPartidoNBA;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoNBA = repositorioEquipoNBA;
        this.repositorioEquipoNBAJugador = repositorioEquipoNBAJugador;
    }

    @Override
    public void registrarJugador(Long idPartido, Long idEquipo, Long idJugador) throws EquipoNoEncontradoException, PartidoNoEncontradoException, JugadorNoEncontradoException, EquipoNoParticipaEnPartidoException, JugadorNoPerteneceAlEquipoException, FormacionDuplicadaException {

        PartidoNBA partidoNBA = repositorioPartidoNBA.buscarPorId(idPartido);
        EquipoNBA equipoNBA = repositorioEquipoNBA.buscarEquipoPorId(idEquipo);
        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        // valido que existan el partido, el equipo y el jugador
        if (partidoNBA == null) {
            throw new PartidoNoEncontradoException("El Partido que intentas registrar no existe");
        }

        if (equipoNBA == null) {
            throw new EquipoNoEncontradoException("El equipo que intentas registrar no existe");
        }

        if (jugador == null) {
            throw new JugadorNoEncontradoException("El jugador que intentas registrar no existe");
        }

        // Valido que el equipo participe en el partido

        boolean participa = partidoNBA.getEquipoLocal().equals(equipoNBA) || partidoNBA.getEquipoVisitante().equals(equipoNBA);

        if (!participa) {
            throw new EquipoNoParticipaEnPartidoException("El equipo que intentas registrar no participa en ese partido");
        }

        //Valido que el jugador pertenezca al equipoNBA de esa temporada.

        Long idTemporada = partidoNBA.getTorneo().getId();


        EquipoNBAJugador pertenece = repositorioEquipoNBAJugador.buscarEquipoJugadorYTemporada(idEquipo, idJugador, idTemporada);

        if (pertenece == null) {
            throw new JugadorNoPerteneceAlEquipoException("El jugador no pertenece al equipo seleccionado");
        }

        //Valido que la formacion no este duplicada o sea que el jugador no haya sido agregado a ese partido

        if (repositorioFormacionSabri.existeJugadorEnFormacion(idJugador, idPartido)) {
            throw new FormacionDuplicadaException("El jugador ya fue registrado en ese partido");
        }

        Formacion nuevaFormacion = new Formacion();

        nuevaFormacion.setJugador(jugador);
        nuevaFormacion.setEquipoNBA(equipoNBA);
        nuevaFormacion.setPartido(partidoNBA);

        repositorioFormacionSabri.guardarFormacion(nuevaFormacion);


    }

    @Override
    public List<Formacion> obtenerFormacionPorPartido(Long idPartido) {
        return repositorioFormacionSabri.obtenerJugadoresPorPartido(idPartido);
    }

    @Override
    public List<Formacion> obtenerFormacionPorPartidoYEquipo(Long idPartido, Long idEquipo) {
        return repositorioFormacionSabri.buscarJugadoresPorEquipoYPartido(idEquipo, idPartido);
    }

    @Override
    public Boolean existeJugadorEnFormacion(Long idJugador, Long idPartido) {
        return repositorioFormacionSabri.existeJugadorEnFormacion(idJugador, idPartido);
    }
}
