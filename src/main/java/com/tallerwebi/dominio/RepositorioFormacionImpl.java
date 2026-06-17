package com.tallerwebi.dominio;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioFormacionImpl  implements RepositorioFormacion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioFormacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(FormacionPartido formacion) {
    sessionFactory.getCurrentSession().save(formacion);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FormacionPartido> buscarPorPartido(Long idPartido) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FormacionPartido.class);
        criteria.add(Restrictions.eq("partido", idPartido));

        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FormacionPartido> buscarTitularesPorPartidoYEquipo(Long idPartido, Long idEquipo) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FormacionPartido.class);
        criteria.add(Restrictions.eq("partido", idPartido));
        criteria.add(Restrictions.eq("equipo", idEquipo));
        criteria.add(Restrictions.eq("rol", RolFormacion.TITULAR));

        return criteria.list();
    }

    @Override
    public List<FormacionPartido> buscarSuplentesPorPartidoYEquipo(Long idPartido, Long idEquipo) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FormacionPartido.class);
        criteria.add(Restrictions.eq("partido", idPartido));
        criteria.add(Restrictions.eq("equipo", idEquipo));
        criteria.add(Restrictions.eq("rol", RolFormacion.SUPLENTE));

        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean jugadorYaEstaEnFormacion(Long idPartido, Long idJugador) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FormacionPartido.class);
        criteria.add(Restrictions.eq("partido", idPartido));
        criteria.add(Restrictions.eq("jugador.id", idJugador));

        List<FormacionPartido> formacionPartidos = criteria.list();
        return !formacionPartidos.isEmpty();

    }

    @Override
    public void eliminar(Long idFormacion) {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FormacionPartido.class);
    criteria.add(Restrictions.eq("id", idFormacion));

    FormacionPartido formacionPartido = (FormacionPartido) criteria.uniqueResult();
    if(formacionPartido!=null){
        sessionFactory.getCurrentSession().delete(formacionPartido);
    }
    }
}
