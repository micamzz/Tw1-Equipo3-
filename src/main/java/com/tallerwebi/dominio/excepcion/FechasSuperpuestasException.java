package com.tallerwebi.dominio.excepcion;

public class FechasSuperpuestasException extends Exception {

    private static final long serialVersionUID = 1L;
    public FechasSuperpuestasException(String mensaje) {
        super(mensaje);
    }
}
