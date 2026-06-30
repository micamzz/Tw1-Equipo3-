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
    public List<EventoPartido> buscarEventosPorPartidoYJugador(Long partidoId, Long jugadorId) {

        String hql =
                "FROM EventoPartido e " +
                        "WHERE e.partido.id = :partidoId " +
                        "AND e.jugador.id = :jugadorId";

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, EventoPartido.class)
                .setParameter("partidoId", partidoId)
                .setParameter("jugadorId", jugadorId)
                .list();
    }

    @Override
    public List<EventoPartido> buscarEventosPorPartidoYEquipo(Long partidoId, Long equipoId) {

      /*  List<EventoPartido> eventos = sessionFactory.getCurrentSession()
                .createCriteria(EventoPartido.class)

                .add(Restrictions.eq("partido.id", partidoId))
                .add(Restrictions.eq("equipo.id", equipoId))
                .list();

        return eventos;
*/
        String hql = "SELECT e FROM EventoPartido e " +
                "JOIN FormacionPartido f ON e.jugador.id = f.jugador.id " +
                "WHERE e.partido.id = :partidoId " +
                "AND f.partido.id = :partidoId " +
                "AND f.equipo.id = :equipoId";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, EventoPartido.class)
                .setParameter("partidoId", partidoId)
                .setParameter("equipoId", equipoId)
                .list();
    }
}



