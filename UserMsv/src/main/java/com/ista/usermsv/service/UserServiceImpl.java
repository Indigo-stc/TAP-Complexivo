package com.ista.usermsv.service;

import com.ista.usermsv.model.entity.Usuario;
import com.ista.usermsv.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsuarioRepo repo;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return repo.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porEmail(String email) {
        return repo.findByEmail(email);
    }
}
