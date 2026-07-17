package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.cronologiaNBA.CronologiaNBA;
import com.tallerwebi.dominio.cronologiaNBA.RepositorioCronologiaNBA;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioCronologiaNBAImpl implements RepositorioCronologiaNBA {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioCronologiaNBAImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(CronologiaNBA cronologia) {
        sessionFactory.getCurrentSession().save(cronologia);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CronologiaNBA> buscarPorPartido(Long partidoId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(CronologiaNBA.class)
                .add(Restrictions.eq("partido.id", partidoId))
                .addOrder(Order.asc("minuto"))
                .list();
    }
}