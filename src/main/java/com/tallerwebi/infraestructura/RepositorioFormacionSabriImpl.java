/*
package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioFormacionSabriImpl implements RepositorioFormacionSabri {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioFormacionSabriImpl(SessionFactory sessionFactory) {
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
    public boolean existeJugadorEnFormacion(Long idJugador, Long idPartido) {
       String hql = "SELECT COUNT(*) " +
               "FROM Formacion f WHERE f.jugador.id = :jugadorId " +
               "AND f.partido.id = :partidoId";

       Long cantidad = sessionFactory
               .getCurrentSession()
               .createQuery(hql, Long.class)
               .setParameter("jugadorId", idJugador)
               .setParameter("partidoId", idPartido)
               .uniqueResult();


       return cantidad > 0;
    }
}

*/