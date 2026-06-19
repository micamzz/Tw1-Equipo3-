/*
package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.temporada.RepositorioTemporada;
import com.tallerwebi.dominio.temporada.Temporada;
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
    private final RepositorioTemporada repositorioTemporada;

    @Autowired
    public ServicioFixtureImpl(RepositorioPartidoNBA repositorioPartidoNBA,
                               RepositorioEquipoNBA repositorioEquipoNBA,
                               RepositorioTemporada repositorioTemporada){
        this.repositorioPartidoNBA = repositorioPartidoNBA;
        this.repositorioEquipoNBA = repositorioEquipoNBA;
        this.repositorioTemporada = repositorioTemporada;
    }

    @Override
    public PartidoNBA crearPartidoNBA(Long idEquipoLocal, Long idEquipoVisitante, LocalDateTime horaInicio, Long idTemporada){
        EquipoNBA local = repositorioEquipoNBA.buscarEquipoPorId(idEquipoLocal);
        EquipoNBA visitante = repositorioEquipoNBA.buscarEquipoPorId(idEquipoVisitante);
        Temporada temporada = repositorioTemporada.obtenerTemporadaPorId(idTemporada);

        PartidoNBA partido = new PartidoNBA();
        partido.setEquipoLocal(local);
        partido.setEquipoVisitante(visitante);
        partido.setHoraInicio(horaInicio);
        partido.setTemporada(temporada);
        partido.setEstadoPartido(EstadoPartido.ABIERTO);

        repositorioPartidoNBA.guardar(partido);
        return partido;
    }

    @Override
    public List<PartidoNBA> obtenerTodosLosPartidos() {
        return repositorioPartidoNBA.buscarTodos();
    }

    @Override
    public List<PartidoNBA> obtenerPartidosPorTemporada(Long idTemporada) {
        return repositorioPartidoNBA.buscarPorTemporada(idTemporada);
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
*/
