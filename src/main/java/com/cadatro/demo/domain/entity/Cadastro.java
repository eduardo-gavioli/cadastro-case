package com.cadatro.demo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.UUID;

@Table(name = "cadastro")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cadastro {

    @GeneratedValue
    @Id
    private UUID id;

    private String nome;
    private String sobrenome;
    private int idade;
    private String pais;
}