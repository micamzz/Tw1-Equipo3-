package com.tallerwebi.dominio.excepcion;

public class EquipoNoEncontradoException extends RuntimeException {

    // Tuve que usar RunTimeException porq con exception solo tiraba error 
    private static final long serialVersionUID = 1L;

    public EquipoNoEncontradoException(String message) {
        super(message);
    }
}
