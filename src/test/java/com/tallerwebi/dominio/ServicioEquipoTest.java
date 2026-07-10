//package com.tallerwebi.dominio;
//
//import com.tallerwebi.dominio.enums.PosicionJugadorEquipo;
//import com.tallerwebi.dominio.equipo.Equipo;
//import com.tallerwebi.dominio.equipo.RepositorioEquipo;
//import com.tallerwebi.dominio.equipo.ServicioEquipo;
//import com.tallerwebi.dominio.equipo.ServicioEquipoImpl;
//import com.tallerwebi.dominio.equipoJugador.EquipoJugador;
//import com.tallerwebi.dominio.equipoJugador.RepositorioEquipoJugador;
//import com.tallerwebi.dominio.excepcion.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class ServicioEquipoTest {
//
//    private ServicioEquipo servicioEquipo;
//    private RepositorioEquipo repositorioEquipoMock;
//    private RepositorioJugador repositorioJugadorMock;
//    private RepositorioEquipoJugador repositorioEquipoJugadorMock;
//    private RepositorioTorneo repositorioTorneoMock;
//    private ServicioPartidoNBA servicioPartidoNBAMock;
//    private Equipo equipoMock;
//
//    @BeforeEach
//    public void inicializacion() {
//        repositorioEquipoMock = mock(RepositorioEquipo.class);
//        repositorioJugadorMock = mock(RepositorioJugador.class);
//        repositorioEquipoJugadorMock = mock(RepositorioEquipoJugador.class);
//        repositorioTorneoMock = mock(RepositorioTorneo.class);
//        servicioPartidoNBAMock = mock(ServicioPartidoNBA.class);
//        equipoMock = mock(Equipo.class);
//
//        servicioEquipo = new ServicioEquipoImpl(repositorioEquipoMock, repositorioJugadorMock, repositorioEquipoJugadorMock, repositorioTorneoMock, servicioPartidoNBAMock
//        );
//
//        when(servicioPartidoNBAMock.obtenerPartidosActivos()).thenReturn(List.of());
//        when(servicioPartidoNBAMock.obtenerPartidosProgramados()).thenReturn(List.of());
//    }
//
//    @Test
//    public void alBuscarUnEquipoPorIdDevuelveElEquipoCorrecto() throws EquipoNoEncontradoException {
//        /* Preparación */
//        when(repositorioEquipoMock.buscarEquipoPorId(1L)).thenReturn(equipoMock);
//
//        /* Ejecución */
//        Equipo equipoEncontrado = servicioEquipo.buscarEquipoPorId(1L);
//
//        /* Verificación */
//        assertEquals(equipoMock, equipoEncontrado);
//    }
//
//    @Test
//    public void alBuscarUnEquipoPorIdDevuelveUnaExcepcion() {
//        /* Preparación */
//        Long idNoRegistrado = 3L;
//        when(repositorioEquipoMock.buscarEquipoPorId(idNoRegistrado)).thenReturn(null);
//
//        /* Ejecución - Verificación */
//        assertThrows(EquipoNoEncontradoException.class, () -> servicioEquipo.buscarEquipoPorId(idNoRegistrado));
//    }
//
//    @Test
//    public void alBuscarUnEquipoPorNombreDevuelveElEquipoCorrecto() throws EquipoNoEncontradoException {
//        /* Preparación */
//        when(repositorioEquipoMock.buscarEquipoPorNombre("NBA")).thenReturn(equipoMock);
//
//        /* Ejecución */
//        Equipo equipoEncontrado = servicioEquipo.buscarEquipoPorNombre("NBA");
//
//        /* Verificación */
//        assertEquals(equipoMock, equipoEncontrado);
//    }
//
//    @Test
//    public void alBuscarUnEquipoPorNombreIncorrectoDevuelveUnaExcepcion() {
//        /* Preparación */
//        String nombreNoEncontrado = "ParenLaMano";
//        when(repositorioEquipoMock.buscarEquipoPorNombre(nombreNoEncontrado)).thenReturn(null);
//
//        /* Ejecución - Verificación */
//        assertThrows(EquipoNoEncontradoException.class, () -> servicioEquipo.buscarEquipoPorNombre(nombreNoEncontrado));
//    }
//
//    @Test
//    public void alGuardarUnEquipoSeAsignaPresupuestoYTorneo() throws TorneoVirtualActualNoEncontradoException {
//        /* Preparación */
//        Equipo equipo = new Equipo();
//        Torneo torneoMock = mock(Torneo.class);
//        when(repositorioTorneoMock.buscarTorneoActual(TipoTorneo.VIRTUAL)).thenReturn(torneoMock);
//
//        /* Ejecución */
//        Equipo resultado = servicioEquipo.guardarEquipo(equipo);
//
//        /* Verificación */
//        assertEquals(2000000D, resultado.getPresupuesto());
//        assertEquals(torneoMock, resultado.getTorneo());
//        verify(repositorioEquipoMock, times(1)).guardarEquipo(equipo);
//    }
//
//    @Test
//    public void alGuardarUnEquipoSinTorneoLanzaExcepcion() {
//        /* Preparación */
//        Equipo equipo = new Equipo();
//        when(repositorioTorneoMock.buscarTorneoActual(TipoTorneo.VIRTUAL)).thenReturn(null);
//
//        /* Ejecución - Verificación */
//        assertThrows(TorneoVirtualActualNoEncontradoException.class, () -> servicioEquipo.guardarEquipo(equipo));
//    }
//
//    @Test
//    public void cuandoSeAgregaUnJugadorAlEquipoSeAgregaCorrectamente() throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException, PresupuestoInsuficienteException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException {
//        /* Preparación */
//        Long idEquipo = 3L;
//        Long idJugador = 4L;
//        Jugador jugadorMock = mock(Jugador.class);
//
//        // método buscarEquipoPorId
//        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
//        // método buscarJugadorPorId
//        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadorMock);
//        // método de equipo y jugador asociados para el método validarSiElJugadorYaFueElegido
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);
//
//        // valores para verificar si el presupuesto alcanza
//        when(equipoMock.getPresupuesto()).thenReturn(2000000D);
//        when(jugadorMock.getPrecio()).thenReturn(150000D);
//
//        /* Ejecución */
//        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1);
//
//        /* Verificación */
//        verify(repositorioEquipoJugadorMock, times(1)).guardarEquipoJugador(any(EquipoJugador.class));
//    }
//
//    @Test
//    public void cuandoSeAgregaUnJugadorYaExistenteAlEquipoLanzaUnaExcepcion() {
//        /* Preparación */
//        Long idEquipo = 3L;
//        Long idJugador = 4L;
//        Jugador jugadorMock = mock(Jugador.class);
//
//        // método buscarEquipoPorId
//        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
//        // método buscarJugadorPorId
//        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadorMock);
//        // método de equipo y jugador asociados para el método validarSiElJugadorYaFueElegido
//        EquipoJugador equipoJugadorMock = mock(EquipoJugador.class);
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(equipoJugadorMock);
//
//        // valores para verificar si el presupuesto alcanza
//        when(equipoMock.getPresupuesto()).thenReturn(850000D);
//        when(jugadorMock.getPrecio()).thenReturn(150000D);
//
//        /* Ejecución - Verificación */
//        assertThrows(elJugadorYaExisteEnElEquipoException.class, () -> servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1));
//    }
//
//    @Test
//    public void cuandoSeAgregaUnJugadorAlEquipoSeActualizaElPresupuestoCorrectamente() throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException, PresupuestoInsuficienteException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException {
//        /* Preparación */
//        Long idEquipo = 3L;
//        Equipo equipoReal = new Equipo();
//        equipoReal.setId(idEquipo);
//        equipoReal.setPresupuesto(20000D);
//
//        Long idJugador = 1L;
//        Jugador jugadorMock = mock(Jugador.class);
//        when(jugadorMock.getPrecio()).thenReturn(5000D);
//
//        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoReal);
//        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadorMock);
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);
//
//        /* Ejecución */
//        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1);
//
//        /* Verificación */
//        assertEquals(15000D, equipoReal.getPresupuesto());
//    }
//
//    @Test
//    public void cuandoSeAgregaUnJugadorAlEquipoYElPresupuestoNoAlcanzaLanzaUnaExcepcion() {
//        /* Preparación */
//        Long idEquipo = 3L;
//        Long idJugador = 4L;
//        Jugador jugadorMock = mock(Jugador.class);
//
//        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoMock);
//        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadorMock);
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);
//
//        // valores para verificar si el presupuesto alcanza
//        when(equipoMock.getPresupuesto()).thenReturn(850000D);
//        when(jugadorMock.getPrecio()).thenReturn(850001D);
//
//        /* Ejecución - Verificación */
//        assertThrows(PresupuestoInsuficienteException.class, () -> servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1));
//    }
//
//    @Test
//    public void cuandoSeEliminaUnJugadorAlEquipoSeEliminaCorrectamenteYSeReintegraElPresupuesto() throws EquipoNoEncontradoException, elJugadorYaExisteEnElEquipoException, PresupuestoInsuficienteException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException {
//        /* Preparación */
//        Long idEquipo = 3L;
//
//        Equipo equipoReal = new Equipo();
//        equipoReal.setId(idEquipo);
//        equipoReal.setPresupuesto(20000D);
//
//        Long idJugador = 1L;
//        Long idJugador2 = 2L;
//        Long idJugador3 = 3L;
//        Long idJugador4 = 4L;
//        Jugador jugadorMock = mock(Jugador.class);
//        when(jugadorMock.getPrecio()).thenReturn(1000D);
//
//        Jugador jugadorMock2 = mock(Jugador.class);
//        when(jugadorMock2.getPrecio()).thenReturn(2000D);
//
//        Jugador jugadorMock3 = mock(Jugador.class);
//        when(jugadorMock3.getPrecio()).thenReturn(5000D);
//
//        Jugador jugadorMock4 = mock(Jugador.class);
//        when(jugadorMock4.getPrecio()).thenReturn(10000D);
//
//        // método buscarEquipoPorId
//        when(repositorioEquipoMock.buscarEquipoPorId(idEquipo)).thenReturn(equipoReal);
//
//        // método buscarJugadorPorId
//        when(repositorioJugadorMock.buscarJugadorPorId(idJugador)).thenReturn(jugadorMock);
//        when(repositorioJugadorMock.buscarJugadorPorId(idJugador2)).thenReturn(jugadorMock2);
//        when(repositorioJugadorMock.buscarJugadorPorId(idJugador3)).thenReturn(jugadorMock3);
//        when(repositorioJugadorMock.buscarJugadorPorId(idJugador4)).thenReturn(jugadorMock4);
//
//        // método de equipo y jugador asociados para el método validarSiElJugadorYaFueElegido
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(null);
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador2)).thenReturn(null);
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador3)).thenReturn(null);
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador4)).thenReturn(null);
//
//        /* Ejecución */
//        // SE AGREGAN
//        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador, 1); // 1000
//        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador2, 2); // 2000
//        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador3, 3); // 5000
//        servicioEquipo.agregarJugadorAlEquipo(idEquipo, idJugador4, 4); // 10000
//
//        /* Verificación parcial */
//        // 20.000 - 1000 - 2000 - 5000 - 10.000 =  2.000
//        assertEquals(2000D, equipoReal.getPresupuesto());
//
//        /* Preparación de la eliminación */
//        // Crear una relación para poder eliminar al jugador
//        EquipoJugador relacionEquipoJugadorMock = mock(EquipoJugador.class);
//        when(relacionEquipoJugadorMock.getJugador()).thenReturn(jugadorMock3);
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador3)).thenReturn(relacionEquipoJugadorMock);
//
//        /* Ejecución de la eliminación */
//        // Se elimina al jugador 3 que vale 5000, el saldo tiene que ser 7000
//        servicioEquipo.eliminarJugadorDelEquipo(idEquipo, idJugador3);
//
//        /* Verificación */
//        assertEquals(7000D, equipoReal.getPresupuesto());
//    }
//
//
//    @Test
//    public void validarEquipoCompletoConEquipoCompletoNoLanzaExcepcion() throws EquipoSinCompletarException {
//        /* Preparación */
//        Long idEquipo = 1L;
//
//        List<EquipoJugador> jugadores = new ArrayList<>();
//
//        for (int i = 1; i <= 10; i++) {
//            EquipoJugador equipoJugador = new EquipoJugador();
//
//            if (i == 1) {
//                equipoJugador.setPosicionDelJugador(PosicionJugadorEquipo.CAPITAN);
//            } else if (i == 2) {
//                equipoJugador.setPosicionDelJugador(PosicionJugadorEquipo.SEXTO_HOMBRE);
//            } else {
//                equipoJugador.setPosicionDelJugador(PosicionJugadorEquipo.TITULAR);
//            }
//
//            jugadores.add(equipoJugador);
//        }
//
//        when(repositorioEquipoJugadorMock.buscarPorEquipoId(idEquipo)).thenReturn(jugadores);
//
//        /* Ejecución - Verificación */
//        servicioEquipo.validarEquipoCompleto(idEquipo);
//    }
//
//    @Test
//    public void validarEquipoCompletoConMenosDeDiezJugadoresLanzaExcepcion() {
//        /* Preparación */
//        Long idEquipo = 1L;
//
//        List<EquipoJugador> jugadores = new ArrayList<>();
//
//        for (int i = 1; i <= 9; i++) {
//            jugadores.add(new EquipoJugador());
//        }
//
//        when(repositorioEquipoJugadorMock.buscarPorEquipoId(idEquipo)).thenReturn(jugadores);
//
//        /* Ejecución - Verificación */
//        assertThrows(EquipoSinCompletarException.class, () -> servicioEquipo.validarEquipoCompleto(idEquipo)
//        );
//    }
//
//    @Test
//    public void asignarRolEspecialCapitanActualizaElJugador() throws EquipoNoEncontradoException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException {
//        /* Preparación */
//        Long idEquipo = 1L;
//        Long idJugador = 2L;
//
//        EquipoJugador equipoJugador = new EquipoJugador();
//        equipoJugador.setNumeroOrden(1);
//        equipoJugador.setPosicionDelJugador(PosicionJugadorEquipo.TITULAR);
//
//        when(repositorioEquipoJugadorMock.buscarPorEquipoId(idEquipo)).thenReturn(new ArrayList<>());
//
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(equipoJugador);
//
//        /* Ejecución */
//        servicioEquipo.asignarRolEspecial(idEquipo, idJugador, PosicionJugadorEquipo.CAPITAN);
//
//        /* Verificación */
//        assertThat(equipoJugador.getPosicionDelJugador(), equalTo(PosicionJugadorEquipo.CAPITAN));
//
//        verify(repositorioEquipoJugadorMock).actualizarEquipoJugador(equipoJugador);
//    }
//
//    @Test
//    public void asignarRolEspecialSextoHombreActualizaElJugador() throws EquipoNoEncontradoException, NoSePuedeModificarEquipoSiHayPartidosEnCursoException {
//        /* Preparación */
//        Long idEquipo = 1L;
//        Long idJugador = 2L;
//
//        EquipoJugador equipoJugador = new EquipoJugador();
//        equipoJugador.setNumeroOrden(6);
//        equipoJugador.setPosicionDelJugador(PosicionJugadorEquipo.SUPLENTE);
//
//        when(repositorioEquipoJugadorMock.buscarPorEquipoId(idEquipo)).thenReturn(new ArrayList<>());
//
//        when(repositorioEquipoJugadorMock.buscarEquipoYJugadorAsociado(idEquipo, idJugador)).thenReturn(equipoJugador);
//
//        /* Ejecución */
//        servicioEquipo.asignarRolEspecial(idEquipo, idJugador, PosicionJugadorEquipo.SEXTO_HOMBRE);
//
//        /* Verificación */
//        assertThat(equipoJugador.getPosicionDelJugador(), equalTo(PosicionJugadorEquipo.SEXTO_HOMBRE));
//
//        verify(repositorioEquipoJugadorMock).actualizarEquipoJugador(equipoJugador);
//    }
//
//    @Test
//    public void calcularPuntajeTotalDeEquipoSinJugadoresDevuelveCero() {
//        /* Preparación */
//        Long equipoId = 1L;
//        when(repositorioEquipoJugadorMock.buscarPorEquipoId(equipoId)).thenReturn(List.of());
//
//        /* Ejecución */
//        Double total = servicioEquipo.calcularPuntajeTotalDelEquipo(equipoId);
//
//        /* Verificación */
//        assertEquals(0.0, total);
//    }
//
//    @Test
//    public void calcularPuntajeTotalDeEquipoConJugadoresAplicaMultiplicadorDeRol() {
//        /* Preparación */
//        Long equipoId = 1L;
//
//        RendimientoJugador rend = new RendimientoJugador();
//        rend.setPuntos(10);
//        rend.setRebotes(5);
//        rend.setAsistencias(4);
//        rend.setRobos(2);
//        rend.setBloqueos(1);
//        rend.setPerdidas(3);
//        // base = 10 + 1.2*5 + 1.5*4 + 3*2 + 3*1 - 2*3 = 10 + 6 + 6 + 6 + 3 - 6 = 25
//
//        Jugador jugador = new Jugador();
//        jugador.setId(1L);
//
//        EquipoJugador ej = mock(EquipoJugador.class);
//        when(ej.getJugador()).thenReturn(jugador);
//        when(ej.getPosicionDelJugador()).thenReturn(PosicionJugadorEquipo.CAPITAN); // x2
//
//        Equipo mockEquipo = mock(Equipo.class);
//        Torneo mockTorneo = mock(Torneo.class);
//        when(mockEquipo.getTorneo()).thenReturn(mockTorneo);
//        when(mockTorneo.getId()).thenReturn(1L);
//        when(ej.getEquipo()).thenReturn(mockEquipo);
//
//        when(repositorioEquipoJugadorMock.buscarPorEquipoId(equipoId)).thenReturn(List.of(ej));
//        when(repositorioJugadorMock.buscarRendimientoPorJugadorYTorneo(1L, 1L)).thenReturn(rend);
//
//        /* Ejecución */
//        Double total = servicioEquipo.calcularPuntajeTotalDelEquipo(equipoId);
//
//        /* Verificación */
//        assertEquals(50.0, total); // 25 * 2
//    }
//
//    @Test
//    public void calcularPuntajeTotalDeEquipoConJugadorSinRendimientoNoAportaPuntos() {
//        /* Preparación */
//        Long equipoId = 1L;
//
//        Jugador jugador = new Jugador();
//        jugador.setId(1L);
//
//        EquipoJugador ej = mock(EquipoJugador.class);
//        when(ej.getJugador()).thenReturn(jugador);
//        when(ej.getPosicionDelJugador()).thenReturn(PosicionJugadorEquipo.TITULAR);
//
//        Equipo mockEquipo = mock(Equipo.class);
//        Torneo mockTorneo = mock(Torneo.class);
//        when(mockEquipo.getTorneo()).thenReturn(mockTorneo);
//        when(mockTorneo.getId()).thenReturn(1L);
//        when(ej.getEquipo()).thenReturn(mockEquipo);
//
//        when(repositorioEquipoJugadorMock.buscarPorEquipoId(equipoId)).thenReturn(List.of(ej));
//        when(repositorioJugadorMock.buscarRendimientoPorJugadorYTorneo(1L, 1L)).thenReturn(null);
//
//        /* Ejecución */
//        Double total = servicioEquipo.calcularPuntajeTotalDelEquipo(equipoId);
//
//        /* Verificación */
//        assertEquals(0.0, total);
//    }
//
//    @Test
//    public void calcularPuntajeTotalDeEquipoAplicaMultiplicadoresCorrectosPorRol() {
//        /* Preparación */
//        Long equipoId = 1L;
//
//        RendimientoJugador rend = new RendimientoJugador();
//        rend.setPuntos(10);
//        rend.setRebotes(0);
//        rend.setAsistencias(0);
//        rend.setRobos(0);
//        rend.setBloqueos(0);
//        rend.setPerdidas(0);
//        // base = 10
//
//        Jugador jug1 = new Jugador();
//        jug1.setId(1L);
//        Jugador jug2 = new Jugador();
//        jug2.setId(2L);
//        Jugador jug3 = new Jugador();
//        jug3.setId(3L);
//        Jugador jug4 = new Jugador();
//        jug4.setId(4L);
//
//        EquipoJugador titular = mock(EquipoJugador.class);
//        when(titular.getJugador()).thenReturn(jug1);
//        when(titular.getPosicionDelJugador()).thenReturn(PosicionJugadorEquipo.TITULAR);
//
//        EquipoJugador capitan = mock(EquipoJugador.class);
//        when(capitan.getJugador()).thenReturn(jug2);
//        when(capitan.getPosicionDelJugador()).thenReturn(PosicionJugadorEquipo.CAPITAN);
//
//        EquipoJugador sexto = mock(EquipoJugador.class);
//        when(sexto.getJugador()).thenReturn(jug3);
//        when(sexto.getPosicionDelJugador()).thenReturn(PosicionJugadorEquipo.SEXTO_HOMBRE);
//
//        EquipoJugador suplente = mock(EquipoJugador.class);
//        when(suplente.getJugador()).thenReturn(jug4);
//        when(suplente.getPosicionDelJugador()).thenReturn(PosicionJugadorEquipo.SUPLENTE);
//
//        Equipo mockEquipo = mock(Equipo.class);
//        Torneo mockTorneo = mock(Torneo.class);
//        when(mockEquipo.getTorneo()).thenReturn(mockTorneo);
//        when(mockTorneo.getId()).thenReturn(1L);
//        when(titular.getEquipo()).thenReturn(mockEquipo);
//
//        when(repositorioEquipoJugadorMock.buscarPorEquipoId(equipoId))
//                .thenReturn(List.of(titular, capitan, sexto, suplente));
//        when(repositorioJugadorMock.buscarRendimientoPorJugadorYTorneo(anyLong(), anyLong())).thenReturn(rend);
//
//        /* Ejecución */
//        Double total = servicioEquipo.calcularPuntajeTotalDelEquipo(equipoId);
//
//        /* Verificación */
//        assertEquals(10 * 1.0 + 10 * 2.0 + 10 * 0.8 + 10 * 0.5, total);
//    }
//
//    @Test
//    public void cuandoHayPartidosActivosNoPermiteModificarEquipo() {
//        PartidoNBA partidoActivo = mock(PartidoNBA.class);
//
//        when(servicioPartidoNBAMock.obtenerPartidosActivos()).thenReturn(List.of(partidoActivo));
//
//        assertThrows(NoSePuedeModificarEquipoSiHayPartidosEnCursoException.class, () -> servicioEquipo.validarQueSePuedaModificarEquipo());
//    }
//
//    @Test
//    public void cuandoHayUnPartidoProgramadoDentroDeLaProximaHoraNoPermiteModificarEquipo() {
//        PartidoNBA partidoProgramado = mock(PartidoNBA.class);
//
//        when(servicioPartidoNBAMock.obtenerPartidosActivos()).thenReturn(List.of());
//        when(servicioPartidoNBAMock.obtenerPartidosProgramados()).thenReturn(List.of(partidoProgramado));
//        when(partidoProgramado.getHoraInicio()).thenReturn(LocalDateTime.now().plusMinutes(30));
//
//        assertThrows(NoSePuedeModificarEquipoSiHayPartidosEnCursoException.class, () -> servicioEquipo.validarQueSePuedaModificarEquipo());
//    }
//
//    @Test
//    public void cuandoNoHayPartidosActivosNiProgramadosDentroDeLaProximaHoraPuedeModificarEquipo() throws NoSePuedeModificarEquipoSiHayPartidosEnCursoException {
//        PartidoNBA partidoProgramado = mock(PartidoNBA.class);
//
//        when(servicioPartidoNBAMock.obtenerPartidosActivos()).thenReturn(List.of());
//        when(servicioPartidoNBAMock.obtenerPartidosProgramados()).thenReturn(List.of(partidoProgramado));
//        when(partidoProgramado.getHoraInicio()).thenReturn(LocalDateTime.now().plusHours(2));
//
//        servicioEquipo.validarQueSePuedaModificarEquipo();
//    }
//
//    @Test
//    public void puedeModificarEquipoDevuelveFalseCuandoHayPartidosActivos() {
//        PartidoNBA partidoActivo = mock(PartidoNBA.class);
//
//        when(servicioPartidoNBAMock.obtenerPartidosActivos()).thenReturn(List.of(partidoActivo));
//
//        Boolean resultado = servicioEquipo.puedeModificarEquipo();
//
//        assertFalse(resultado);
//    }
//
//    @Test
//    public void puedeModificarEquipoDevuelveTrueCuandoNoHayPartidosActivosNiProgramadosDentroDeLaProximaHora() {
//        when(servicioPartidoNBAMock.obtenerPartidosActivos()).thenReturn(List.of());
//        when(servicioPartidoNBAMock.obtenerPartidosProgramados()).thenReturn(List.of());
//
//        Boolean resultado = servicioEquipo.puedeModificarEquipo();
//
//        assertTrue(resultado);
//    }
//
//
//}
