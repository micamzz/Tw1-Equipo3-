package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    @Column(unique = true, nullable = false)
    private Integer dni;
    private Integer precioActual;
    @Enumerated(EnumType.STRING)
    private Posicion posicion;
    private Boolean lesionado;

    // Esto dsp lo sacas mica?
    @ManyToOne
    private Equipo equipo;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public Integer getPrecio() {
        return precioActual;
    }

    public void setPrecio(Integer precioActual) {
        this.precioActual = precioActual;
    }

    public void setLesionado(Boolean lesionado) {
        this.lesionado = lesionado;
    }

    public Boolean getLesionado() {
        return lesionado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
