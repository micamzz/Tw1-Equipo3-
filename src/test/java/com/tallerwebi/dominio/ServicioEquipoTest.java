package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.EquipoSinCompletarException;
import com.tallerwebi.dominio.excepcion.PresupuestoInsuficienteException;
import com.tallerwebi.dominio.excepcion.elJugadorYaExisteEnElEquipoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioEquipoTest {

    private ServicioEquipo servicioEquipo;
    private RepositorioEquipo repositorioEquipoMock;
    private RepositorioJugador repositorioJugadorMock;
    private RepositorioEquipoJugador repositorioEquipoJugadorMock;
    private Equipo equipoMock;
    private RepositorioTorneo repositorioTorneoMock;

    /*
    1- Buscar un equipo por ID devuelve el correcto
    2- Buscar un equipo con Id incorrecto devuelve excepcion
    3- Buscar un equipo por nombre devuelve el correcto
    4-Buscar un equipo con nombre incorrecto devuelve excepcion
    5- Cuando se agrega un jugador al equipo se agrega correctamente
    6- Cuando se agrega un jugador existente al equipo devuelve una excepcion
    7- Cuando se agrega un jugador y el presupuesto es insuficiente devuelve una excepcion
    Al agregar un jugador al equipo el saldo se descuenta bien
    Al eliminar un jugador del equipo el saldo se reintegra bien
    Al crear un equipo con 12 jugadores se valida el equipo correctamente
    Al crear un equipo con 5 jugadores devuelve una excepcion
    * */

    @BeforeEach
    public void inicializacion() {
        this.repositorioEquipoMock = mock(RepositorioEquipo.class);
        this.repositorioJugadorMock = mock(RepositorioJugador.class);
        this.repositorioEquipoJugadorMock = mock(RepositorioEquipoJugador.class);
        this.repositorioTorneoMock = mock(RepositorioTorneo.class);
        this.servicioEquipo = new ServicioEquipoImpl(repositorioEquipoMock, repositorioJugadorMock, repositorioEquipoJugadorMock, repositorioTorneoMock);
        this.equipoMock = mock(Equipo.class);


    }


    @Test
    public void alBuscarUnEquipoPorIdDevuelveElEquipoCorrecto() throws EquipoNoEncontradoException {

        //preparación se necesita a equipoMock que está inicializado en el before

        // cuando se llame a buscarEquipoPorId(1L) sobre el mock, devuelve equipoMock
        // entonces cuando el servicio llama al método recibe ese equipo.
        when(repositorioEquipoMock.buscarEquipoPorId(1L)).thenReturn(equipoMock);

//    Ejecución
        Equipo equipoEncontrado = servicioEquipo.buscarEquipoPorId(1L);

        // validación
        assertEquals(equipoMock, equipoEncontrado);
    }

    @Test
    public void alBuscarUnEquipoPorIdDevuelveUnaExcepcion() {
        // preparación
        Long idNoRegistrado = 3L;
        when(repositorioEquipoMock.buscarEquipoPorId(idNoRegistrado)).thenReturn(null);

        assertThrows(EquipoNoEncontradoException.class, () -> servicioEquipo.buscarEquipoPorId(idNoRegistrado));

    }

    @Test
    public void alBuscarUnEquipoPorNombreDevuelveElEquipoCorrecto() throws EquipoNoEncontradoException {

        //preparación se necesita a equipoMock que está inicializado en el before

        when(repositorioEquipoMock.buscarEquipoPorNombre("NBA")).thenReturn(equipoMock);

        //    Ejecución
        Equipo equipoEncontrado = servicioEquipo.buscarEquipoPorNombre("NBA");

        // validación
        assertEquals(equipoMock, equipoEncontrado);
    }

    @Test
    public void alBuscarUnEquipoPorNombreIncorrectoDevuelveUnaExcepcion() {

        //Preparación
        String nombreNoEncontrado = "ParenLaMano";
        when(repositorioEquipoMock.buscarEquipoPorNombre(nombreNoEncontrado)).thenReturn(null);

        //Ejecución y verificación
        assertThrows(EquipoNoEncontradoException.class, () -> servicioEquipo.buscarEquipoPorNombre(nombreNoEncontrado));
    }

    /* Se agrega un jugador correctamente, no está registrado y el presupuesto alcanza*/

    @Test
    public void cuandoSeAgregaUnJugadorAlEquipoSeAgregaCorrectamente() throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException, PresupuestoInsuficienteException {

        //preparación se necesita a equipoMock que está inicializado en el before
        Long idEquipo = 3L;
        Long idJugador = 4L;
        Jugador jugadormock = mock(Jugador.class);

        // método buscarEquipoPorId
        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
        // método buscarJugadorPorId
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadormock);
        // método de equipo y jugador asociados para el método validarSiElJugadorYaFueElegido
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);

        //valores para verificar si el presupuesto alcanza
        when(equipoMock.getPresupuesto()).thenReturn(2000000D);
        when(jugadormock.getPrecio()).thenReturn(150000D);

//        Ejecución
        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1);

//        Verificación
        verify(repositorioEquipoJugadorMock, times(1)).guardarEquipoJugador(any(EquipoJugador.class));
    }

    @Test
    public void cuandoSeAgregaUnJugadorYaExistenteAlEquipoLanzaUnaExcepcion() {

        //preparación se necesita a equipoMock que está inicializado en el before
        Long idEquipo = 3L;
        Long idJugador = 4L;
        Jugador jugadormock = mock(Jugador.class);

        // método buscarEquipoPorId
        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
        // método buscarJugadorPorId
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadormock);
        // método de equipo y jugador asociados para el método validarSiElJugadorYaFueElegido

        EquipoJugador equipoJugadorMock = mock(EquipoJugador.class);
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(equipoJugadorMock);

        //valores para verificar si el presupuesto alcanza
        when(equipoMock.getPresupuesto()).thenReturn(850000D);
        when(jugadormock.getPrecio()).thenReturn(150000D);

/*       ejecución y Verificación

        assertThrows(UsuarioExistente.class, () -> this.servicioLogin.registrar(usuario));
        verify(this.repositorioUsuarioMock, times(0)).guardar(usuario);
*/
        assertThrows(elJugadorYaExisteEnElEquipoException.class, () -> servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1));
    }

    @Test
    public void cuandoSeAgregaUnJugadorAlEquipoYElPresupuestoNoAlcanzaLanzaUnaExcepcion() {

        //preparación se necesita a equipoMock que está inicializado en el before
        Long idEquipo = 3L;
        Long idJugador = 4L;
        Jugador jugadormock = mock(Jugador.class);

        // método buscarEquipoPorId
        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
        // método buscarJugadorPorId
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadormock);
        // método de equipo y jugador asociados para el método validarSiElJugadorYaFueElegido
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);

        //valores para verificar si el presupuesto alcanza
        when(equipoMock.getPresupuesto()).thenReturn(850000D);
        when(jugadormock.getPrecio()).thenReturn(850001D);

        //Ejecución y Verificación
        assertThrows(PresupuestoInsuficienteException.class, () -> servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1));
    }

    @Test
    public void cuandoSeAgregaUnJugadorAlEquipoQueValeMilElPresupuestoDelEquipoEs19000() throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException, PresupuestoInsuficienteException {

        Long idEquipo = 3L;

        Equipo equipoReal = new Equipo();
        equipoReal.setId(idEquipo);
        equipoReal.setPresupuesto(20000D);

        Long idJugador = 1L;

        Jugador jugadormock = mock(Jugador.class);
        when(jugadormock.getPrecio()).thenReturn(1000D);


        // método buscarEquipoPorId
        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoReal);

        // método buscarJugadorPorId
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadormock);


        // método de equipo y jugador asociados para el método validarSiElJugadorYaFueElegido
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);

        // SE AGREGAN - Ejecución
        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1); // 1000

        assertEquals(19000D, equipoReal.getPresupuesto());

    }

    @Test
    public void cuandoSeEliminaUnJugadorAlEquipoSeEliminaCorrectamenteYSeReintegraElPresupuesto() throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException, PresupuestoInsuficienteException {

        Long idEquipo = 3L;

        Equipo equipoReal = new Equipo();
        equipoReal.setId(idEquipo);
        equipoReal.setPresupuesto(20000D);

        Long idJugador = 1L;
        Long idJugador2 = 2L;
        Long idJugador3 = 3L;
        Long idJugador4 = 4L;
        Jugador jugadormock = mock(Jugador.class);
        when(jugadormock.getPrecio()).thenReturn(1000D);

        Jugador jugadormock2 = mock(Jugador.class);
        when(jugadormock2.getPrecio()).thenReturn(2000D);

        Jugador jugadormock3 = mock(Jugador.class);
        when(jugadormock3.getPrecio()).thenReturn(5000D);

        Jugador jugadormock4 = mock(Jugador.class);
        when(jugadormock4.getPrecio()).thenReturn(10000D);

        // método buscarEquipoPorId
        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoReal);

        // método buscarJugadorPorId
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadormock);
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador2)).thenReturn(jugadormock2);
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador3)).thenReturn(jugadormock3);
        when(repositorioJugadorMock.buscarJugadorPorId(idJugador4)).thenReturn(jugadormock4);

        // método de equipo y jugador asociados para el método validarSiElJugadorYaFueElegido
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador2)).thenReturn(null);
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador3)).thenReturn(null);
        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador4)).thenReturn(null);


        // SE AGREGAN - Ejecución
        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1); // 1000
        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador2, 2); // 2000
        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador3, 3); // 5000
        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador4, 4); //10000

        // 20.000 - 1000 - 2000 - 5000 - 10.000 =  2.000
        assertEquals(2000D, equipoReal.getPresupuesto());

        /*Crear una relación para poder eliminar al jugador */
        EquipoJugador relacionEquipoJugadorMock = mock(EquipoJugador.class);
        when(relacionEquipoJugadorMock.getJugador()).thenReturn(jugadormock3);

        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador3)).thenReturn(relacionEquipoJugadorMock);

        // Se elimina al jugador 3 que vale 5000 , el saldo tiene que ser 7000
        servicioEquipo.eliminarJugadorDelEquipo(idEquipo, idJugador3);

        assertEquals(7000D, equipoReal.getPresupuesto());

    }

    @Test
    public void alCrearUnEquipoCon12JugadoresSeObtieneResultadoExitoso() throws EquipoSinCompletarException {
        Long idEquipo = 1L;

        EquipoJugador jugador1 = mock(EquipoJugador.class);
        EquipoJugador jugador2 = mock(EquipoJugador.class);
        EquipoJugador jugador3 = mock(EquipoJugador.class);
        EquipoJugador jugador4 = mock(EquipoJugador.class);
        EquipoJugador jugador5 = mock(EquipoJugador.class);
        EquipoJugador jugador6 = mock(EquipoJugador.class);
        EquipoJugador jugador7 = mock(EquipoJugador.class);
        EquipoJugador jugador8 = mock(EquipoJugador.class);
        EquipoJugador jugador9 = mock(EquipoJugador.class);
        EquipoJugador jugador10 = mock(EquipoJugador.class);
        EquipoJugador jugador11 = mock(EquipoJugador.class);
        EquipoJugador jugador12 = mock(EquipoJugador.class);

        HashMap<Integer, EquipoJugador> jugadores = new HashMap<>();

        jugadores.put(1, jugador1);
        jugadores.put(2, jugador2);
        jugadores.put(3, jugador3);
        jugadores.put(4, jugador4);
        jugadores.put(5, jugador5);
        jugadores.put(6, jugador6);
        jugadores.put(7, jugador7);
        jugadores.put(8, jugador8);
        jugadores.put(9, jugador9);
        jugadores.put(10, jugador10);
        jugadores.put(11, jugador11);
        jugadores.put(12, jugador12);

        when(servicioEquipoJugadorMock.buscarJugadoresPorEquipoId(idEquipo)).thenReturn(jugadores);

        servicioEquipo.validarEquipoCompleto(idEquipo);

    }

    @Test
    public void alCrearUnEquipoCon5JugadoresLaValidacionTiraUnaExcepcion() {
        /*´Preparación*/
        Long idEquipo = 1L;

        EquipoJugador jugador1 = mock(EquipoJugador.class);
        EquipoJugador jugador2 = mock(EquipoJugador.class);
        EquipoJugador jugador3 = mock(EquipoJugador.class);
        EquipoJugador jugador4 = mock(EquipoJugador.class);
        EquipoJugador jugador5 = mock(EquipoJugador.class);

        HashMap<Integer, EquipoJugador> jugadores = new HashMap<>();

        jugadores.put(1, jugador1);
        jugadores.put(2, jugador2);
        jugadores.put(3, jugador3);
        jugadores.put(4, jugador4);
        jugadores.put(5, jugador5);

        when(servicioEquipoJugadorMock.buscarJugadoresPorEquipoId(idEquipo)).thenReturn(jugadores);

        /*Ejecución y validación */
        assertThrows(EquipoSinCompletarException.class, () -> servicioEquipo.validarEquipoCompleto(idEquipo));

    }

}
