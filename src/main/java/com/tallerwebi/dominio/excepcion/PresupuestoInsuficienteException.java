package com.tallerwebi.dominio.excepcion;

public class PresupuestoInsuficienteException extends Exception {
    private static final long serialVersionUID = 1L;

    public PresupuestoInsuficienteException(String message) {
        super(message);
    }
}
