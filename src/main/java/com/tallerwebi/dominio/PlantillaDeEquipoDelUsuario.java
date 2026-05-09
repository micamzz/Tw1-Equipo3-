package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlantillaDeEquipoDelUsuario {
    public static final int TOTAL_PIVOTS = 2;
    public static final int TOTAL_BASES = 4;
    public static final int TOTAL_ALEROS = 4;

    private static final int MAX_PIVOTS_TITULARES = 1;
    private static final int MAX_BASES_TITULARES = 2;
    private static final int MAX_ALEROS_TITULARES = 2;

    // Límites para el bloque de NO titulares (Sexto Hombre + Suplentes)
    private static final int MAX_PIVOTS_NO_TITULARES = 1;
    private static final int MAX_BASES_NO_TITULARES = 2;
    private static final int MAX_ALEROS_NO_TITULARES = 2;

    public static final int TITULARES_COUNT = 5;
    public static final int SUPLENTES_COUNT = 4;

    private final String nombreEquipo;
    private final List<Jugador> titulares;
    private Jugador sextoHombre;
    private final List<Jugador> suplentes;

    public PlantillaDeEquipoDelUsuario(String nombreEquipo) {
        this.nombreEquipo = Objects.requireNonNull(nombreEquipo);
        this.titulares = new ArrayList<>();
        this.suplentes = new ArrayList<>();
        this.sextoHombre = null;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public List<Jugador> getTitulares() {
        return List.copyOf(titulares);
    }

    public Jugador getSextoHombre() {
        return sextoHombre;
    }

    public List<Jugador> getSuplentes() {
        return List.copyOf(suplentes);
    }

    public boolean estaSeleccionado(Jugador jugador) {
        if (jugador == null)
            return false;
        return titulares.contains(jugador) || suplentes.contains(jugador) || Objects.equals(jugador, sextoHombre);
    }

    private long cantidadDeJugadoresDeLaPosicionEnLaLista(List<Jugador> lista, PosicionJugador posicion) {
        return lista.stream()
                .filter(j -> j.getPosicionJugador() == posicion)
                .count();
    }

    public void agregarTitular(Jugador jugador) {
        Objects.requireNonNull(jugador);
        if (estaSeleccionado(jugador))
            throw new IllegalArgumentException("Jugador ya seleccionado en la plantilla");
        if (titulares.size() >= TITULARES_COUNT)
            throw new IllegalStateException("No hay más cupos de titulares");

        validarCupoEnEquipoTitular(jugador.getPosicionJugador());

        titulares.add(jugador);
    }

    public void asignarSextoHombre(Jugador jugador) {
        Objects.requireNonNull(jugador);
        if (estaSeleccionado(jugador))
            throw new IllegalArgumentException("Jugador ya seleccionado en la plantilla");

        validarCupoEnEquipoNoTitular(jugador.getPosicionJugador());

        this.sextoHombre = jugador;
    }

    public void agregarSuplente(Jugador jugador) {
        Objects.requireNonNull(jugador);
        if (estaSeleccionado(jugador))
            throw new IllegalArgumentException("Jugador ya seleccionado en la plantilla");
        if (suplentes.size() >= SUPLENTES_COUNT)
            throw new IllegalStateException("No hay más cupos de suplentes");

        validarCupoEnEquipoNoTitular(jugador.getPosicionJugador());

        suplentes.add(jugador);
    }

    public void removerJugador(Jugador jugador) {
        if (jugador == null)
            return;
        titulares.remove(jugador);
        suplentes.remove(jugador);
        if (Objects.equals(jugador, sextoHombre))
            sextoHombre = null;
    }

    private void validarCupoEnEquipoTitular(PosicionJugador posicion) {
        long actual = cantidadDeJugadoresDeLaPosicionEnLaLista(titulares, posicion);
        int limite = 0;

        switch (posicion) {
            case PIVOT:
                limite = MAX_PIVOTS_TITULARES;
                break;
            case BASE:
                limite = MAX_BASES_TITULARES;
                break;
            case ALERO:
                limite = MAX_ALEROS_TITULARES;
                break;
            default:
                throw new IllegalArgumentException("Posición desconocida: " + posicion);
        }

        if (actual >= limite) {
            throw new IllegalStateException("Ya hay " + limite + " " + posicion + " titulares");
        }
    }

    private void validarCupoEnEquipoNoTitular(PosicionJugador posicion) {
        long actual = cantidadDeJugadoresDeLaPosicionEnLaLista(suplentes, posicion);
        if (sextoHombre != null && sextoHombre.getPosicionJugador() == posicion) {
            actual++;
        }

        int limite = 0;
        switch (posicion) {
            case PIVOT:
                limite = MAX_PIVOTS_NO_TITULARES;
                break;
            case BASE:
                limite = MAX_BASES_NO_TITULARES;
                break;
            case ALERO:
                limite = MAX_ALEROS_NO_TITULARES;
                break;
            default:
                throw new IllegalArgumentException("Posición desconocida: " + posicion);
        }

        if (actual >= limite) {
            throw new IllegalStateException("El cupo de " + posicion + " para suplentes/sexto hombre ya está completo");
        }
    }

    public void validarPlantillaCompleta() {
        if (titulares.size() != TITULARES_COUNT)
            throw new IllegalStateException("La plantilla debe tener exactamente 5 titulares");
        if (suplentes.size() != SUPLENTES_COUNT)
            throw new IllegalStateException("La plantilla debe tener exactamente 4 suplentes");
        if (sextoHombre == null)
            throw new IllegalStateException("Falta asignar el sexto hombre");
    }

    public List<Jugador> getTodosLosJugadoresSeleccionados() {
        return Stream.concat(
                Stream.concat(titulares.stream(), Stream.ofNullable(sextoHombre)),
                suplentes.stream()).collect(Collectors.toUnmodifiableList());
    }
}
