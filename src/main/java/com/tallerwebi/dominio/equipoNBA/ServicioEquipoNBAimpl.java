package com.tallerwebi.dominio.equipoNBA;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.RepositorioJugador;
import com.tallerwebi.dominio.equipoNBAJugador.EquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.JugadorYaExisteEnLaTemporadaException;
import com.tallerwebi.dominio.temporada.Temporada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tallerwebi.dominio.temporada.ServicioTemporada;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoNBAimpl implements ServicioEquipoNBA {

    private final RepositorioEquipoNBA repositorioEquipoNba;
    private final RepositorioJugador repositorioJugador;
    private final RepositorioEquipoNBAJugador repositorioEquipoNBAJugador;
    private final ServicioTemporada servicioTemporada;

    @Autowired
    public ServicioEquipoNBAimpl(RepositorioEquipoNBA repositorioEquipoNba, RepositorioJugador repositorioJugador, RepositorioEquipoNBAJugador repositorioEquipoNBAJugador, ServicioTemporada servicioTemporada) {
        this.repositorioEquipoNba = repositorioEquipoNba;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoNBAJugador = repositorioEquipoNBAJugador;
        this.servicioTemporada = servicioTemporada;
    }

    @Override
    public void agregarJugadorAlEquipo(Long idEquipo, Long idJugador)
            throws EquipoNoEncontradoException, JugadorYaExisteEnLaTemporadaException {

        EquipoNBA equipoNBA = buscarEquipoPorId(idEquipo);
        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        Temporada temporadaActual = servicioTemporada.obtenerTemporadaActual();

        if (repositorioEquipoNBAJugador.jugadorPerteneceAUnEquipoEnLaTemporada(idJugador, temporadaActual.getId())) {
            throw new JugadorYaExisteEnLaTemporadaException();
        }

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipoNBA);
        asignacion.setJugador(jugador);
        asignacion.setTemporada(temporadaActual);

        repositorioEquipoNBAJugador.asignarJugadorAUnEquipo(asignacion);
    }

    @Override
    public void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException {

        EquipoNBA equipo = repositorioEquipoNba.buscarEquipoPorId(idEquipo);
        if (equipo == null) {
            throw new EquipoNoEncontradoException("No existe el equipo con id: " + idEquipo);
        }
        EquipoNBAJugador equipoNBAJugador = repositorioEquipoNBAJugador.buscarEquipoYJugadorAsociado(idEquipo, idJugador);

        repositorioEquipoNBAJugador.eliminarJugadorDelEquipo(equipoNBAJugador);
    }

    @Override
    public void guardarEquipoNBA(EquipoNBA equipo) {

        repositorioEquipoNba.crearEquipo(equipo);
    }

    @Override
    public EquipoNBA buscarEquipoPorId(Long id) throws EquipoNoEncontradoException {
        EquipoNBA equipoNBA = repositorioEquipoNba.buscarEquipoPorId(id);

        if (equipoNBA == null) {
            throw new EquipoNoEncontradoException("No existe el equipo con id: " + id);
        }
        return equipoNBA;
    }


    @Override
    public List<EquipoNBA> obtenerTodosLosEquiposOrdenadosDeMenorAMayor() {
        return repositorioEquipoNba.obtenerTodosLosEquiposOrdenados();
    }

    @Override
    public void eliminarEquipoNBA(Long idEquipo) throws EquipoNoEncontradoException {

        EquipoNBA equipo = repositorioEquipoNba.buscarEquipoPorId(idEquipo);

        if (equipo == null) {
            throw new EquipoNoEncontradoException("No existe el equipo con id: " + idEquipo);
        }

        repositorioEquipoNBAJugador.eliminarTodasLasAsignacionesDelEquipo(idEquipo);
        repositorioEquipoNba.eliminar(equipo);
    }


}
