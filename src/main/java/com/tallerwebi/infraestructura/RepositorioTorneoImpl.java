package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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
    public TorneoVirtual buscarTorneoVirtualActual() {

       LocalDate hoy = LocalDate.now();

        String hql =
                "FROM TorneoVirtual tv " +
                        "WHERE tv.fechaInicio <= :hoy " +
                        "AND tv.fechaFin >= :hoy"
                ;

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, TorneoVirtual.class)
                .setParameter("hoy", hoy)
                .uniqueResult();
    }

    @Override
    public void actualizarTorneo(Torneo torneo) {
        sessionFactory.getCurrentSession().update(torneo);
    }

    @Override
    public List<TorneoVirtual> obtenerTodosLosTorneosVirtuales() {

        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "FROM TorneoVirtual",
                        TorneoVirtual.class)
                .list();
    }

    @Override
    public void eliminarTorneo(Torneo torneo) {
        sessionFactory.getCurrentSession().delete(torneo);
    }


}
