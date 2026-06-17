package com.tallerwebi.dominio.excepcion;

public class JugadorNoPerteneceAlEquipoException extends Exception {
    public JugadorNoPerteneceAlEquipoException(String mensaje) {
        super(mensaje);
    }
}
