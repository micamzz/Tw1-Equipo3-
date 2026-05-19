package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipo;
import com.tallerwebi.dominio.EquipoJugador;
import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.RepositorioEquipoJugador;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

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
    public List <EquipoJugador> buscarPorEquipoId(Long id) {
        String hql =
                "FROM EquipoJugador e WHERE equipo.id = :id";

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, EquipoJugador.class)
                .setParameter("id", id)
                .getResultList();


    }





}
