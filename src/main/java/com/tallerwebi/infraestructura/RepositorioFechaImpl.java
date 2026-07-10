package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoFecha;
import com.tallerwebi.dominio.Fecha;
import com.tallerwebi.dominio.RepositorioFecha;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioFechaImpl implements RepositorioFecha {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioFechaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarFecha(Fecha fecha) {
        sessionFactory.getCurrentSession().save(fecha);

    }

    @Override
    public Fecha buscarFechaPorId(Long id) {
        return sessionFactory.getCurrentSession()
                .get(Fecha.class, id);
    }

    @Override
    public void actualizarFecha(Fecha fecha) {
        sessionFactory.getCurrentSession().update(fecha);
    }

    @Override
    public void eliminarFecha(Fecha fecha) {
        sessionFactory.getCurrentSession().delete(fecha);
    }

    // Trae las fechas ordenadas
    @Override
    public List<Fecha> buscarTodasLasFechas() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Fecha.class)
                .addOrder(Order.asc("numeroDeFecha"))
                .list();
    }

    @Override
    public Fecha buscarFechaEnCurso() {
        return (Fecha) sessionFactory.getCurrentSession()
                .createCriteria(Fecha.class)
                .add(Restrictions.eq("estadoFecha", EstadoFecha.EN_CURSO))
                .uniqueResult();
    }

    @Override
    public Fecha buscarFechaProgramada() {
        return (Fecha) sessionFactory.getCurrentSession()
                .createCriteria(Fecha.class)
                .add(Restrictions.eq("estadoFecha", EstadoFecha.PROGRAMADA))
                .addOrder(Order.asc("numeroDeFecha"))
                .setMaxResults(1)
                .uniqueResult();
    }


}
