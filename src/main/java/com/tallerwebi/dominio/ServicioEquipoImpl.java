package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoTitularSinCompletarException;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
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
        return equipo;/* Devuelve Equipo porq necesito recuperar el ID en el controlador  */
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
    public List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo) {
        return repositorioEquipoJugador.buscarPorEquipoId(idEquipo);
    }


    @Override
    public void guardarEquipoCompleto(Long idEquipo, List<Long> idsJugadores) throws EquipoTitularSinCompletarException, PresupuestoInsuficienteException, EquipoNoEncontradoException {

        Equipo equipo = buscarEquipoPorId(idEquipo);
        validarTitular(idsJugadores);

        for (int i = 0; i < idsJugadores.size(); i++) {
            Long idJugador = idsJugadores.get(i);

            if (idJugador != null) {
                /*el i+1 es para el numero de orden*/
                Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

                siElPresupuestoEsMenorLanzaExcepcion(equipo, jugador);
                guardarRelacionEntreEquipoYJugador(equipo, jugador, i + 1);
            }
        }
    }

    private void siElPresupuestoEsMenorLanzaExcepcion(Equipo equipo, Jugador jugador) throws PresupuestoInsuficienteException {
        if (equipo.getPresupuesto() < jugador.getPrecio()) {
            throw new PresupuestoInsuficienteException("Presupuesto insuficiente");
        } else {
            equipo.setPresupuesto(equipo.getPresupuesto() - jugador.getPrecio());
        }
    }

    private void guardarRelacionEntreEquipoYJugador(Equipo equipo, Jugador jugador, Integer numeroOrden) {
        EquipoJugador equipoJugador = new EquipoJugador(equipo, jugador, numeroOrden);
        repositorioEquipoJugador.guardarEquipoJugador(equipoJugador);
    }

    private void validarTitular(List<Long> idsJugadores) throws EquipoTitularSinCompletarException {

        if (idsJugadores == null || idsJugadores.size() < 5) {
            throw new EquipoTitularSinCompletarException("Debes seleccionar los 5 jugadores titulares");
        }

        for (int i = 0; i < 5; i++) {
            if (idsJugadores.get(i) == null) {
                throw new EquipoTitularSinCompletarException("Debes seleccionar los 5 jugadores titulares");
            }
        }
    }


}






