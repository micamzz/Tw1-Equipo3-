package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    @Column(unique = true, nullable = false)
    private Integer dni;

    @Enumerated(EnumType.STRING)
    private Posicion posicion;

    @ManyToOne
    private Equipo equipo;
}
