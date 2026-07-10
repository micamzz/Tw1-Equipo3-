package com.tallerwebi.dominio.excepcion;

public class NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException extends Exception {
    public NoSePuedeModificarEquipoSiLaFechaEstaEnCursoException(String message) {
        super(message);
    }
}
