package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Calendario;
import com.tallerwebi.dominio.PartidoNBA;
import com.tallerwebi.dominio.RendimientoJugador;
import com.tallerwebi.dominio.RepositorioCalendario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioCalendarioImpl implements RepositorioCalendario {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioCalendarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Calendario buscarCalendarioActual() {
        return (Calendario) sessionFactory.getCurrentSession()
                .createCriteria(Calendario.class)
                .add(Restrictions.eq("nombre", "Temporada 2026"))
                .uniqueResult();
    }

    @Override
    public void guardarCalendario(Calendario calendario) {
        sessionFactory.getCurrentSession().save(calendario);
    }

    @Override
    public List<PartidoNBA> buscarListaDePartidosPorCalendarioID(Long calendarioId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(PartidoNBA.class)
                .add(Restrictions.eq("calendario.id", calendarioId))
                .list();
    }



    @Override
    public List<RendimientoJugador> buscarTop6Jugadores() {
        return sessionFactory.getCurrentSession()
                .createCriteria(RendimientoJugador.class)
                .addOrder(Order.desc("puntos"))
                .setMaxResults(6)
                .list();
    }
}