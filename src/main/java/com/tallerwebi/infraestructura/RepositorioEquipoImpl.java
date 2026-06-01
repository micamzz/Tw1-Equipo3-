package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.RepositorioEquipo;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioEquipoImpl implements RepositorioEquipo {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioEquipoImpl(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarEquipo(Equipo equipo) {

        sessionFactory.getCurrentSession().save(equipo);
    }

    @Override
    public Equipo buscarEquipoPorId(Long id) {

        return (Equipo) sessionFactory.getCurrentSession()
                .createCriteria(Equipo.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
         /* select *
    From Equipo
    where id=
     */
    }

    @Override
    public Equipo buscarEquipoPorNombre(String nombre) {
        return (Equipo) sessionFactory.getCurrentSession()
                .createCriteria(Equipo.class)
                .add(Restrictions.eq("nombreEquipo", nombre))
                .uniqueResult();
    }

    @Override
    public boolean existeEquipoEnTorneo(Long idTorneo) {
        String hql =
                "SELECT COUNT (e)" +
                        "FROM Equipo e " +
                        "WHERE e.torneo.id = :idTorneo";

        Long cantidad = sessionFactory
                .getCurrentSession()
                .createQuery(hql, Long.class)
                .setParameter("idTorneo", idTorneo)
                .uniqueResult();

        return cantidad > 0;
    }


}

