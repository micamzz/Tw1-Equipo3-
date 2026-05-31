package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioTorneoImpl implements RepositorioTorneo {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioTorneoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardarTorneo(Torneo torneo) {
        sessionFactory.getCurrentSession().save(torneo);
    }

    @Override
    public Torneo buscarTorneoPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Torneo.class, id);
    }

    @Override
    public TorneoVirtual buscarTorneoVirtualActual(){
            String hql =
                    "FROM TorneoVirtual tv WHERE tv.estadoTorneo = :estado";

            return sessionFactory
                    .getCurrentSession()
                    .createQuery(hql, TorneoVirtual.class)
                    .setParameter("estado", EstadoTorneo.EN_CURSO)
                    .uniqueResult();
        }

    @Override
    public void actualizarTorneo(Torneo torneo) {
        sessionFactory.getCurrentSession().update(torneo);
    }

}
