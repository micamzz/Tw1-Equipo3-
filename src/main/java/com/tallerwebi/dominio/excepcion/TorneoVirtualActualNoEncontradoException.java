package com.tallerwebi.dominio.excepcion;

public class TorneoVirtualActualNoEncontradoException extends Exception {

    private static final long serialVersionUID = 1L;

    public TorneoVirtualActualNoEncontradoException(String message) {
        super(message);
    }
}
