package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import com.tallerwebi.dominio.equipoNBAJugador.RepositorioEquipoNBAJugador;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioFormacionImpl implements ServicioFormacion {

    private RepositorioFormacion repositorioFormacion;
    //private RepositorioPartidoNBA repositorioPartidoNBA;
    private RepositorioJugador repositorioJugador;
    private RepositorioEquipoNBA repositorioEquipoNBA;
    private RepositorioEquipoNBAJugador repositorioEquipoNBAJugador;


    public ServicioFormacionImpl(
            RepositorioFormacion repositorioFormacion,
            //RepositorioPartidoNBA repositorioPartidoNBA,
            RepositorioJugador repositorioJugador,
            RepositorioEquipoNBA repositorioEquipoNBA,
            RepositorioEquipoNBAJugador repositorioEquipoNBAJugador) {

        this.repositorioFormacion = repositorioFormacion;
        //this.repositorioPartidoNBA = repositorioPartidoNBA;
        this.repositorioJugador = repositorioJugador;
        this.repositorioEquipoNBA = repositorioEquipoNBA;
        this.repositorioEquipoNBAJugador = repositorioEquipoNBAJugador;
    }

    @Override
    public void registrarJugador(Long idPartido, Long idEquipo, Long idJugador) {

       // PartidoNBA partidoNBA = repositorioPartidoNBA.buscarPorId(idPartido);
        EquipoNBA partidoNBA = repositorioEquipoNBA.buscarEquipoPorId(idEquipo); //cambiar esta linea cuando se implemente el repositorio de partidoNBA
        EquipoNBA equipoNBA = repositorioEquipoNBA.buscarEquipoPorId(idEquipo);
        Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);



    }

    @Override
    public List<Formacion> obtenerFormacionPorPartido(Long idPartido) {
        return List.of();
    }

    @Override
    public List<Formacion> obtenerFormacionPorPartidoYEquipo(Long idPartido, Long idEquipo) {
        return List.of();
    }
}
