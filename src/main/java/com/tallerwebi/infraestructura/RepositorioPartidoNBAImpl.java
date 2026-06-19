package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PartidoNBA;
import com.tallerwebi.dominio.RepositorioPartidoNBA;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPartidoNBAImpl implements RepositorioPartidoNBA {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidoNBAImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(PartidoNBA partido) {
        sessionFactory.getCurrentSession().save(partido);
    }

    @Override
    public void actualizar(PartidoNBA partido) {
        sessionFactory.getCurrentSession().update(partido);
    }

    @Override
    public PartidoNBA buscarPorId(Long id) {
        return (PartidoNBA) sessionFactory.getCurrentSession()
                .createCriteria(PartidoNBA.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PartidoNBA> buscarPartidosActivos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(PartidoNBA.class)
                .add(Restrictions.isNull("minutoFin"))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PartidoNBA> buscarPartidosFinalizados() {
        return sessionFactory.getCurrentSession()
                .createCriteria(PartidoNBA.class)
                .add(Restrictions.isNotNull("minutoFin"))
                .list();
    }

    @Override
    public boolean equipoTienePartidoActivo(Long equipoId) {
        Long countLocal = (Long) sessionFactory.getCurrentSession()
                .createCriteria(PartidoNBA.class)
                .createAlias("equipoLocal", "equipoLocal")
                .add(Restrictions.eq("equipoLocal.id", equipoId))
                .add(Restrictions.isNull("minutoFin"))
                .setProjection(Projections.rowCount())
                .uniqueResult();


        Long countVisitante = (Long) sessionFactory.getCurrentSession()
                .createCriteria(PartidoNBA.class)
                .createAlias("equipoVisitante", "equipoVisitante")
                .add(Restrictions.eq("equipoVisitante.id", equipoId))
                .add(Restrictions.isNull("minutoFin"))
                .setProjection(Projections.rowCount())
                .uniqueResult();

        return (countLocal + countVisitante) > 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PartidoNBA> buscarTodos() {
        return sessionFactory.getCurrentSession()
                .createCriteria(PartidoNBA.class)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PartidoNBA> buscarPorTorneo(Long torneoId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(PartidoNBA.class)
                .add(Restrictions.eq("torneo.id", torneoId))
                .list();
    }
}