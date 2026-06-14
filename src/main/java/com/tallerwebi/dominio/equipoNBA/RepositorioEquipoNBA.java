package com.tallerwebi.dominio.equipoNBA;

import java.util.List;

public interface RepositorioEquipoNBA {


    EquipoNBA buscarEquipoPorId(Long id);


    void crearEquipo(EquipoNBA equipo);


    List<EquipoNBA> obtenerTodosLosEquiposOrdenados();

    void eliminar(EquipoNBA equipo);
}
