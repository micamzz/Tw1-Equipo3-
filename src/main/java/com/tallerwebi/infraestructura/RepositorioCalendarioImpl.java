package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.FuturosPartidos;
import com.tallerwebi.dominio.RendimientoJugador;
import com.tallerwebi.dominio.RepositorioCalendario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
    public FuturosPartidos buscarFuturosPartidosActuales() {
        return (FuturosPartidos) sessionFactory.getCurrentSession()
                .createCriteria(FuturosPartidos.class)
                .setMaxResults(1)
                .uniqueResult();
    }

    @Override
    public void guardarFuturosPartidos(FuturosPartidos futurosPartidos) {
        sessionFactory.getCurrentSession().save(futurosPartidos);
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
