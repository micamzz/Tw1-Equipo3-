package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioMercadoTest {
    @Test
    public void queBuscarPorNombreDevuelvaJugadoresQueCoincidan() {
        RepositorioJugador repositorioMock= mock(RepositorioJugador.class);

        Jugador jugador = new Jugador();
        jugador.setNombre("Stephen Curry");
        when(repositorioMock.buscarJugadores(null,"Curry")).thenReturn(Arrays.asList(jugador));

        ServicioMercado servicio = new ServicioMercadoImpl(repositorioMock);

        List<Jugador> resultado = servicio.obtenerJugadores(null, "Curry");
        assertThat(resultado, not(empty()));
        assertThat(resultado.get(0).getNombre(),equalTo("Stephen Curry"));

    }
}
