package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.RepositorioEquipoJugador;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioEquipoJugadorImpl implements RepositorioEquipoJugador {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEquipoJugadorImpl (SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarEquipoJugador (EquipoJugador equipoJugador){
        sessionFactory.getCurrentSession().save(equipoJugador);
    }

    @Override
    public Equipo buscarEquipoPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Equipo.class, id);
    }





}
