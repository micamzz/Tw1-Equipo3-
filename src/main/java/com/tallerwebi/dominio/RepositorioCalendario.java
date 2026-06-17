package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCalendario {
    FuturosPartidos buscarFuturosPartidosActuales();
    void guardarFuturosPartidos(FuturosPartidos futurosPartidos);
    List<RendimientoJugador> buscarTop6Jugadores();
}
