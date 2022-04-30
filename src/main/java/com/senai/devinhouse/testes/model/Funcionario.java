package com.senai.devinhouse.testes.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "func_seq")
    @SequenceGenerator(name = "func_seq", sequenceName = "func_seq")
    private Long id;

    private String nome;

    private String sobreNome;

    private String email;

    private LocalDate dataCriacao;

}
