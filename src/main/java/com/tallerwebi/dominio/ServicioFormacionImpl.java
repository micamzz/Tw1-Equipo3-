package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioFormacionImpl implements ServicioFormacion {

    private final RepositorioFormacion repositorioFormacion;
    private final RepositorioJugador repositorioJugador;
    private final RepositorioEquipoNBA repositorioEquipoNBA;
    private final RepositorioPartidoNBA repositorioPartidoNBA;

    @Autowired
    public ServicioFormacionImpl(RepositorioFormacion repositorioFormacion, RepositorioJugador repositorioJugador, RepositorioEquipoNBA repositorioEquipoNBA, RepositorioPartidoNBA repositorioPartidoNBA) {
        this.repositorioFormacion = repositorioFormacion;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoNBA = repositorioEquipoNBA;
        this.repositorioPartidoNBA = repositorioPartidoNBA;
    }

    @Override
    public void agregarJugador(Long idPartido, Long idEquipo, Long idJugador) {
        if (!repositorioFormacion.jugadorYaEstaEnFormacion(idPartido, idJugador)) {
            PartidoNBA partido = repositorioPartidoNBA.buscarPorId(idPartido);
            EquipoNBA equipo = repositorioEquipoNBA.buscarEquipoPorId(idEquipo);
            Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

            FormacionPartido formacion = new FormacionPartido();

            formacion.setPartido(partido);
            formacion.setEquipo(equipo);
            formacion.setJugador(jugador);

            repositorioFormacion.guardar(formacion);
        }
    }

    @Override
    public void quitarJugador(Long idFormacion) {
        repositorioFormacion.eliminar(idFormacion);
    }

    @Override
    public List<FormacionPartido> obtenerFormacion(Long idPartido) {
        return repositorioFormacion.buscarPorPartido(idPartido);
    }

    @Override
    public List<FormacionPartido> obtenerFormacionPorEquipo(Long idPartido, Long idEquipo) {
        return repositorioFormacion.buscarPorPartidoYEquipo(idPartido, idEquipo);
    }


    @Override
    public boolean jugadorYaEstasEnFormacion(Long idPartido, Long idJugador) {
        return repositorioFormacion.jugadorYaEstaEnFormacion(idPartido, idJugador);
    }

    @Override
    public EquipoRol obtenerRolJugadorEnFormacion(Long idPartido, Long idJugador) {
        PartidoNBA partido = repositorioPartidoNBA.buscarPorId(idPartido);

        FormacionPartido formacion = repositorioFormacion.buscarPorPartidoYJugador(idPartido, idJugador);

        if (formacion == null) {
            return null; // el jugador no esta en la formacion
        }
        if (formacion.getEquipo().getId().equals(partido.getEquipoLocal().getId())) {
            return EquipoRol.LOCAL;
        } else {
            return EquipoRol.VISITANTE;
        }

        @Override
        public boolean partidoTieneJugadoresEnFormacion(Long idPartido) { //cada equipo necesita al menos 5 jugadores para iniciar el partido
           PartidoNBA partido = repositorioPartidoNBA.buscarPorId(idPartido);
           List<FormacionPartido> formacionLocal = repositorioFormacion.buscarPorPartidoYEquipo(idPartido, partido.getEquipoLocal().getId());
           List<FormacionPartido> formacionVisitante = repositorioFormacion.buscarPorPartidoYEquipo(idPartido, partido.getEquipoVisitante().getId());
            return formacionLocal.size()>=5 && formacionVisitante.size()>=5;
        }

    }

}


