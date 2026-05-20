package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CapitanNoEsTitularException;
import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

public class ServicioEquipoTest {

    private ServicioEquipo servicioEquipo;
    private RepositorioEquipo repositorioEquipoMock;
    private RepositorioJugador repositorioJugadorMock;
    private RepositorioEquipoJugador repositorioEquipoJugadorMock;

    /*
    1- Buscar un equipo por ID devuelve el correcto
    2- Buscar un equipo con Id incorrecto devuelve excepcion
    3- Buscar un equipo por nombre devuelve el correcto
    4-Buscar un equipo con nombre incorrecto devuelve excepcion
    * */

    @BeforeEach
    public void inicializacion() {
        this.repositorioEquipoMock = mock(RepositorioEquipo.class);
        this.repositorioJugadorMock = mock(RepositorioJugador.class);
        this.repositorioEquipoJugadorMock = mock(RepositorioEquipoJugador.class);
        this.servicioEquipo = new ServicioEquipoImpl(repositorioEquipoMock, repositorioJugadorMock, repositorioEquipoJugadorMock);

    }


    @Test
    public void alBuscarUnEquipoPorIdDevuelveElEquipoCorrecto() throws EquipoNoEncontradoException {

        //preparacion
        Equipo equipoMock = mock(Equipo.class);

        // cuando se llame a buscarEquipoPorId(1L) sobre el mock, devolvé equipoMock
        // entonces cuando el servicio llama al metodo recibe ese equipo.
        when(repositorioEquipoMock.buscarEquipoPorId(1L)).thenReturn(equipoMock);

//    Ejecucion
        Equipo equipoEncontrado = servicioEquipo.buscarEquipoPorId(1L);

        // validacion
        assertEquals(equipoMock, equipoEncontrado);
    }

    @Test
    public void alBuscarUnEquipoPorIdDevuelveUnaExcepcion() {
        // preparacion
        Long idNoRegistrado = 3L;
        when(repositorioEquipoMock.buscarEquipoPorId(idNoRegistrado)).thenReturn(null);

        assertThrows(EquipoNoEncontradoException.class, () -> {
            servicioEquipo.buscarEquipoPorId(idNoRegistrado);
        });

    }


    @Test
    public void alBuscarUnEquipoPorNombreDevuelveElEquipoCorrecto() {
        //preparacion
        Equipo equipoMock = mock(Equipo.class);

        when(repositorioEquipoMock.buscarEquipoPorNombre("NBA")).thenReturn(equipoMock);

//    Ejecucion
        Equipo equipoEncontrado = servicioEquipo.buscarEquipoPorNombre("NBA");

        // validacion
        assertEquals(equipoMock, equipoEncontrado);
    }

    @Test
    public void alBuscarUnEquipoPorNombreIncorrectoDevuelveUnaExcepcion() {

        String nombreNoEncontrado = "ParenLaMano";
        when(repositorioEquipoMock.buscarEquipoPorNombre(nombreNoEncontrado)).thenReturn(null);

        assertThrows(EquipoNoEncontradoException.class, () -> {
            servicioEquipo.buscarEquipoPorNombre(nombreNoEncontrado);
        });
    }

    private Jugador jugadorConId(Long id, Posicion posicion, Integer precio) {
        Jugador jugador = new Jugador();
        jugador.setId(id);
        jugador.setPosicion(posicion);
        jugador.setPrecio(precio);
        return jugador;
    }

    @Test
    public void alTenerUnEquipoConJugadoresYUnCapitanQueElCapitanSeIdentifiqueConExito() {
        // El jugador con id=1L es el capitán, está entre los titulares
        Equipo equipo = new Equipo();
        equipo.setId(1L);

        Jugador jugador1 = jugadorConId(1L, Posicion.BASE, 100000);
        Jugador jugador2 = jugadorConId(2L, Posicion.BASE, 100000);
        Jugador jugador3 = jugadorConId(3L, Posicion.ALERO, 100000);
        Jugador jugador4 = jugadorConId(4L, Posicion.ALERO, 100000);
        Jugador jugador5 = jugadorConId(5L, Posicion.PIVOT, 100000);

        when(repositorioEquipoMock.buscarEquipoPorId(1L)).thenReturn(equipo);
        when(repositorioJugadorMock.buscarJugadorPorId(1L)).thenReturn(jugador1);
        when(repositorioJugadorMock.buscarJugadorPorId(2L)).thenReturn(jugador2);
        when(repositorioJugadorMock.buscarJugadorPorId(3L)).thenReturn(jugador3);
        when(repositorioJugadorMock.buscarJugadorPorId(4L)).thenReturn(jugador4);
        when(repositorioJugadorMock.buscarJugadorPorId(5L)).thenReturn(jugador5);

        List<Long> titulares = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        Long idCapitan = 1L;

        servicioEquipo.guardarEquipoCompleto(1L, titulares, idCapitan);

        var captor = org.mockito.ArgumentCaptor.forClass(EquipoJugador.class);
        verify(repositorioEquipoJugadorMock, times(5)).guardarEquipoJugador(captor.capture());

        List<EquipoJugador> jugadoresGuardados = captor.getAllValues();
        EquipoJugador capitanGuardado = jugadoresGuardados.get(0);

        assertTrue(capitanGuardado.getCapitan());
    }

    @Test
    public void alContarElNumeroDeCapitanesExistentesEsDeSolo1() {
        Equipo equipo = new Equipo();
        equipo.setId(1L);

        Jugador j1 = jugadorConId(1L, Posicion.BASE, 100000);
        Jugador j2 = jugadorConId(2L, Posicion.BASE, 100000);
        Jugador j3 = jugadorConId(3L, Posicion.ALERO, 100000);
        Jugador j4 = jugadorConId(4L, Posicion.ALERO, 100000);
        Jugador j5 = jugadorConId(5L, Posicion.PIVOT, 100000);

        when(repositorioEquipoMock.buscarEquipoPorId(1L)).thenReturn(equipo);
        when(repositorioJugadorMock.buscarJugadorPorId(1L)).thenReturn(j1);
        when(repositorioJugadorMock.buscarJugadorPorId(2L)).thenReturn(j2);
        when(repositorioJugadorMock.buscarJugadorPorId(3L)).thenReturn(j3);
        when(repositorioJugadorMock.buscarJugadorPorId(4L)).thenReturn(j4);
        when(repositorioJugadorMock.buscarJugadorPorId(5L)).thenReturn(j5);

        List<Long> titulares = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        servicioEquipo.guardarEquipoCompleto(1L, titulares, 3L);


        ArgumentCaptor<EquipoJugador> captor = ArgumentCaptor.forClass(EquipoJugador.class);
        verify(repositorioEquipoJugadorMock, times(5)).guardarEquipoJugador(captor.capture());
        Integer cantidadCapitanes = 0;

        for (EquipoJugador equipoJugador : captor.getAllValues()) {
            if (equipoJugador.getCapitan()) {
                cantidadCapitanes++;
            }
        }
        assertEquals(1, cantidadCapitanes);
    }

    @Test
    public void siElCapitanNoEstaEntreLosItularesLanzaExcepcion() {
        Equipo equipo = new Equipo();
        equipo.setId(1L);

        when(repositorioEquipoMock.buscarEquipoPorId(1L)).thenReturn(equipo);

        List<Long> titulares = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        Long idCapitanQueNoExiste = 99L;

        assertThrows(CapitanNoEsTitularException.class, () ->
                servicioEquipo.guardarEquipoCompleto(1L, titulares, idCapitanQueNoExiste)
        );
    }

    @Test
    public void siNoSeEligeCapitanLanzaExcepcion() {
        Equipo equipo = new Equipo();
        equipo.setId(1L);

        when(repositorioEquipoMock.buscarEquipoPorId(1L)).thenReturn(equipo);

        List<Long> titulares = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        Long idCapitan = null;

        assertThrows(CapitanNoEsTitularException.class, () ->
                servicioEquipo.guardarEquipoCompleto(1L, titulares, idCapitan)
        );
    }


}
