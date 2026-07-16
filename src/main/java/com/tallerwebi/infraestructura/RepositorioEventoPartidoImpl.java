package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EventoPartido;
import com.tallerwebi.dominio.FormacionPartido;
import com.tallerwebi.dominio.RepositorioEventoPartido;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEventoPartidoImpl implements RepositorioEventoPartido {

    private final SessionFactory sessionFactory;

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
    public List<EventoPartido> buscarEventosPorPartido(Long partidoId) {
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

    @Override
    public List<EventoPartido> buscarEventosPorJugadorTorneo(Long jugadorId, Long torneoId) {
        String hql =
                "FROM EventoPartido e " +
                        "WHERE e.jugador.id = :jugadorId " +
                        "AND e.partido.torneo.id = :torneoId " +
                        "ORDER BY e.momentoPartido";
        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, EventoPartido.class)
                .setParameter("jugadorId", jugadorId)
                .setParameter("torneoId", torneoId)
                .list();

    }

    @Override
    public List<EventoPartido> buscarEventosPorJugadorYFecha(Long jugadorId, Long fechaId) {
        String hql =
                "FROM EventoPartido e " +
                        "WHERE e.jugador.id = :jugadorId " +
                        "AND e.partido.fecha.id = :fechaId " +
                        "ORDER BY e.momentoPartido";
        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, EventoPartido.class)
                .setParameter("jugadorId", jugadorId)
                .setParameter("fechaId", fechaId)
                .list();
    }

    @Override
    public void eliminarEvento(EventoPartido evento) {
        sessionFactory.getCurrentSession().delete(evento);
    }

    @Override
    public EventoPartido buscarEventoPorId(Long idEvento) {
        return sessionFactory.getCurrentSession().get(EventoPartido.class, idEvento);

    }

}





