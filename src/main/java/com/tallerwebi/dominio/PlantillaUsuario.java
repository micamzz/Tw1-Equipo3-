package com.tallerwebi.dominio;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PlantillaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Usuario usuario;

    private String nombrePlantilla;
    private BigDecimal presupuestoDisponible;

    private Double puntosTotales;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    public BigDecimal getPresupuestoDisponible() {
        return presupuestoDisponible;
    }

    public void setPresupuestoDisponible(BigDecimal presupuestoDisponible) {
        this.presupuestoDisponible = presupuestoDisponible;
    }

    public Double getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(Double puntosTotales) {
        this.puntosTotales = puntosTotales;
    }
}
