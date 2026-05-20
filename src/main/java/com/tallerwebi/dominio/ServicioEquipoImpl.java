package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoTitularSinCompletarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoImpl implements ServicioEquipo {

    private RepositorioEquipo repositorioEquipo;
    private RepositorioJugador repositorioJugador;
    private RepositorioEquipoJugador repositorioEquipoJugador;


    @Autowired
    public ServicioEquipoImpl(RepositorioEquipo repositorioEquipo, RepositorioJugador repositorioJugador,
                              RepositorioEquipoJugador repositorioEquipoJugador) {
        this.repositorioEquipo = repositorioEquipo;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoJugador = repositorioEquipoJugador;
    }


    @Override
    public Equipo guardarEquipo(Equipo equipo) {
        repositorioEquipo.guardarEquipo(equipo);
        return equipo;
        /*
        Devuelve Equipo porq necesito recuperar el ID en el controlador
         */
    }

    @Override
    public Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException {

        Equipo equipo = repositorioEquipo.buscarEquipoPorId(id);

        if (equipo == null) {
            throw new EquipoNoEncontradoException("No se encuentra el equipo asociado a ese id");
        }
        return equipo;

    }

    @Override
    public Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException {

        Equipo equipo = repositorioEquipo.buscarEquipoPorNombre(nombre);

        if (equipo == null) {
            throw new EquipoNoEncontradoException("No se encuentra el equipo con ese nombre");
        }

        return equipo;

    }


    @Override
    public void guardarEquipoCompleto(Long idEquipo, List<Long> idsJugadores) throws EquipoTitularSinCompletarException, EquipoNoEncontradoException {
        Equipo equipo = repositorioEquipo.buscarEquipoPorId(idEquipo);

        if (equipo == null) {
            throw new EquipoNoEncontradoException("No se encuentra el equipo con ese id");
        }

        validarTitular(idsJugadores);

        for (int i = 0; i < idsJugadores.size(); i++) {
            Long idJugador = idsJugadores.get(i);

            if (idJugador != null) {
                /*el i+1 es para el numero de orden*/
                guardarRelacionEntreEquipoYJugador(equipo, idJugador, i + 1);
            }
        }
    }

    private void validarTitular(List<Long> idsJugadores) {

        if (idsJugadores == null || idsJugadores.size() < 5) {
            throw new EquipoTitularSinCompletarException("Debes seleccionar los 5 jugadores titulares");
        }

        for (int i = 0; i < 5; i++) {
            if (idsJugadores.get(i) == null) {
                throw new EquipoTitularSinCompletarException("Debes seleccionar los 5 jugadores titulares");
            }
        }
    }


    @Override
    public List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo) {
        return repositorioEquipoJugador.buscarPorEquipoId(idEquipo);
    }

    /* Para crear un objeto de tipo EquipoJugador
     * como se tiene que usar muchas veces en el metodo GuardarEquipoCompleto, se hace el metodo para no repetir tanto codigo
     */


    private void guardarRelacionEntreEquipoYJugador(Equipo equipo, Long idJugador, Integer numeroOrden) {
        /*Si un jugador es null, porq los suplentes pueden venir vacios corta el flujo
        para evitar que se guarde como null, y continuá lamando al siguiente metodo/
         */
        if (idJugador == null) {
            return;
        }

        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);
        EquipoJugador equipoJugador = new EquipoJugador(equipo, jugador, numeroOrden);
        repositorioEquipoJugador.guardarEquipoJugador(equipoJugador);
    }

}






