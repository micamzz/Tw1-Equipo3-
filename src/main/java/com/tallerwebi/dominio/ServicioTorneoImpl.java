package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
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

    private void validarQueNoSeSuperponganFechas(Torneo torneoNuevo) throws FechasSuperpuestasException {
        List<Torneo> torneosExistentes = repositorioTorneo.obtenerTorneosPorTipo(torneoNuevo.getTipoTorneo());

        for (Torneo torneoExistente : torneosExistentes) {
            boolean seSuperponen =
                    !torneoNuevo.getFechaFin().isBefore(torneoExistente.getFechaInicio())
                            &&
                            !torneoNuevo.getFechaInicio().isAfter(torneoExistente.getFechaFin());
            if (seSuperponen) {
                throw new FechasSuperpuestasException("Ya existe un torneo en ese rango de fechas");
            }

        }
    }

    private void validarQueElNombreDelTorneoNoEsteEnBlanco(Torneo torneo) throws NombreDeTorneoEnBlancoException {

        if (torneo.getNombreTorneo() == null || torneo.getNombreTorneo().isBlank()) {
            throw new NombreDeTorneoEnBlancoException("El nombre no puede estar vacío");
        }
    }

    @Override
    public void crearTorneo(Torneo torneo) throws FechaIncoherenteException, FechasSuperpuestasException, NombreDeTorneoEnBlancoException, TipoDeTorneoEnBlancoException {

        if (torneo.getFechaInicio() == null || torneo.getFechaFin() == null) {
            throw new FechaIncoherenteException("La fecha de inicio y la fecha de finalizacion del torneo no pueden estar vacias");
        }

        if (torneo.getTipoTorneo() == null) {
            throw new TipoDeTorneoEnBlancoException("El tipo de torneo no puede estar vacio");
        }

        if (torneo.getFechaFin().isBefore(torneo.getFechaInicio())) {
            throw new FechaIncoherenteException("La fecha de finalizacion del torneo debe ser posterior a la fecha de inicio");

        }
        validarQueElNombreDelTorneoNoEsteEnBlanco(torneo);
        validarQueNoSeSuperponganFechas(torneo);
        repositorioTorneo.guardarTorneo(torneo);
    }

    @Override
    public Torneo obtenerTorneoActual(TipoTorneo tipoTorneo) {

        return repositorioTorneo.buscarTorneoActual(tipoTorneo);
    }

    @Override
    public Torneo buscarTorneoPorId(Long id) {
        return repositorioTorneo.buscarTorneoPorId(id);
    }


    @Override
    public void eliminarTorneo(Long id) throws NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException, TorneoNoEncontradoException {

        Torneo torneo = buscarTorneoPorId(id);

        if (torneo == null) {
            throw new TorneoNoEncontradoException("El torneo que intentas eliminar NO existe");
        }

        if (repositorioEquipo.existeEquipoEnTorneo(id)) {
            throw new NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException("No se puede eliminar el torneo ya que tiene equipos asociados");
        }

        repositorioTorneo.eliminarTorneo(torneo);

    }

    @Override
    public List<Torneo> obtenerTodosLosTorneos() {

        return repositorioTorneo.obtenerTodosLosTorneos();
    }


}
