package com.tallerwebi.dominio.login;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.usuario.Usuario;

public interface ServicioLogin {
    Usuario consultarUsuario(String email, String password);

    void registrar(Usuario usuario) throws UsuarioExistente;

    /*Para que el administrador pueda asignar otros admin */
    void registrarAdmin(Usuario usuario) throws UsuarioExistente;
}
