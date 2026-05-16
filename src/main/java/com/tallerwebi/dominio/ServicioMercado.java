package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioMercado {
    List<Jugador> obtenerJugadores(Posicion posicion, String nombre);
}
