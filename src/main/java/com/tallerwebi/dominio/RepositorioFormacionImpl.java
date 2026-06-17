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
        return sessionFactory.getCurrentSession()
                .createCriteria(FormacionPartido.class)
                .add(Restrictions.eq("partido", idPartido))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FormacionPartido> buscarTitularesPorPartidoYEquipo(Long idPartido, Long idEquipo) {

       return sessionFactory.getCurrentSession()
               .createCriteria(FormacionPartido.class)
               .add(Restrictions.eq("partido.id", idPartido))
               .add(Restrictions.eq("equipo.id", idEquipo))
               .add(Restrictions.eq("rol", RolFormacion.TITULAR))
               .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FormacionPartido> buscarSuplentesPorPartidoYEquipo(Long idPartido, Long idEquipo) {
        return sessionFactory.getCurrentSession()
                .createCriteria(FormacionPartido.class)
                .add(Restrictions.eq("partido.id", idPartido))
                .add(Restrictions.eq("equipo.id", idEquipo))
                .add(Restrictions.eq("rol", RolFormacion.SUPLENTE))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean jugadorYaEstaEnFormacion(Long idPartido, Long idJugador) {
        List<FormacionPartido> resultado = sessionFactory.getCurrentSession()
                .createCriteria(FormacionPartido.class)
                .add(Restrictions.eq("partido.id", idPartido))
                .add(Restrictions.eq("jugador.id", idJugador))
                .list();
        return !resultado.isEmpty();

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
