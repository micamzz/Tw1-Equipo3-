package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCalendario {
    Calendario obtenerCalendario();
    List<RendimientoJugador> Top6Jugadores();
}
