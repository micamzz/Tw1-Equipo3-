package com.tallerwebi.dominio;


import java.util.List;

public interface RepositorioCalendario{
    Calendario buscarCalendarioActual();
    void guardarCalendario(Calendario calendario);
    List<PartidoNBA> buscarListaDePartidosPorCalendarioID(Long calendarioId);
    List<RendimientoJugador> buscarTop6Jugadores();
}