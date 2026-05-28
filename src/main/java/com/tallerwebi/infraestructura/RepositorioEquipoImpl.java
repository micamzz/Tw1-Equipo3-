package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.RepositorioEquipo;
import org.hibernate.SessionFactory;
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

        return sessionFactory.getCurrentSession().get(Equipo.class, id);
    }

    @Override
    public Equipo buscarEquipoPorNombre(String nombre) {
        String hql =
                "FROM Equipo e WHERE e.nombreEquipo = :nombre";

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, Equipo.class)
                .setParameter("nombre", nombre)
                .uniqueResult();
    }
}

