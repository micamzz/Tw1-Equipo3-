package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCronologiaNBA {

    void guardar(CronologiaNBA cronologia);

    List<CronologiaNBA> buscarPorPartido(Long partidoId);
}