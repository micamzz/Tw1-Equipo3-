package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Calendario;
import com.tallerwebi.dominio.PartidoNBA;
import com.tallerwebi.dominio.RendimientoJugador;
import com.tallerwebi.dominio.RepositorioCalendario;
import org.hibernate.SessionFactory;
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
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Calendario WHERE nombre = :nombre", Calendario.class)
                .setParameter("nombre", "Temporada 2026")
                .uniqueResult();
    }

    @Override
    public void guardarCalendario(Calendario calendario) {
        sessionFactory.getCurrentSession().save(calendario);
    }

    @Override
    public List<PartidoNBA> buscarListaDePartidosPorCalendarioID(Long calendarioId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM PartidoNBA WHERE calendario.id = :id", PartidoNBA.class)
                .setParameter("id", calendarioId)
                .list();
    }

    @Override
    public List<RendimientoJugador> buscarTop6Jugadores() {
        return sessionFactory.getCurrentSession()
                .createQuery(
                        "FROM RendimientoJugador ORDER BY puntos DESC",
                        RendimientoJugador.class)
                .setMaxResults(6)
                .list();
    }
}