package com.tallerwebi.dominio.excepcion;

public class NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException extends Exception {
    public NoSePuedeEliminarUnTorneoSiTieneEquiposAsociadosException(String mensaje) {
        super(mensaje);
    }
}
