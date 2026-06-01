package com.tallerwebi.dominio;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;


public class ServicioTorneoImplTest {

    private ServicioTorneo servicioTorneo;
    private RepositorioTorneo repositorioTorneoMock;

    @BeforeEach
    public void init() {
        this.repositorioTorneoMock = mock(RepositorioTorneo.class);
        this.servicioTorneo = new ServicioTorneoImpl(this.repositorioTorneoMock);
    }

    @Test
    public void siNoExisteTorneoActivoSePuedeCrearNuevo() {


    }



}
