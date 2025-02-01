package com.fabada.librayapi.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private  String login;

    @Column(nullable = false)
    private  String senha;

    @Type(ListArrayType.class)
    @Column(name = "roles", nullable = false, columnDefinition = "varchar[]")
    private List<String> roles;
}
