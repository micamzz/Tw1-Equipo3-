package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
import com.tallerwebi.dominio.equipoJugador.RepositorioEquipoJugador;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEquipoJugadorImpl implements RepositorioEquipoJugador {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioEquipoJugadorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarEquipoJugador(EquipoJugador equipoJugador) {
        sessionFactory.getCurrentSession().save(equipoJugador);
    }


    @Override
    public List<EquipoJugador> buscarPorEquipoId(Long id) {
        String hql =
                "FROM EquipoJugador e WHERE equipo.id = :id";

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, EquipoJugador.class)
                .setParameter("id", id)
                .getResultList();


    }

    @Override
    public EquipoJugador buscarEquipoYJugadorAsociado(Long idEquipo, Long idJugador) {

        return (EquipoJugador) sessionFactory.getCurrentSession()
                .createCriteria(EquipoJugador.class)
                /*el primer string tiene que coinicidr con el nombre del atributo de la clase*/
                .createAlias("equipo", "equi")
                .createAlias("jugador", "juga")
                /*usa el alias y lo demas tiene q coinicidr con el atributo de esa clase*/
                .add(Restrictions.eq("equi.id", idEquipo))
                .add(Restrictions.eq("juga.id", idJugador))
                .uniqueResult();
    }

    @Override
    public void eliminarEquipoJugador(EquipoJugador equipoJugador) {
        sessionFactory.getCurrentSession().delete(equipoJugador);
    }

    @Override
    public void actualizarEquipoJugador(EquipoJugador equipoJugador) {
        sessionFactory.getCurrentSession().update(equipoJugador);
    }

    @Override
    public List<EquipoJugador> buscarPorEquipoIdYFechaId(Long idEquipo, Long idFechaActual) {
        return sessionFactory.getCurrentSession()
                .createCriteria(EquipoJugador.class)
                .add(Restrictions.eq("equipo.id", idEquipo))
                .add(Restrictions.eq("fecha.id", idFechaActual))
                .list();
    }

}
