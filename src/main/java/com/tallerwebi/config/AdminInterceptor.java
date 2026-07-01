package com.tallerwebi.config;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.enums.RolUsuario;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        if (usuario.getRol() != RolUsuario.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/home");
            return false;
        }

        return true;
    }
}
