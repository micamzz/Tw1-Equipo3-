package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoNBAimpl implements ServicioEquipoNBA {

    private final RepositorioEquipoNBA repositorioEquipoNba;
    private final RepositorioJugador repositorioJugador;
    private final RepositorioEquipoNBAJugador repositorioEquipoNBAJugador;

    @Autowired
    public ServicioEquipoNBAimpl(RepositorioEquipoNBA repositorioEquipoNba, RepositorioJugador repositorioJugador, RepositorioEquipoNBAJugador repositorioEquipoNBAJugador) {
        this.repositorioEquipoNba = repositorioEquipoNba;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoNBAJugador = repositorioEquipoNBAJugador;
    }

    @Override
    public void agregarJugadorAlEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException {

        EquipoNBA equipoNBA = buscarEquipoPorId(idEquipo);
        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        EquipoNBAJugador asignacion = new EquipoNBAJugador();
        asignacion.setEquipoNBA(equipoNBA);
        asignacion.setJugador(jugador);

        repositorioEquipoNBAJugador.asignarJugadorAUnEquipo(asignacion);
    }

    @Override
    public void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException {

    }

    @Override
    public void guardarEquipoNBA(EquipoNBA equipo) {

        repositorioEquipoNba.crearEquipo(equipo);
    }

    @Override
    public EquipoNBA buscarEquipoPorId(Long id) throws EquipoNoEncontradoException {
        EquipoNBA equipoNBA = repositorioEquipoNba.buscarEquipoPorId(id);

        if (equipoNBA == null) {
            throw new EquipoNoEncontradoException();
        }
        return equipoNBA;
    }

    @Override
    public EquipoNBA buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException {
        return null;
    }

    @Override
    public List<EquipoNBA> obtenerTodosLosEquipos() {
        return repositorioEquipoNba.obtenerTodosLosEquipos();
    }
}
