package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    

}
