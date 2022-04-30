package com.senai.devinhouse.testes.service;

import com.senai.devinhouse.testes.model.Funcionario;
import com.senai.devinhouse.testes.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl  implements FuncionarioService{

    private FuncionarioRepository funcionarioRepository;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public Funcionario salvarFuncionario(Funcionario funcionario) {
        Optional<Funcionario> funcionarioSalvo = funcionarioRepository.findByEmail(funcionario.getEmail());

        if(funcionarioSalvo.isPresent()) {
            throw new IllegalArgumentException("Funcionario já existe com este e-mail");
        }
        funcionario.setDataCriacao(LocalDate.now());
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.findAll();
    }

    @Override
    public Optional<Funcionario> buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    public Funcionario atualizarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public void deletarFuncionario(Long id) {
        funcionarioRepository.deleteById(id);
    }
}
