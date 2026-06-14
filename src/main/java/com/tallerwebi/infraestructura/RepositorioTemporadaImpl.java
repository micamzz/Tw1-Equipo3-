package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.temporada.RepositorioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    public Temporada obtenerTemporadaActual() {
        LocalDate hoy = LocalDate.now();
        return (Temporada) sessionFactory.getCurrentSession()
                .createCriteria(Temporada.class)
                .add(Restrictions.le("fechaInicio", hoy))
                .add(Restrictions.ge("fechaFin", hoy))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Temporada> obtenerTodas() {
        return (List<Temporada>) sessionFactory.getCurrentSession()
                .createCriteria(Temporada.class)
                .list();
    }

}
