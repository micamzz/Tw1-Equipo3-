package com.tallerwebi.dominio.excepcion;

public class TemporadaFueraDeRangoException extends RuntimeException {
    public TemporadaFueraDeRangoException(String message) {
        super(message);
    }
}
