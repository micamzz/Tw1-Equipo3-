package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.RepositorioEquipoNBA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
    @Service
    @Transactional
    public class ServicioFormacionImpl implements ServicioFormacion {

        private RepositorioFormacion repositorioFormacion;
        private RepositorioJugador repositorioJugador;
        private RepositorioEquipoNBA repositorioEquipoNBA;
        private RepositorioPartidoNBA repositorioPartidoNBA;

        @Autowired
        public ServicioFormacionImpl(RepositorioFormacion repositorioFormacion,RepositorioJugador repositorioJugador, RepositorioEquipoNBA repositorioEquipoNBA, RepositorioPartidoNBA repositorioPartidoNBA) {
            this.repositorioFormacion = repositorioFormacion;
            this.repositorioJugador = repositorioJugador;
            this.repositorioEquipoNBA = repositorioEquipoNBA;
            this.repositorioPartidoNBA = repositorioPartidoNBA;
        }
        @Override
        public void agregarJugador(Long idPartido, Long idEquipo, Long idJugador) {
            if(!repositorioFormacion.jugadorYaEstaEnFormacion(idPartido, idJugador)){
                PartidoNBA partido = repositorioPartidoNBA.buscarPorId(idPartido);
                EquipoNBA equipo = repositorioEquipoNBA.buscarEquipoPorId(idEquipo);
                Jugador jugador = repositorioJugador.buscarJugadorPorId(idJugador);

                FormacionPartido formacion = new FormacionPartido();

                formacion.setPartido(partido);
                formacion.setEquipo(equipo);
                formacion.setJugador(jugador);

                repositorioFormacion.guardar(formacion);
            }
        }

        @Override
        public void quitarJugador(Long idFormacion) {
            repositorioFormacion.eliminar(idFormacion);
        }

        @Override
        public List<FormacionPartido> obtenerFormacion(Long idPartido) {
            return repositorioFormacion.buscarPorPartido(idPartido);
        }

        @Override
        public List<FormacionPartido> obtenerFormacionPorEquipo(Long idPartido, Long idEquipo) {
            return repositorioFormacion.buscarPorPartidoYEquipo(idPartido, idEquipo);
        }


        @Override
        public boolean jugadorYaEstasEnFormacion(Long idPartido, Long idJugador) {
            return repositorioFormacion.jugadorYaEstaEnFormacion(idPartido, idJugador);
        }
    }


