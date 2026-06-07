package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEquipoNBA {


    EquipoNBA buscarEquipoPorId(Long id);


    void crearEquipo(EquipoNBA equipo);


    List<EquipoNBA> obtenerTodosLosEquipos();
}
