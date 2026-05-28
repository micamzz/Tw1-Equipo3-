package com.tallerwebi.dominio;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreTorneo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoTorneo estadoTorneo;

    public Torneo() {
    }

    public Long getId() {
        return id;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    public EstadoTorneo getEstadoTorneo() {
        return estadoTorneo;
    }
    public void setEstadoTorneo(EstadoTorneo estadoTorneo) {
        this.estadoTorneo = estadoTorneo;
    }
}
