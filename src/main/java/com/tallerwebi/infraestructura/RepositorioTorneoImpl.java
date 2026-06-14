package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTorneo;
import com.tallerwebi.dominio.TipoTorneo;
import com.tallerwebi.dominio.Torneo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RepositorioTorneoImpl implements RepositorioTorneo {

    private final SessionFactory sessionFactory;

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
    public void actualizarTorneo(Torneo torneo) {
        sessionFactory.getCurrentSession().update(torneo);
    }

    @Override
    public List<Torneo> obtenerTorneosPorTipo(TipoTorneo tipoTorneo) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Torneo t WHERE t.tipoTorneo = :tipo", Torneo.class)
                .setParameter("tipo", tipoTorneo)
                .list();
    }

    @Override
    public void eliminarTorneo(Torneo torneo) {
        sessionFactory.getCurrentSession().delete(torneo);
    }

    @Override
    public List<Torneo> obtenerTodosLosTorneos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Torneo", Torneo.class)
                .list();
    }

    @Override
    public Torneo buscarTorneoActual(TipoTorneo tipoTorneo) {
        LocalDate hoy = LocalDate.now();
        String hql = "FROM Torneo t WHERE t.fechaInicio <= :hoy AND t.fechaFin >= :hoy AND t.tipoTorneo = :tipo";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Torneo.class)
                .setParameter("hoy", hoy)
                .setParameter("tipo", tipoTorneo)
                .uniqueResult();
    }

}
