package com.tallerwebi.dominio.cronologiaNBA;

import com.tallerwebi.dominio.equipoNBA.EquipoNBA;
import com.tallerwebi.dominio.jugador.Jugador;
import com.tallerwebi.dominio.partidoNBA.PartidoNBA;

import javax.persistence.*;

@Entity
public class CronologiaNBA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PartidoNBA partido;

    private Integer minuto;


    private String tipo;


    private String descripcion;


    private Integer puntosSumados;


    @ManyToOne
    private EquipoNBA equipoBeneficiado;


    @ManyToOne

    private Jugador jugadorSale;


    @ManyToOne

    private Jugador jugadorEntra;

    public CronologiaNBA() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PartidoNBA getPartido() {
        return partido;
    }

    public void setPartido(PartidoNBA partido) {
        this.partido = partido;
    }

    public Integer getMinuto() {
        return minuto;
    }

    public void setMinuto(Integer minuto) {
        this.minuto = minuto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPuntosSumados() {
        return puntosSumados;
    }

    public void setPuntosSumados(Integer puntosSumados) {
        this.puntosSumados = puntosSumados;
    }

    public EquipoNBA getEquipoBeneficiado() {
        return equipoBeneficiado;
    }

    public void setEquipoBeneficiado(EquipoNBA equipoBeneficiado) {
        this.equipoBeneficiado = equipoBeneficiado;
    }

    public Jugador getJugadorSale() {
        return jugadorSale;
    }

    public void setJugadorSale(Jugador jugadorSale) {
        this.jugadorSale = jugadorSale;
    }

    public Jugador getJugadorEntra() {
        return jugadorEntra;
    }

    public void setJugadorEntra(Jugador jugadorEntra) {
        this.jugadorEntra = jugadorEntra;
    }
}