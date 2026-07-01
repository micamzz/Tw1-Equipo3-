package com.tallerwebi.dominio.excepcion;

public class NoSePuedeModificarEquipoSiHayPartidosEnCursoException extends Exception {
    public NoSePuedeModificarEquipoSiHayPartidosEnCursoException(String message) {
        super(message);
    }
}
