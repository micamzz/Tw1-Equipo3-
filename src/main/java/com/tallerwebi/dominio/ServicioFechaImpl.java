package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FechaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.TorneoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioFechaImpl implements ServicioFecha{

    private final RepositorioFecha repositorioFecha;
    private final RepositorioTorneo repositorioTorneo;

    @Autowired
    public ServicioFechaImpl(RepositorioFecha repositorioFecha,
                             RepositorioTorneo repositorioTorneo) {
        this.repositorioFecha = repositorioFecha;
        this.repositorioTorneo = repositorioTorneo;
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

        Fecha fechaModificada = this.obtenerFechaPorId(idFecha);
        fechaModificada.setNumeroDeFecha(numero);
        fechaModificada.setEstadoFecha(estado);
        repositorioFecha.actualizarFecha(fechaModificada);
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
}
