package com.tallerwebi.dominio.equipo;

import com.tallerwebi.dominio.enums.PosicionJugadorEquipo;
import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioEquipo {

    void agregarJugadorAlEquipo(Long idEquipo, Long idJugador, Integer numeroDeOrden) throws EquipoNoEncontradoException, PresupuestoInsuficienteException, elJugadorYaExisteEnElEquipoException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException;

    void eliminarJugadorDelEquipo(Long idEquipo, Long idJugador) throws EquipoNoEncontradoException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException;

    Equipo guardarEquipo(Equipo equipo) throws TorneoVirtualActualNoEncontradoException;

    /*Equipo guardarEquipo(Equipo equipo, Usuario usuario) throws TorneoVirtualActualNoEncontradoException;
     */
    List<EquipoJugador> buscarJugadoresDelEquipo(Long idEquipo);

    Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException;

    Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException;

    void validarEquipoCompleto(Long idEquipo) throws EquipoSinCompletarException;

    void actualizarEquipo(Equipo equipo);

    Equipo obtenerEquipoPorIdUsuario(Long usuarioId);

    Double obtenerPresupuestoInicial();

    Double calcularPuntajeTotalDelEquipo(Long equipoId);

    void asignarRolEspecial(Long idEquipo, Long idJugador, PosicionJugadorEquipo rol) throws EquipoNoEncontradoException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException;

    List<Equipo> obtenerTopEquiposPorTorneo(Long torneoId, int limite);

    void validarQueSePuedaModificarEquipo() throws NoSePuedeModificarEquipoSiHayPartidosEnCursoException;

    Boolean puedeModificarEquipo();
}
