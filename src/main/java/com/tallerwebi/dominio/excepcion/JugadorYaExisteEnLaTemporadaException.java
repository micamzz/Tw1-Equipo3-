package com.tallerwebi.dominio.excepcion;

public class JugadorYaExisteEnLaTemporadaException extends Exception {
    public JugadorYaExisteEnLaTemporadaException() {
        super("El jugador ya pertenece a un equipo en esta temporada");
    }
}
