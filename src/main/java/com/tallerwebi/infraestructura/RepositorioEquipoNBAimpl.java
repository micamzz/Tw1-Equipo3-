package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EquipoNBA;
import com.tallerwebi.dominio.RepositorioEquipoNBA;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEquipoNBAimpl implements RepositorioEquipoNBA {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioEquipoNBAimpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public EquipoNBA buscarEquipoPorId(Long id) {
        return (EquipoNBA) sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBA.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void crearEquipo(EquipoNBA equipo) {
        sessionFactory.getCurrentSession().save(equipo);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EquipoNBA> obtenerTodosLosEquipos() {
        return (List<EquipoNBA>) sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBA.class)
                .list();
    }


}