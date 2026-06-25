package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioFormacionImpl implements RepositorioFormacion {

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
                .add(Restrictions.eq("partido.id", idPartido))
                .list();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<FormacionPartido> buscarPorPartidoYEquipo(Long idPartido, Long idEquipo) {
        return sessionFactory.getCurrentSession()
                .createCriteria(FormacionPartido.class)
                .add(Restrictions.eq("partido.id", idPartido))
                .add(Restrictions.eq("equipo.id", idEquipo))
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
    public EquipoNBA buscarEquipo(Long idPartido, Long idJugador) {
        List<FormacionPartido> resultados = sessionFactory.getCurrentSession()
                .createCriteria(FormacionPartido.class)
                .add(Restrictions.eq("partido.id", idPartido))
                .add(Restrictions.eq("jugador.id", idJugador))
                .list();

        if (resultados.isEmpty()) {
            return null; // or throw a specific exception
        }

        return resultados.get(0).getEquipo();
    }

    @Override
    public void eliminar(Long idFormacion) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FormacionPartido.class);
        criteria.add(Restrictions.eq("id", idFormacion));

        FormacionPartido formacionPartido = (FormacionPartido) criteria.uniqueResult();
        if (formacionPartido != null) {
            sessionFactory.getCurrentSession().delete(formacionPartido);
        }
    }
}
