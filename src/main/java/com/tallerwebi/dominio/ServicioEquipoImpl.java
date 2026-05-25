package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoTitularSinCompletarException;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoImpl implements ServicioEquipo {

    private final RepositorioEquipo repositorioEquipo;
    private final RepositorioJugador repositorioJugador;
    private final RepositorioEquipoJugador repositorioEquipoJugador;
    private static final Double PRESUPUESTO_INICIAL = 850000D;


    @Autowired
    public ServicioEquipoImpl(RepositorioEquipo repositorioEquipo, RepositorioJugador repositorioJugador,
                              RepositorioEquipoJugador repositorioEquipoJugador) {
        this.repositorioEquipo = repositorioEquipo;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoJugador = repositorioEquipoJugador;
    }


    @Override
    public Equipo guardarEquipo(Equipo equipo) {
        equipo.setPresupuesto(PRESUPUESTO_INICIAL); // Presupuesto inicial para cada equipo
        repositorioEquipo.guardarEquipo(equipo);
        return equipo;/* Devuelve Equipo porq necesito recuperar el ID en el controlador  */
    }

    @Override
    public Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException {
        Equipo equipo = repositorioEquipo.buscarEquipoPorId(id);
        if (equipo == null) {
            throw new EquipoNoEncontradoException();
        }
        return equipo;
    }

    @Override
    public Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException {
        Equipo equipo = repositorioEquipo.buscarEquipoPorNombre(nombre);
        if (equipo == null) {
            throw new EquipoNoEncontradoException();
        }
        return equipo;
    }

    @Override
    public void agregarJugadorAlEquipo(Long idEquipo, Long idJugador, Integer numeroDeOrden) throws EquipoNoEncontradoException, PresupuestoInsuficienteException, elJugadorYaExisteEnElEquipoException {

        Equipo equipo = buscarEquipoPorId(idEquipo);

        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

        /* Verifica si el jugador ya está asociado al equipo*/
        validarSiElJugadorYaFueElegido(idEquipo, idJugador);

        // Verifica que el presupuesto alcanze
        siElPresupuestoEsMenorLanzaExcepcion(equipo, jugador);

        // Llama al método que guarda la relación en el repositorioEquipoJugador
        guardarRelacionEntreEquipoYJugador(equipo, jugador, numeroDeOrden);
    }


    @Override
    public void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException {

        Equipo equipo = buscarEquipoPorId(idEquipo);
    /* cuando el método tenga la excepcion llamar al repo
    repositorioJugador.buscarJugadorPorId(idJugador);
 */
        EquipoJugador equipoJugador = repositorioEquipoJugador.buscarEquipoYJugadorAsociado(idEquipo, idJugador);

        if (equipoJugador != null) {
            equipo.setPresupuesto(equipo.getPresupuesto() + equipoJugador.getJugador().getPrecio());
            /* cuando tenga a jugador puedo hace jugador.getprecio()*/
            repositorioEquipoJugador.eliminarEquipoJugador(equipoJugador);
        }
    }


    /*DEVUELVE UNA LISTA CON TODOS LOS Jugadores QUE ESTAN ASOCIADOS AL EQUIPO*/
    @Override
    public List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo) {
        return repositorioEquipoJugador.buscarPorEquipoId(idEquipo);
    }



    /*
    @Override
    public void guardarEquipoCompleto(Long idEquipo, List<Long> idsJugadores) throws EquipoTitularSinCompletarException, PresupuestoInsuficienteException, EquipoNoEncontradoException {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        validarTitular(idsJugadores);
        for (int i = 0; i < idsJugadores.size(); i++) {
            Long idJugador = idsJugadores.get(i);
            if (idJugador != null) {
               *el i+1 es para el numero de orden
                Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);
                siElPresupuestoEsMenorLanzaExcepcion(equipo, jugador);
                guardarRelacionEntreEquipoYJugador(equipo, jugador, i + 1);
            }
        }
    }
  /*


    /*  GUARDA AL EQUIPO Y AL JUGADOR EN EQUIPOJUGADOR LLAMANDO AL REPOSITORIO */

    private void guardarRelacionEntreEquipoYJugador(Equipo equipo, Jugador jugador, Integer numeroDeOrden) {
        EquipoJugador equipoJugador = new EquipoJugador();

        equipoJugador.setEquipo(equipo);
        equipoJugador.setJugador(jugador);
        equipoJugador.setNumeroOrden(numeroDeOrden);

        repositorioEquipoJugador.guardarEquipoJugador(equipoJugador);
    }

    /* SI EL SALDO DEL EQUIPO ES MENOR AL VALOR DEL JUGADOR EXCEPCION, NO PUEDE COMPRARLO.*/
    private void siElPresupuestoEsMenorLanzaExcepcion(Equipo equipo, Jugador jugador) throws PresupuestoInsuficienteException {
        if (equipo.getPresupuesto() < jugador.getPrecio()) {
            throw new PresupuestoInsuficienteException("Presupuesto insuficiente");
        } else {
            equipo.setPresupuesto(equipo.getPresupuesto() - jugador.getPrecio());
        }
    }


    private void validarTitular(List<Long> idsJugadores) throws EquipoTitularSinCompletarException {

        if (idsJugadores == null || idsJugadores.size() < 5) {
            throw new EquipoTitularSinCompletarException();
        }

        for (int i = 0; i < 5; i++) {
            if (idsJugadores.get(i) == null) {
                throw new EquipoTitularSinCompletarException();
            }
        }
    }

    /* SI EL JUGADOR YA ESTA ASOCIADO AL EQUIPO HAY UNA EXCEPCION*/
    private void validarSiElJugadorYaFueElegido(Long idEquipo, Long idJugador) throws elJugadorYaExisteEnElEquipoException {

        EquipoJugador equipoJugador = repositorioEquipoJugador.buscarEquipoYJugadorAsociado(idEquipo, idJugador);

        if (equipoJugador != null) {
            throw new elJugadorYaExisteEnElEquipoException("El jugador ya esta fichado en el equipo");
        }
    }

}






