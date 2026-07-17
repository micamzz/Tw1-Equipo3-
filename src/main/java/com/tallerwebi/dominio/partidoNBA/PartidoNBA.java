package com.tallerwebi.dominio.partidoNBA;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.equipoNBA.EstadoPartido;
import com.tallerwebi.dominio.fecha.Fecha;
import com.tallerwebi.dominio.torneo.Torneo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PartidoNBA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoPartido estadoPartido;

    @ManyToOne
    private EquipoNBA equipoLocal;

    @Column(nullable = false)
    @Transient
    private Integer puntosLocal = 0;

    @Column(nullable = false)
    @Transient
    private Integer puntosVisitante = 0;

    @ManyToOne
    private EquipoNBA equipoVisitante;


    private LocalDateTime horaInicio;

    private Integer minutoFin;

    @ManyToOne
    private Torneo torneo;

    @ManyToOne
    private Fecha fecha;

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

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getMinutoFin() {
        return minutoFin;
    }

    public void setMinutoFin(Integer minutoFin) {
        this.minutoFin = minutoFin;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public boolean estaActivo() {
        return minutoFin == null;
    }

    public boolean estaFinalizado() {
        return EstadoPartido.FINALIZADO.equals(this.estadoPartido);
    }

    public EstadoPartido getEstadoPartido() {
        return estadoPartido;
    }

    public void setEstadoPartido(EstadoPartido estadoPartido) {
        this.estadoPartido = estadoPartido;
    }

    public String getFechaFormateada() {
        if (horaInicio == null) return "";
        return String.format("%02d/%02d/%d",
                horaInicio.getDayOfMonth(),
                horaInicio.getMonthValue(),
                horaInicio.getYear());
    }

    public String getHoraFormateada() {
        if (horaInicio == null) return "";
        return String.format("%02d:%02d", horaInicio.getHour(), horaInicio.getMinute());
    }

    public Integer getPuntosLocal() {
        return puntosLocal;
    }

    public void setPuntosLocal(Integer puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public Integer getPuntosVisitante() {
        return puntosVisitante;
    }

    public void setPuntosVisitante(Integer puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }
}