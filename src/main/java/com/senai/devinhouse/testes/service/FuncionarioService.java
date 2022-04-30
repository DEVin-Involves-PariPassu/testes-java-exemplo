package com.senai.devinhouse.testes.service;

import com.senai.devinhouse.testes.model.Funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioService {

    Funcionario salvarFuncionario(Funcionario funcionario);
    List<Funcionario> buscarTodos();
    Optional<Funcionario> buscarFuncionarioPorId(Long id);
    Funcionario atualizarFuncionario(Funcionario funcionario);
    void deletarFuncionario(Long id);
}
