package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioFormacionImpl implements RepositorioFormacion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioFormacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardarFormacion(Formacion formacion) {
        sessionFactory.getCurrentSession().save(formacion);
    }

    @Override
    public List<Formacion> obtenerJugadoresPorPartido(Long idPartido) {
        String hql =
                "FROM Formacion f WHERE f.partido.id = :idPartido";

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, Formacion.class)
                .setParameter("idPartido", idPartido)
                .list();
    }

    @Override
    public List<Formacion> buscarJugadoresPorEquipoYPartido(Long idEquipo, Long idPartido) {
        String hql =
                "FROM Formacion f " +
                        "WHERE f.equipoNBA.id = :idEquipo " +
                        "AND f.partido.id = :idPartido";

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, Formacion.class)
                .setParameter("idEquipo", idEquipo)
                .setParameter("idPartido", idPartido)
                .list();
    }

    @Override
    public boolean existeJugadorEnPartido(Long jugadorId, Long partidoId) {
       String hql = "SELECT COUNT(*) " +
               "FROM Formacion f WHERE f.jugador.id = :jugadorId " +
               "AND f.partido.id = :partidoId";

       Long cantidad = sessionFactory
               .getCurrentSession()
               .createQuery(hql, Long.class)
               .setParameter("jugadorId", jugadorId)
               .setParameter("partidoId", partidoId)
               .uniqueResult();


       return cantidad > 0;
    }
}

