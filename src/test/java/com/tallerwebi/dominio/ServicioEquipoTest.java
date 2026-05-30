package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioEquipoTest {

    private ServicioEquipo servicioEquipo;
    private RepositorioEquipo repositorioEquipoMock;
    private RepositorioJugador repositorioJugadorMock;
    private RepositorioEquipoJugador repositorioEquipoJugadorMock;
    private Equipo equipoMock;

    /*
    1- Buscar un equipo por ID devuelve el correcto
    2- Buscar un equipo con Id incorrecto devuelve excepcion
    3- Buscar un equipo por nombre devuelve el correcto
    4-Buscar un equipo con nombre incorrecto devuelve excepcion
    5- Cuando se agrega un jugador al equipo se agrega correctamente
    6- Cuando se agrega un jugador existente al equipo devuelve una excepcion
    7- Cuando se agrega un jugador y el presupuesto es insuficiente devuelve una excepcion
    * */

    @BeforeEach
    public void inicializacion() {
        this.repositorioEquipoMock = mock(RepositorioEquipo.class);
        this.repositorioJugadorMock = mock(RepositorioJugador.class);
        this.repositorioEquipoJugadorMock = mock(RepositorioEquipoJugador.class);
        this.servicioEquipo = new ServicioEquipoImpl(repositorioEquipoMock, repositorioJugadorMock, repositorioEquipoJugadorMock);
        this.equipoMock = mock(Equipo.class);

    }


    @Test
    public void alBuscarUnEquipoPorIdDevuelveElEquipoCorrecto() throws EquipoNoEncontradoException {

        //preparacion se necesita a equipoMock que esta inicializado en el before

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
    public void alBuscarUnEquipoPorNombreDevuelveElEquipoCorrecto() throws EquipoNoEncontradoException {

        //preparacion se necesita a equipoMock que esta inicializado en el before

        when(repositorioEquipoMock.buscarEquipoPorNombre("NBA")).thenReturn(equipoMock);

        //    Ejecucion
        Equipo equipoEncontrado = servicioEquipo.buscarEquipoPorNombre("NBA");

        // validacion
        assertEquals(equipoMock, equipoEncontrado);
    }

    @Test
    public void alBuscarUnEquipoPorNombreIncorrectoDevuelveUnaExcepcion() {

        //Preparacion
        String nombreNoEncontrado = "ParenLaMano";
        when(repositorioEquipoMock.buscarEquipoPorNombre(nombreNoEncontrado)).thenReturn(null);

        //Ejecucion y verificacion
        assertThrows(EquipoNoEncontradoException.class, () -> {
            servicioEquipo.buscarEquipoPorNombre(nombreNoEncontrado);
        });
    }

    /* Se agrega un jugador correctamente, no esta registrado y el presupuesto alcanza*/

    @Test
    public void cuandoSeAgregaUnJugadorAlEquipoSeAgregaCorrectamente() throws elJugadorYaExisteEnElEquipoException, EquipoNoEncontradoException, PresupuestoInsuficienteException, elJugadorYaExisteEnElEquipoException, PresupuestoInsuficienteException {

        //preparacion se necesita a equipoMock que esta inicializado en el before
        Long idEquipo = 3L;
        Long idJugador = 4L;
        Jugador jugadormock = mock(Jugador.class);

        // metodo buscarEquipoPorId
        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
        // metodo buscarJugadorPorId
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadormock);
        // metodo de equipoyjugador asociados para el metodo validarSiElJugadorYaFueElegido
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);

        //valores para verificar si el presuesto alcanza
        when(equipoMock.getPresupuesto()).thenReturn(2000000D);
        when(jugadormock.getPrecio()).thenReturn(150000.0);

//        Ejecucion
        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1);

//        Verificacion
        verify(repositorioEquipoJugadorMock, times(1)).guardarEquipoJugador(any(EquipoJugador.class));
    }

    @Test
    public void cuandoSeAgregaUnJugadorYaExistenteAlEquipoLanzaUnaExcepcion() throws elJugadorYaExisteEnElEquipoException, EquipoNoEncontradoException, PresupuestoInsuficienteException {

        //preparacion se necesita a equipoMock que esta inicializado en el before
        Long idEquipo = 3L;
        Long idJugador = 4L;
        Jugador jugadormock = mock(Jugador.class);

        // metodo buscarEquipoPorId
        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
        // metodo buscarJugadorPorId
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadormock);
        // metodo de equipoyjugador asociados para el metodo validarSiElJugadorYaFueElegido

        EquipoJugador equipoJugadorMock = mock(EquipoJugador.class);
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(equipoJugadorMock);

        //valores para verificar si el presuesto alcanza
        when(equipoMock.getPresupuesto()).thenReturn(850000D);
        when(jugadormock.getPrecio()).thenReturn(150000.0);

//        ejecucion y Verificacion
        /*
        assertThrows(UsuarioExistente.class, () -> this.servicioLogin.registrar(usuario));
        verify(this.repositorioUsuarioMock, times(0)).guardar(usuario);
*/
        assertThrows(elJugadorYaExisteEnElEquipoException.class, () -> servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1));
    }

    @Test
    public void cuandoSeAgregaUnJugadorAlEquipoYElPresupuestoNoAlcanzaLanzaUnaExcepcion() throws elJugadorYaExisteEnElEquipoException, EquipoNoEncontradoException, PresupuestoInsuficienteException {

        //preparacion se necesita a equipoMock que esta inicializado en el before
        Long idEquipo = 3L;
        Long idJugador = 4L;
        Jugador jugadormock = mock(Jugador.class);

        // metodo buscarEquipoPorId
        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
        // metodo buscarJugadorPorId
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadormock);
        // metodo de equipoyjugador asociados para el metodo validarSiElJugadorYaFueElegido
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);

        //valores para verificar si el presuesto alcanza
        when(equipoMock.getPresupuesto()).thenReturn(850000D);
        when(jugadormock.getPrecio()).thenReturn(850001.0);

        //Ejecucion y  Verificacion
        assertThrows(PresupuestoInsuficienteException.class, () -> servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1));
    }


}
