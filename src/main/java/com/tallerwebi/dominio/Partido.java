package com.tallerwebi.dominio;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Ronda ronda;

    private LocalDateTime fechaPartido;

    private Equipo equipoLocal;
    private Integer puntosEquipoLocal;

    private Equipo equipoVisitante;
    private Integer puntosEquipoVisitante;

    public Ronda getRonda() {
        return ronda;
    }

    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    public LocalDateTime getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(LocalDateTime fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Integer getPuntosEquipoLocal() {
        return puntosEquipoLocal;
    }

    public void setPuntosEquipoLocal(Integer puntosEquipoLocal) {
        this.puntosEquipoLocal = puntosEquipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public Integer getPuntosEquipoVisitante() {
        return puntosEquipoVisitante;
    }

    public void setPuntosEquipoVisitante(Integer puntosEquipoVisitante) {
        this.puntosEquipoVisitante = puntosEquipoVisitante;
    }

    public Long getId() {
        return id;
    }
}
