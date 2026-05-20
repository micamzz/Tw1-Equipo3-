package com.tallerwebi.dominio;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class JugadorPlantilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plantilla_usuario_id")
    private PlantillaUsuario plantillaUsuario;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "ronda_id")
    private Ronda ronda;

    @Enumerated(EnumType.STRING)
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
