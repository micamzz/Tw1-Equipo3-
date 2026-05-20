package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class EquipoJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Equipo equipo;

    @ManyToOne
    private Jugador jugador;

    //numeroOrden es el valor que le voy a asignar para saber si es titular (1 a 5), suplente (6 a 10) y 6to (6) para luego calcular los puntos del equipo.
    private Integer numeroOrden;


    public EquipoJugador() {
    }

    public EquipoJugador(Equipo equipo, Jugador jugador, Integer numeroOrden) {
        this.equipo = equipo;
        this.jugador = jugador;
        this.numeroOrden = numeroOrden;
    }



    public Long getId() {
        return id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Integer getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Integer numeroOrden) {
        this.numeroOrden = numeroOrden;
    }
}
