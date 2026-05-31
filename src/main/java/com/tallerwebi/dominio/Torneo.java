package com.tallerwebi.dominio;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String nombreTorneo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate fechaFin;
    @Enumerated(EnumType.STRING)
    protected EstadoTorneo estadoTorneo;

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
