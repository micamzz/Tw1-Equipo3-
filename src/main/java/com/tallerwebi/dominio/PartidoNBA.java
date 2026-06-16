package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PartidoNBA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private EquipoNBA equipoLocal;

    @ManyToOne
    private EquipoNBA equipoVisitante;

    private LocalDateTime fechaYhora;

    @ManyToOne
    private Calendario calendario;

    public PartidoNBA() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EquipoNBA getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(EquipoNBA equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public EquipoNBA getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(EquipoNBA equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public LocalDateTime getFechaYhora() {
        return fechaYhora;
    }

    public void setFechaYhora(LocalDateTime fechaYhora) {
        this.fechaYhora = fechaYhora;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public String getHora() {
        return String.format("%02d:%02d", fechaYhora.getHour(), fechaYhora.getMinute());
    }

    public String getFecha() {
        return String.format("%02d/%02d/%d",
                fechaYhora.getDayOfMonth(),
                fechaYhora.getMonthValue(),
                fechaYhora.getYear());
    }
}