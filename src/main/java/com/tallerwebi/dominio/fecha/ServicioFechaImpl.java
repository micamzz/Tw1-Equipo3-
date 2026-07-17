package com.tallerwebi.dominio.fecha;

import com.tallerwebi.dominio.enums.EstadoFecha;
import com.tallerwebi.dominio.equipo.ServicioEquipo;
import com.tallerwebi.dominio.excepcion.FechaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.TorneoNoEncontradoException;
import com.tallerwebi.dominio.torneo.RepositorioTorneo;
import com.tallerwebi.dominio.torneo.Torneo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioFechaImpl implements ServicioFecha {

    private final RepositorioFecha repositorioFecha;
    private final RepositorioTorneo repositorioTorneo;
    private final ServicioEquipo servicioEquipo;

    /*   @Lazy necesario porque ServicioEquipoImpl y ServicioFechaImpl  dependen mutuamente.
         Sin esto, Spring no puede iniciar la aplicación (circular reference). */
    @Autowired
    public ServicioFechaImpl(RepositorioFecha repositorioFecha, RepositorioTorneo repositorioTorneo, @Lazy ServicioEquipo servicioEquipo) {
        this.repositorioFecha = repositorioFecha;
        this.repositorioTorneo = repositorioTorneo;
        this.servicioEquipo = servicioEquipo;
    }


    @Override
    public void registrarFecha(Long idTorneo, Integer numeroFecha, EstadoFecha estadoFecha) throws TorneoNoEncontradoException {

        // Validacion de que el torneo exista
        Torneo torneo = repositorioTorneo.buscarTorneoPorId(idTorneo);
        if (torneo == null) {
            throw new TorneoNoEncontradoException("El torneo no existe");
        }

        Fecha fechaNueva = new Fecha();
        fechaNueva.setNumeroDeFecha(numeroFecha);
        fechaNueva.setTorneo(torneo);
        fechaNueva.setEstadoFecha(estadoFecha);

        repositorioFecha.guardarFecha(fechaNueva);


    }


    @Override
    @Transactional
    public Fecha obtenerFechaPorId(Long id) throws FechaNoEncontradaException {
        Fecha fecha = repositorioFecha.buscarFechaPorId(id);
        if (fecha == null) {
            throw new FechaNoEncontradaException("La fecha no existe");
        }
        return fecha;
    }


    @Override
    public void actualizarFecha(Long idFecha, Integer numero, EstadoFecha estado) throws FechaNoEncontradaException {

        Fecha fechaModificada = obtenerFechaPorId(idFecha);

        EstadoFecha estadoAnterior = fechaModificada.getEstadoFecha();

        fechaModificada.setNumeroDeFecha(numero);
        fechaModificada.setEstadoFecha(estado);

        repositorioFecha.actualizarFecha(fechaModificada);

        /*
          Cuando finaliza una fecha, se busca la siguiente fecha programada
          y se copian automáticamente las formaciones de los equipos.
         */
        if (estadoAnterior == EstadoFecha.EN_CURSO && estado == EstadoFecha.FINALIZADA) {
            Fecha fechaSiguiente = repositorioFecha.buscarFechaProgramada();

            if (fechaSiguiente != null) {
                servicioEquipo.copiarEquiposDeUnaFechaAOtra(fechaModificada, fechaSiguiente);
            }
        }
    }


    @Override
    public void eliminarFecha(Long id) throws FechaNoEncontradaException {

        Fecha fechaAEliminar = this.obtenerFechaPorId(id);

        repositorioFecha.eliminarFecha(fechaAEliminar);
    }

    @Override
    public List<Fecha> obtenerTodasLasFechas() {
        return repositorioFecha.buscarTodasLasFechas();
    }


    @Override
    public Fecha obtenerFechaActual() throws FechaNoEncontradaException {
        Fecha fechaEnCurso = repositorioFecha.buscarFechaEnCurso();

        if (fechaEnCurso != null) {
            return fechaEnCurso;
        } else {
            Fecha fechaProgramada = repositorioFecha.buscarFechaProgramada();

            if (fechaProgramada != null) {
                return fechaProgramada;
            } else {
                throw new FechaNoEncontradaException("No existe una fecha activa.");
            }
        }
    }


}
