package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EquipoNBAJugador;
import com.tallerwebi.dominio.RepositorioEquipoNBAJugador;
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
    public boolean jugadorExisteEnLaTemporada(Long idJugador, Long idTemporada) {
        return this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("jugador.id", idJugador))
                .createAlias("equipoNBA", "equipo")
                .add(Restrictions.eq("equipo.temporada.id", idTemporada))
                .uniqueResult() != null;
    }
}

