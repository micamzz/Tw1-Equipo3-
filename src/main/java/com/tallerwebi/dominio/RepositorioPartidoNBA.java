package com.tallerwebi.dominio;

import java.time.LocalDateTime;
import java.util.List;

public interface RepositorioPartidoNBA {

    void guardar(PartidoNBA partido);

    void actualizar(PartidoNBA partido);

    PartidoNBA buscarPorId(Long id);

    List<PartidoNBA> buscarPartidosActivos();

    List<PartidoNBA> buscarPartidosFinalizados();

    List<PartidoNBA> buscarPartidosProgramados();

    List<PartidoNBA> buscarPartidosEnVivo();

    boolean equipoTienePartidoActivo(Long equipoId);

    List<PartidoNBA> buscarTodos();

    List<PartidoNBA> buscarPorTorneo(Long torneoId);

    void eliminar(PartidoNBA partido);

    boolean existePartidoEnFecha(LocalDateTime horaInicio);

}