package com.tallerwebi.dominio;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JugadorPlantilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PlantillaUsuario plantillaUsuario;

    private Jugador jugador;

    private Ronda ronda;

    private RolJugador rolJugador;

    private Boolean capitan;

    private BigDecimal precioCompra;

    public PlantillaUsuario getPlantillaUsuario() {
        return plantillaUsuario;
    }

    public void setPlantillaUsuario(PlantillaUsuario plantillaUsuario) {
        this.plantillaUsuario = plantillaUsuario;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Ronda getRonda() {
        return ronda;
    }

    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    public RolJugador getRolJugador() {
        return rolJugador;
    }

    public void setRolJugador(RolJugador rolJugador) {
        this.rolJugador = rolJugador;
    }

    public Boolean isCapitan() {
        return capitan;
    }

    public void setCapitan(Boolean capitan) {
        this.capitan = capitan;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }
}
