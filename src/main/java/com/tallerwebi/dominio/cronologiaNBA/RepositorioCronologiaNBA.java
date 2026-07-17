package com.tallerwebi.dominio.cronologiaNBA;

import java.util.List;

public interface RepositorioCronologiaNBA {

    void guardar(CronologiaNBA cronologia);

    List<CronologiaNBA> buscarPorPartido(Long partidoId);
}