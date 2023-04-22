package com.ista.usermsv.service;

import com.ista.usermsv.model.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);

    Optional<Usuario> porEmail(String email);

}
