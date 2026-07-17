package com.tallerwebi.dominio.equipoNBA;

import com.tallerwebi.dominio.jugador.RepositorioJugador;
import com.tallerwebi.dominio.torneo.ServicioTorneo;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioEquipoNBAimpl implements ServicioEquipoNBA {

    private final RepositorioEquipoNBA repositorioEquipoNba;
    private final RepositorioJugador repositorioJugador;
    private final RepositorioEquipoNBAJugador repositorioEquipoNBAJugador;
    private final ServicioTorneo servicioTorneo;

    @Autowired
    public ServicioEquipoNBAimpl(RepositorioEquipoNBA repositorioEquipoNba, RepositorioJugador repositorioJugador, RepositorioEquipoNBAJugador repositorioEquipoNBAJugador, ServicioTorneo servicioTorneo) {
        this.repositorioEquipoNba = repositorioEquipoNba;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoNBAJugador = repositorioEquipoNBAJugador;
        this.servicioTorneo = servicioTorneo;
    }


    @Override
    public void crearEquipoNBA(EquipoNBA equipo) {

        repositorioEquipoNba.crearEquipo(equipo);
    }

    @Override
    public EquipoNBA buscarEquipoPorId(Long id) throws EquipoNoEncontradoException {
        EquipoNBA equipoNBA = repositorioEquipoNba.buscarEquipoPorId(id);

        if (equipoNBA == null) {
            throw new EquipoNoEncontradoException("No existe el equipo con id: " + id);
        }
        return equipoNBA;
    }


    @Override
    public List<EquipoNBA> obtenerTodosLosEquiposOrdenadosDeMenorAMayor() {
        return repositorioEquipoNba.obtenerTodosLosEquiposOrdenados();
    }

    @Override
    public void eliminarEquipoNBA(Long idEquipo) throws EquipoNoEncontradoException {

        EquipoNBA equipo = repositorioEquipoNba.buscarEquipoPorId(idEquipo);

        if (equipo == null) {
            throw new EquipoNoEncontradoException("No existe el equipo con id: " + idEquipo);
        }

        repositorioEquipoNBAJugador.eliminarTodasLasAsignacionesDelEquipo(idEquipo);
        repositorioEquipoNba.eliminar(equipo);
    }


}
