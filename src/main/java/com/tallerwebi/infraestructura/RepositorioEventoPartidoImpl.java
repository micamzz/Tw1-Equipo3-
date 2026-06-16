package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EventoPartido;
import com.tallerwebi.dominio.RepositorioEventoPartido;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEventoPartidoImpl implements RepositorioEventoPartido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEventoPartidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarEventoPartido(EventoPartido evento) {
        sessionFactory
                .getCurrentSession()
                .save(evento);

    }

    @Override
    public EventoPartido buscarEventoPorId(Long id) {
        return sessionFactory
                .getCurrentSession()
                .get(EventoPartido.class, id);
    }

    @Override
    public List<EventoPartido> buscarEventoPorPartido(Long partidoId) {
        String hql =
                "FROM EventoPartido e " +
                        "WHERE e.partido.id = :partidoId " +
                        "ORDER BY e.momentoPartido";
        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, EventoPartido.class)
                .setParameter("partidoId", partidoId)
                .list();

    }


}
