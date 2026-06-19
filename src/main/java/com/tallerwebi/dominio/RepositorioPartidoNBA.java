package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPartidoNBA {

    void guardar(PartidoNBA partido);

    void actualizar(PartidoNBA partido);

    PartidoNBA buscarPorId(Long id);

    List<PartidoNBA> buscarPartidosActivos();

    List<PartidoNBA> buscarPartidosFinalizados();

    boolean equipoTienePartidoActivo(Long equipoId);

    List<PartidoNBA> buscarTodos();
    List<PartidoNBA> buscarPorTorneo(Long torneoId);
}