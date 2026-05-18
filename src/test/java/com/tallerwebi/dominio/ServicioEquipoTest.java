package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioEquipoTest {

    private RepositorioEquipo repositorioEquipoMock;
    private ServicioEquipo servicioEquipo;

    @BeforeEach
    public void inicializacion() {
        this.repositorioEquipoMock = mock(RepositorioEquipo.class);
        this.servicioEquipo = new ServicioEquipoImpl(repositorioEquipoMock);

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
    public void alBuscarUnEquipoPorNombreDevuelveUnaExcepcion() {

        String nombreNoEncontrado = "ParenLaMano";
        when(repositorioEquipoMock.buscarEquipoPorNombre(nombreNoEncontrado)).thenReturn(null);

        assertThrows(EquipoNoEncontradoException.class, () -> {
            servicioEquipo.buscarEquipoPorNombre(nombreNoEncontrado);
        });
    }


}
