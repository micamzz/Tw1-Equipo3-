package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTorneo;
import com.tallerwebi.dominio.Torneo;
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
        Torneo torneo = sessionFactory.getCurrentSession().get(Torneo.class, id);
        return torneo;
    }



}