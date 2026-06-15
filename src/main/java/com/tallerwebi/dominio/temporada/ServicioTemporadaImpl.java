package com.tallerwebi.dominio.temporada;

import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;
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
    public Temporada obtenerTemporadaActual() throws TemporadaActualNoEncontradaException {
        Temporada temporadaActual = repositorioTemporada.obtenerTemporadaActual();

        if (temporadaActual == null) {
            throw new TemporadaActualNoEncontradaException("No hay ninguna temporada vigente en este momento");
        }

        return temporadaActual;
    }

    @Override
    public List<Temporada> obtenerTodasLasTemporadas() {
        return repositorioTemporada.obtenerTodasLasTemporadas();
    }

    @Override
    public Temporada obtenerTemporadaPorId(Long idTemporada) {
        return repositorioTemporada.obtenerTemporadaPorId(idTemporada);
    }
}