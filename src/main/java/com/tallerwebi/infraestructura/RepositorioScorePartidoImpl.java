package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioScorePartido;
import com.tallerwebi.dominio.ScorePartido;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioScorePartidoImpl implements RepositorioScorePartido {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioScorePartidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(ScorePartido score) {
        sessionFactory.getCurrentSession().save(score);
    }

    @Override
    public void actualizar(ScorePartido score) {
        sessionFactory.getCurrentSession().update(score);
    }

    @Override
    public ScorePartido buscarPorPartidoYEquipo(Long partidoId, Long equipoId) {
        return (ScorePartido) sessionFactory.getCurrentSession()
                .createCriteria(ScorePartido.class)
                .add(Restrictions.eq("partido.id", partidoId))
                .add(Restrictions.eq("equipo.id", equipoId))
                .uniqueResult();
    }
}