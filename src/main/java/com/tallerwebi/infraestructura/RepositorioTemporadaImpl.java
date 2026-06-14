package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.equipoNBAJugador.EquipoNBAJugador;
import com.tallerwebi.dominio.temporada.RepositorioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RepositorioTemporadaImpl implements RepositorioTemporada {


    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioTemporadaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Temporada temporada) {
        sessionFactory.getCurrentSession().save(temporada);
    }

    @Override
    public Temporada buscarPorId(Long id) {
        return (Temporada) sessionFactory.getCurrentSession()
                .createCriteria(Temporada.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public Temporada obtenerTemporadaActual() {
        LocalDate hoy = LocalDate.now();
        return (Temporada) sessionFactory.getCurrentSession()
                .createCriteria(Temporada.class)
                .add(Restrictions.le("fechaInicio", hoy))
                .add(Restrictions.ge("fechaFin", hoy))
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Temporada> obtenerTodas() {
        return (List<Temporada>) sessionFactory.getCurrentSession()
                .createCriteria(Temporada.class)
                .list();
    }

    @Override
    public boolean jugadorExisteEnLaTemporada(Long idJugador, Long idTemporada) {
        return this.sessionFactory.getCurrentSession()
                .createCriteria(EquipoNBAJugador.class)
                .add(Restrictions.eq("jugador.id", idJugador))
                .createAlias("equipoNBA", "equipo")
                .add(Restrictions.eq("equipo.temporada.id", idTemporada))
                .uniqueResult() != null;
    }
}
