package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service("servicioFixture")
@Transactional
public class ServicioFixtureImpl implements ServicioFixture{
    private final RepositorioPartidoNBA repositorioPartidoNBA;
    private final RepositorioEquipoNBA repositorioEquipoNBA;
    private final RepositorioTorneo repositorioTorneo;

    @Autowired
    public ServicioFixtureImpl(RepositorioPartidoNBA repositorioPartidoNBA,
                               RepositorioEquipoNBA repositorioEquipoNBA,
                               RepositorioTorneo repositorioTorneo){
        this.repositorioPartidoNBA = repositorioPartidoNBA;
        this.repositorioEquipoNBA = repositorioEquipoNBA;
        this.repositorioTorneo = repositorioTorneo;
    }

    @Override
    public PartidoNBA crearPartidoNBA(Long idEquipoLocal, Long idEquipoVisitante, LocalDateTime horaInicio, Long idTorneo){
        EquipoNBA local = repositorioEquipoNBA.buscarEquipoPorId(idEquipoLocal);
        EquipoNBA visitante = repositorioEquipoNBA.buscarEquipoPorId(idEquipoVisitante);
        Torneo torneo = repositorioTorneo.buscarTorneoPorId(idTorneo);

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setHoraInicio(horaInicio);
        partido.setTorneo(torneo);
        partido.setEstadoPartido(EstadoPartido.ABIERTO);

        repositorioPartidoNBA.guardar(partido);
        return partido;
    }

    @Override
    public List<PartidoNBA> obtenerTodosLosPartidos() {
        return repositorioPartidoNBA.buscarTodos();
    }

    @Override
    public List<PartidoNBA> obtenerPartidosPorTorneo(Long idTorneo) {
        return repositorioPartidoNBA.buscarPorTorneo(idTorneo);
    }

    @Override
    public void abrirPartido(Long idPartido) {
    PartidoNBA partido = repositorioPartidoNBA.buscarPorId(idPartido);
    partido.setEstadoPartido(EstadoPartido.ABIERTO);
    repositorioPartidoNBA.guardar(partido);
    }

    @Override
    public void cerrarPartido(Long idPartido) {
    PartidoNBA partido = repositorioPartidoNBA.buscarPorId(idPartido);
    partido.setEstadoPartido(EstadoPartido.CERRADO);
    repositorioPartidoNBA.guardar(partido);
    }
}
