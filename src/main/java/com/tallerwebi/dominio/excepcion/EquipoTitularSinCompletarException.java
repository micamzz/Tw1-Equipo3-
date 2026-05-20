package com.tallerwebi.dominio.excepcion;

public class EquipoTitularSinCompletarException extends Exception {
    private static final long serialVersionUID = 1L;

    public EquipoTitularSinCompletarException(String message) {
        super(message);
    }
}
