package com.tallerwebi.dominio.temporada;

import com.tallerwebi.dominio.excepcion.FechaFinAnteriorAInicioException;
import com.tallerwebi.dominio.excepcion.TemporadaActualNoEncontradaException;
import com.tallerwebi.dominio.excepcion.TemporadaFueraDeRangoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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

    @Override
    public void finalizarTemporada(LocalDate fechaFin) {
        Temporada actual = repositorioTemporada.obtenerTemporadaActual();
        if (actual == null) {
            throw new TemporadaActualNoEncontradaException("No hay una temporada activa para finalizar");
        }

        // Validacion: fechaFin debe ser mayor a fechaInicio
        if (!fechaFin.isAfter(actual.getFechaInicio())) {
            throw new FechaFinAnteriorAInicioException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        // Validacion: no se pueden crear temporadas del año 2028 en adelante
        int siguienteAnio = actual.getAnio() + 1;
        if (siguienteAnio >= 2028) {
            throw new TemporadaFueraDeRangoException("No se puede crear una temporada del año 2028 o posterior");
        }

        // Finalizar la temporada actual
        actual.setFechaFin(fechaFin);
        repositorioTemporada.actualizar(actual);

        // Crear la siguiente temporada automaticamente
        Temporada siguiente = new Temporada();
        siguiente.setAnio(siguienteAnio);
        siguiente.setNombre("Temporada " + siguienteAnio);
        siguiente.setFechaInicio(LocalDate.of(siguienteAnio, 3, 1));
        siguiente.setFechaFin(null); // null = activa
        repositorioTemporada.guardar(siguiente);
    }
}