package com.ista.usermsv.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty @NotNull
    @Column(nullable = false)
    private String nombre;

    @NotEmpty @Email @NotNull
    @Column(unique = true)
    private String email;

    @NotEmpty @NotNull
    @Column(nullable = false)
    private String password;

}
