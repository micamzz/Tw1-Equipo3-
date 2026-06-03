package com.tallerwebi.dominio.excepcion;

public class FechaIncoherenteException extends Exception {
    private static final long serialVersionUID = 1L;

    public FechaIncoherenteException(String mensaje) {
        super(mensaje);
    }
}
