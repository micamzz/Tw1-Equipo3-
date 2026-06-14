package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.equipoNBAJugador.EquipoNBAJugador;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class repositorioEquipoNBAJugadorImpl implements RepositorioEquipoNBAJugador {

    private final SessionFactory sessionFactory;

    public repositorioEquipoNBAJugadorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void asignarJugadorAUnEquipo(EquipoNBAJugador equipoJugador) {
        this.sessionFactory.getCurrentSession().save(equipoJugador);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EquipoNBAJugador> buscarJugadoresDelEquipoNBA(Long idEquipo) {
        return (List<EquipoNBAJugador>) this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("equipoNBA.id", idEquipo))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EquipoNBAJugador> buscarTodasLasAsignaciones() {
        return (List<EquipoNBAJugador>) this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .list();
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

    @Override
    public void eliminarJugadorDelEquipo(EquipoNBAJugador equipoJugador) {
        sessionFactory.getCurrentSession().delete(equipoJugador);
    }

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

    @Override
    @SuppressWarnings("unchecked")
    public List<EquipoNBAJugador> buscarAsignacionesPorTemporada(Long idTemporada) {
        return (List<EquipoNBAJugador>) this.sessionFactory
                .getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("temporada.id", idTemporada))
                .list();
    }

}

