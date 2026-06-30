package com.tallerwebi.dominio;


public enum TipoEstadistica {
    DOBLE ("+ 2 puntos"),
    TRIPLE ("+ 3 puntos"),
    TIRO_LIBRE ("+ 1 punto"),
    REBOTE ("Rebote"),
    ASISTENCIA ("Asistencia"),
    ROBO ("Recuperación"),
    TAPA ("Tapa"),
    PERDIDA ("Perdida"),
    FALTA_PERSONAL ("Falta personal");

    private final String descripcion;

    TipoEstadistica(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }



}