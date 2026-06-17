package com.tallerwebi.dominio;

import com.tallerwebi.dominio.PartidoNBA;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;

import javax.persistence.*;

@Entity
public class ScorePartido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PartidoNBA partido;

    @ManyToOne
    private EquipoNBA equipo;

    private Integer puntos;

    public ScorePartido() {}

    public ScorePartido(PartidoNBA partido, EquipoNBA equipo) {
        this.partido = partido;
        this.equipo = equipo;
        this.puntos = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PartidoNBA getPartido() { return partido; }
    public void setPartido(PartidoNBA partido) { this.partido = partido; }

    public EquipoNBA getEquipo() { return equipo; }
    public void setEquipo(EquipoNBA equipo) { this.equipo = equipo; }

    public Integer getPuntos() { return puntos; }
    public void setPuntos(Integer puntos) { this.puntos = puntos; }

    public void sumarPuntos(int cantidad) { this.puntos += cantidad; }
}
