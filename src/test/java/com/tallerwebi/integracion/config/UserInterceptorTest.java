package com.tallerwebi.integracion.config;

import com.tallerwebi.config.UserInterceptor;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.RolUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserInterceptorTest {

    private UserInterceptor interceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    public void inicializacion() {
        interceptor = new UserInterceptor();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/spring");
    }

    @Test
    public void siNoHayUsuarioEnSesionDebeRedirigirAlLogin() throws Exception {

        when(session.getAttribute("usuario")).thenReturn(null);

        boolean resultado = interceptor.preHandle(request, response, new Object());

        assertFalse(resultado);
        verify(response).sendRedirect("/spring/login");
    }

    @Test
    public void siElUsuarioEsUserDebePermitirElAcceso() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setRol(RolUsuario.USER);

        when(session.getAttribute("usuario")).thenReturn(usuario);

        boolean resultado = interceptor.preHandle(request, response, new Object());

        assertTrue(resultado);
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    public void siElUsuarioEsAdminDebeRedirigirAlHomeDelAdministrador() throws Exception {

        Usuario admin = new Usuario();
        admin.setRol(RolUsuario.ADMIN);

        when(session.getAttribute("usuario")).thenReturn(admin);

        boolean resultado = interceptor.preHandle(request, response, new Object());

        assertFalse(resultado);
        verify(response).sendRedirect("/spring/admin/home");
    }
}