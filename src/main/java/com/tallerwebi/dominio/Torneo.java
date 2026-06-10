package com.tallerwebi.dominio;



import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombreTorneo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTorneo tipoTorneo;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate fechaFin;


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


    public TipoTorneo getTipoTorneo() {
        return tipoTorneo;
    }

    public void setTipoTorneo(TipoTorneo tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }

    @Transient //parametro calculado, no se guarda en la BBDD
    public EstadoTorneo getEstadoTorneo() {

        LocalDate hoy = LocalDate.now();

        if (hoy.isBefore(fechaInicio)) {
            return EstadoTorneo.POR_INICIAR;
        }

        if (hoy.isAfter(fechaFin)) {
            return EstadoTorneo.FINALIZADO;
        }

        return EstadoTorneo.EN_CURSO;

    }
}
