package com.tallerwebi.dominio.equipoJugador;

import java.util.List;

public interface RepositorioEquipoJugador {

    void guardarEquipoJugador(EquipoJugador equipoJugador);

    List<EquipoJugador> buscarPorEquipoId(Long id);

    EquipoJugador buscarEquipoYJugadorAsociado(Long idEquipo, Long idJugador);

    void eliminarEquipoJugador(EquipoJugador equipoJugador);


}
