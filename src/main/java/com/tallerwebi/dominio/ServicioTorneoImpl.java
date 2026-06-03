package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.FechaIncoherenteException;
import com.tallerwebi.dominio.excepcion.FechasSuperpuestasException;
import com.tallerwebi.dominio.excepcion.NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ServicioTorneoImpl implements ServicioTorneo {

    private RepositorioTorneo repositorioTorneo;
    private RepositorioEquipo repositorioEquipo;

    @Autowired
    public ServicioTorneoImpl(
            RepositorioTorneo repositorioTorneo,
            RepositorioEquipo repositorioEquipo) {
        this.repositorioTorneo = repositorioTorneo;
        this.repositorioEquipo = repositorioEquipo;
    }

    private void validarQueNoSeSuperponganFechas(TorneoVirtual torneoNuevo) throws FechasSuperpuestasException {
        List<TorneoVirtual> torneosExistentes = repositorioTorneo.obtenerTodosLosTorneosVirtuales();

        for (TorneoVirtual torneoExistente : torneosExistentes) {
            boolean seSuperponen =
                    !torneoNuevo.getFechaFin().isBefore(torneoExistente.getFechaInicio())
                            &&
                            !torneoNuevo.getFechaInicio().isAfter(torneoExistente.getFechaFin());
            if (seSuperponen) {
                throw new FechasSuperpuestasException("Ya existe un torneo en ese rango de fechas");
            }

        }
    }


    @Override
    public void crearTorneo(TorneoVirtual torneo) throws FechaIncoherenteException, FechasSuperpuestasException {
        if (torneo.getFechaFin().isBefore(torneo.getFechaInicio())) {
            throw new FechaIncoherenteException("La fecha de finalizacion del torneo debe ser posterior a la fecha de inicio");

        }
        validarQueNoSeSuperponganFechas(torneo);
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
    public void eliminarTorneo(Long id) throws NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException {

        if (repositorioEquipo.existeEquipoEnTorneo(id)){
            throw new NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException("No se puede eliminar el torneo ya que tiene equipos asociados");
        }
       Torneo torneo = buscarTorneoPorId(id);

        repositorioTorneo.eliminarTorneo(torneo);

    }

    @Override
    public List<TorneoVirtual> obtenerTodosLosTorneos() {
        return repositorioTorneo.obtenerTodosLosTorneosVirtuales();
    }


}
