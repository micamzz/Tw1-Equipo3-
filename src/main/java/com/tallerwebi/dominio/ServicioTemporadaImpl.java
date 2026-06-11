package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioTemporadaImpl implements ServicioTemporada {

    private final RepositorioTemporada repositorioTemporada;

    @Autowired
    public ServicioTemporadaImpl(RepositorioTemporada repositorioTemporada) {
        this.repositorioTemporada = repositorioTemporada;
    }

    @Override
    public void guardarTemporada(Temporada temporada) {
        repositorioTemporada.guardar(temporada);
    }

    @Override
    public Temporada obtenerTemporadaActual() {
        return repositorioTemporada.obtenerTemporadaActual();
    }

    @Override
    public List<Temporada> obtenerTodasLasTemporadas() {
        return repositorioTemporada.obtenerTodas();
    }
}