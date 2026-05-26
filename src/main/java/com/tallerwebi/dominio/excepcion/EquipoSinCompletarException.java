package com.tallerwebi.dominio.excepcion;

public class EquipoSinCompletarException extends Exception {

    private static final long serialVersionUID = 1L;

    public EquipoSinCompletarException(String message) {
        super(message);
    }
}
