package com.tallerwebi.dominio.equipo;

public interface RepositorioEquipo {

    void guardarEquipo(Equipo equipo);

    Equipo buscarEquipoPorId(Long id);

    Equipo buscarEquipoPorNombre(String nombre);

    boolean existeEquipoEnTorneo(Long idTorneo);

    void actualizarEquipo(Equipo equipo);

    Equipo buscarEquipoPorIdUsuario(Long usuarioId);
}
