package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCalendario {
    FuturosPartidos obtenerFuturosPartidos();
    List<RendimientoJugador> Top6Jugadores();
}
