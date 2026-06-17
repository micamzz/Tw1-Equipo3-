package com.tallerwebi.dominio;

import com.tallerwebi.dominio.equipo.Equipo;
import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.temporada.Temporada;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PartidoNBA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String equipoLocal;
    private String equipoVisitante;
    private LocalDateTime fechaYhora;

    @ManyToOne
    @JoinColumn(name = "calendario_id")
    private Calendario calendario;

    @Enumerated(EnumType.STRING)
    private EstadoPartido estadoPartido;

    public PartidoNBA(String equipoLocal, String equipoVisitante, LocalDateTime fechaYhora) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fechaYhora = fechaYhora;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
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
        return String.format("%02d/%02d/%d", fechaYhora.getDayOfMonth(), fechaYhora.getMonthValue(), fechaYhora.getYear());
    }
}