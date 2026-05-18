package com.tallerwebi.dominio;

public interface RepositorioEquipo {

    void guardarEquipo(Equipo equipo);

    Equipo buscarEquipoPorId(Long id);

    Equipo buscarEquipoPorNombre(String nombre);
}
