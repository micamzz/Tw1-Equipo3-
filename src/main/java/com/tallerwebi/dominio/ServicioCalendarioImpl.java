package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioCalendarioImpl implements ServicioCalendario {

    private final RepositorioCalendario repositorioCalendario;

    @Autowired
    public ServicioCalendarioImpl(RepositorioCalendario repositorioCalendario) {
        this.repositorioCalendario = repositorioCalendario;
    }

    @Override
    public Calendario obtenerCalendario() {
        return repositorioCalendario.buscarCalendarioActual();
    }

    @Override
    public List<RendimientoJugador> Top6Jugadores() {
        return repositorioCalendario.buscarTop6Jugadores();
    }
}