package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ServicioTorneoImpl implements ServicioTorneo {

    private RepositorioTorneo repositorioTorneo;

    @Autowired
    public ServicioTorneoImpl(
            RepositorioTorneo repositorioTorneo) {

        this.repositorioTorneo = repositorioTorneo;
    }

    @Override
    public void crearTorneo(TorneoVirtual torneo) {
        TorneoVirtual torneoActual = repositorioTorneo.buscarTorneoVirtualActual();

        if(torneoActual != null) {
            throw new  RuntimeException("Ya existe un torneo en curso.");
        }
        torneo.setEstadoTorneo(EstadoTorneo.EN_CURSO);

        repositorioTorneo.guardarTorneo(torneo);
    }

    @Override
    public TorneoVirtual obtenerTorneoActual() {
        return repositorioTorneo.buscarTorneoVirtualActual();
    }

    @Override
    public Torneo buscarTorneoPorId(Long id) {
        return repositorioTorneo.buscarTorneoPorId(id);
    }

    @Override
    public void finalizarTorneo(Long id) {
        Torneo torneo = buscarTorneoPorId(id);
        torneo.setEstadoTorneo(EstadoTorneo.FINALIZADO);
        repositorioTorneo.actualizarTorneo(torneo);
    }


}
