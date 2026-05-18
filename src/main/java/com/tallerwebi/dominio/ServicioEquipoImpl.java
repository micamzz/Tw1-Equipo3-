package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EquipoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioEquipoImpl implements ServicioEquipo {

    private RepositorioEquipo repositorioEquipo;

    @Autowired
    public ServicioEquipoImpl(RepositorioEquipo repositorioEquipo) {
        this.repositorioEquipo = repositorioEquipo;
    }


    @Override
    public Equipo guardarEquipo(Equipo equipo) {
        repositorioEquipo.guardarEquipo(equipo);
        return equipo;
        /*
        Devuelve Equipo porq necesito recuperar el ID en el controlador
         */
    }

    @Override
    public Equipo buscarEquipoPorId(Long id) throws EquipoNoEncontradoException {

        Equipo equipo = repositorioEquipo.buscarEquipoPorId(id);

        if (equipo == null) {
            throw new EquipoNoEncontradoException("No se encuentra el equipo asociado a ese id");
        }
        return equipo;

    }


    @Override
    public Equipo buscarEquipoPorNombre(String nombre) throws EquipoNoEncontradoException {

        Equipo equipo = repositorioEquipo.buscarEquipoPorNombre(nombre);

        if (equipo == null) {
            throw new EquipoNoEncontradoException("No se encuentra el equipo con ese nombre");
        }

        return equipo;

    }

}




