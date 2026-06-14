package com.tallerwebi.dominio.equipo;

import com.tallerwebi.dominio.TorneoVirtual;

import javax.persistence.*;


@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreEquipo;
    private Double presupuesto;

    @ManyToOne
    private TorneoVirtual torneo;
    // private Usuario usu;


    public Equipo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public TorneoVirtual getTorneo() {
        return torneo;
    }

    public void setTorneo(TorneoVirtual torneo) {
        this.torneo = torneo;
    }
}

