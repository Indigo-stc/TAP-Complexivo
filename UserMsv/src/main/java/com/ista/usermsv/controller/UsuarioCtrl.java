package com.ista.usermsv.controller;

import com.ista.usermsv.model.entity.Usuario;
import com.ista.usermsv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsuarioCtrl {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Usuario> usuario = service.porId(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        if (service.porEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Ya existe el correo electronico"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        if (service.porEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Ya existe el correo electronico"));
        }
        Optional<Usuario> optional = service.porId(id);
        if (optional.isPresent()) {
            Usuario current = optional.get();
            current.setEmail(usuario.getEmail());
            current.setNombre(usuario.getNombre());
            current.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(current));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> optional = service.porId(id);
        if (optional.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
