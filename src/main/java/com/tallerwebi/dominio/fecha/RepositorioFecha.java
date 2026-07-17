package com.tallerwebi.dominio.fecha;

import java.util.List;

public interface RepositorioFecha {

    void guardarFecha(Fecha fecha);

    Fecha buscarFechaPorId(Long id);

    void actualizarFecha(Fecha fecha);

    void eliminarFecha(Fecha fecha);

    List<Fecha> buscarTodasLasFechas();

    Fecha buscarFechaEnCurso();

    Fecha buscarFechaProgramada();
}
