package com.tallerwebi.dominio.excepcion;

public class elJugadorYaExisteEnElEquipoException extends Exception {

    private static final long serialVersionUID = 1L;

    public elJugadorYaExisteEnElEquipoException(String message) {
        super(message);
    }
}
