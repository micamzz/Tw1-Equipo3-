package com.tallerwebi.dominio;

import java.util.HashMap;
import java.util.List;

public interface RepositorioEquipoJugador {

    void guardarEquipoJugador(EquipoJugador equipoJugador);

    List<EquipoJugador> buscarPorEquipoId(Long id);

}
