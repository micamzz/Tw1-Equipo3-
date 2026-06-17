package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.temporada.RepositorioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioTemporadaImpl implements RepositorioTemporada {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioTemporadaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Temporada temporada) {
        sessionFactory.getCurrentSession().save(temporada);
    }

    @Override
    public void actualizar(Temporada temporada) {
        sessionFactory.getCurrentSession().update(temporada);
    }

    // La temporada activa es la que tiene fechaFin null
    @Override
    public Temporada obtenerTemporadaActual() {
        return (Temporada) sessionFactory.getCurrentSession()
                .createCriteria(Temporada.class)
                .add(Restrictions.isNull("fechaFin"))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Temporada> obtenerTodasLasTemporadas() {
        return (List<Temporada>) sessionFactory.getCurrentSession()
                .createCriteria(Temporada.class)
                .addOrder(Order.desc("anio"))
                .list();
    }

    @Override
    public Temporada obtenerTemporadaPorId(Long idTemporada) {
        return (Temporada) sessionFactory.getCurrentSession()
                .createCriteria(Temporada.class)
                .add(Restrictions.eq("id", idTemporada))
                .uniqueResult();
    }
}
