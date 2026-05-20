package com.tallerwebi.dominio.excepcion;

public class CapitanNoEsTitularException extends RuntimeException {

    public CapitanNoEsTitularException(String message) {
        super(message);
    }
}