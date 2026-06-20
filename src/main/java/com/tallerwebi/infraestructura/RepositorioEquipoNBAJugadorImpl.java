package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.equipoNBAJugador.EquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEquipoNBAJugadorImpl implements RepositorioEquipoNBAJugador {

    private final SessionFactory sessionFactory;

    public RepositorioEquipoNBAJugadorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void asignarJugadorAUnEquipo(EquipoNBAJugador equipoJugador) {
        this.sessionFactory.getCurrentSession().save(equipoJugador);
    }

    @Override
    public void eliminarJugadorDelEquipo(EquipoNBAJugador equipoJugador) {
        sessionFactory.getCurrentSession().delete(equipoJugador);
    }

    /*borra todas las asignaciones de un equipo*/
    @Override
    @SuppressWarnings("unchecked")
    public void eliminarTodasLasAsignacionesDelEquipo(Long idEquipo) {
        List<EquipoNBAJugador> asignaciones =
                this.sessionFactory.getCurrentSession()
                        .createCriteria(EquipoNBAJugador.class)
                        .add(Restrictions.eq("equipoNBA.id", idEquipo))
                        .list();

        for (EquipoNBAJugador asignacion : asignaciones) {
            this.sessionFactory.getCurrentSession().delete(asignacion);
        }
    }

    /*Busca todas las asignaciones de un equipoNBA, sin filtrar por torneo*/
    @Override
    @SuppressWarnings("unchecked")
    public List<EquipoNBAJugador> buscarJugadoresDelEquipoNBA(Long idEquipo) {
        return (List<EquipoNBAJugador>) this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("equipoNBA.id", idEquipo))
                .list();
    }

    /* Busca asignacion filtrando por torneo*/
    @Override
    @SuppressWarnings("unchecked")
    public List<EquipoNBAJugador> buscarJugadoresDelEquipoNBAEnTorneo(Long idEquipo, Long idTorneo) {
        return (List<EquipoNBAJugador>) this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("equipoNBA.id", idEquipo))
                .add(Restrictions.eq("torneo.id", idTorneo))
                .list();
    }

    /*Comprueba que exista al menos una fila en esta tabla para ese torneo*/
    @Override
    public boolean existenJugadoresAsignadosEnTorneo(Long idTorneo) {
        List<EquipoNBAJugador> asignaciones = this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("torneo.id", idTorneo))
                .list();

        return !asignaciones.isEmpty();
    }

    /*VER SI SE BORRA*/
    @Override
    @SuppressWarnings("unchecked")
    public List<EquipoNBAJugador> buscarTodasLasAsignaciones() {
        return (List<EquipoNBAJugador>) this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .list();
    }


    /*BUSCA UNA ASIGNACION PUNTUAL ENTRE EQUIPONBA Y JUGADOR*/
    @Override
    public EquipoNBAJugador buscarEquipoYJugadorAsociado(Long idEquipo, Long idJugador) {

        return (EquipoNBAJugador) sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                /*el primer string tiene que coinicidr con el nombre del atributo de la clase*/
                .createAlias("equipoNBA", "equi")
                .createAlias("jugador", "juga")
                /*usa el alias y lo demas tiene q coinicidr con el atributo de esa clase*/
                .add(Restrictions.eq("equi.id", idEquipo))
                .add(Restrictions.eq("juga.id", idJugador))
                .uniqueResult();
    }


    /*Para saber si un jugador pertenece a un torneo*/
    @Override
    @SuppressWarnings("unchecked")
    public boolean jugadorPerteneceAUnEquipoEnElTorneo(Long idJugador, Long idTorneo) {
        return this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("jugador.id", idJugador))
                .add(Restrictions.eq("torneo.id", idTorneo))
                .uniqueResult() != null;
    }

    @Override
    public List<EquipoNBAJugador> buscarAsignacionesPorTorneo(Long idTorneo) {
        return (List<EquipoNBAJugador>) this.sessionFactory
                .getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("torneo.id", idTorneo))
                .list();
    }

    @Override
    public EquipoNBAJugador buscarEquipoJugadorYTorneo(Long idEquipo, Long idJugador, Long idTorneo) {
        return (EquipoNBAJugador) sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("equipoNBA.id", idEquipo))
                .add(Restrictions.eq("jugador.id", idJugador))
                .add(Restrictions.eq("torneo.id", idTorneo))
                .uniqueResult();
    }



    /* TEMPORADA*/
    /*
    @Override
    @SuppressWarnings("unchecked")
    public List<EquipoNBAJugador> buscarAsignacionesPorTemporada(Long idTemporada) {
        return (List<EquipoNBAJugador>) this.sessionFactory
                .getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("temporada.id", idTemporada))
                .list();
    }

    @Override
    public EquipoNBAJugador buscarEquipoJugadorYTemporada(Long idEquipo, Long idJugador, Long idTemporada) {
        return (EquipoNBAJugador) sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("equipoNBA.id", idEquipo))
                .add(Restrictions.eq("jugador.id", idJugador))
                .add(Restrictions.eq("temporada.id", idTemporada))
                .uniqueResult();
    }

     @Override
     @SuppressWarnings("unchecked")
     public boolean jugadorPerteneceAUnEquipoEnLaTemporada(Long idJugador, Long idTemporada) {
         return this.sessionFactory.getCurrentSession()
                 .createCriteria(EquipoNBAJugador.class)
                 .add(Restrictions.eq("jugador.id", idJugador))
                 .add(Restrictions.eq("temporada.id", idTemporada))
                 .uniqueResult() != null;
     }
 */

}



