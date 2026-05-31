package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Posicion;
import com.tallerwebi.dominio.RepositorioJugador;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioJugador")
public class RepositorioJugadorImpl implements RepositorioJugador {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioJugadorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Jugador> buscarJugadores(Posicion posicion, String nombre) {
        org.hibernate.Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Jugador.class);
        if (posicion != null) {
            criteria.add(Restrictions.eq("posicion", posicion));
        }
        if (nombre != null && !nombre.isEmpty()) {
            criteria.add(Restrictions.or(Restrictions.ilike("nombre", "%" + nombre + "%"),
                    Restrictions.ilike("apellido", "%" + nombre + "%")
            ));
        }
        return criteria.list();
    }

    @Override
    public Jugador buscarJugadorPorId(long id) {
        org.hibernate.Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Jugador.class);
        criteria.add(Restrictions.eq("id", id));
        return (Jugador) criteria.uniqueResult();
    }

    @Override
    public List<Jugador> buscarJugadoresPorPosicion(Posicion posicion) {
        return (List<Jugador>) sessionFactory.getCurrentSession()
                .createCriteria(Jugador.class)
                .add(Restrictions.eq("posicion", posicion))
                .list();
    }

    @Override
    public List<Jugador> buscarTodosLosJugadores() {
        return (List<Jugador>) sessionFactory.getCurrentSession()
                .createCriteria(Jugador.class)
                .list();
    }


}

