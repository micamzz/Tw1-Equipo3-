package com.tallerwebi.dominio.fecha;

import com.tallerwebi.dominio.torneo.Torneo;
import com.tallerwebi.dominio.enums.EstadoFecha;

import javax.persistence.*;

@Entity
public class Fecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_fecha", nullable = false)
    private Integer numeroDeFecha;

    @ManyToOne
    private Torneo torneo;


    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoFecha estadoFecha;


    public Fecha() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public EstadoFecha getEstadoFecha() {
        return estadoFecha;
    }

    public void setEstadoFecha(EstadoFecha estadoFecha) {
        this.estadoFecha = estadoFecha;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Integer getNumeroDeFecha() {
        return numeroDeFecha;
    }

    public void setNumeroDeFecha(Integer numeroDeFecha) {
        this.numeroDeFecha = numeroDeFecha;
    }
}
