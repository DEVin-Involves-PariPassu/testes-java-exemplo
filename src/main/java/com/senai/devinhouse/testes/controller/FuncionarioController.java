package com.senai.devinhouse.testes.controller;

import com.senai.devinhouse.testes.model.Funcionario;
import com.senai.devinhouse.testes.service.FuncionarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody Funcionario funcionario) {
        if(funcionario.getSobreNome() == null || "".equals(funcionario.getSobreNome())) {
            return new ResponseEntity("Informe o sobrenome", HttpStatus.BAD_REQUEST);
        }
        Funcionario funcionarioSalvo = funcionarioService.salvarFuncionario(funcionario);

        return new ResponseEntity<>(funcionarioSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Funcionario> buscarTodosFuncionarios() {
        return funcionarioService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable("id") Long id) {
        return funcionarioService.buscarFuncionarioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable("id") Long id,
                                                            @RequestBody Funcionario funcionario) {

        return funcionarioService.buscarFuncionarioPorId(id)
                .map(func -> {
                    func.setEmail(funcionario.getEmail());
                    func.setNome(funcionario.getNome());
                    func.setSobreNome(funcionario.getSobreNome());

                    Funcionario funcAtualizado = funcionarioService.atualizarFuncionario(func);
                    return new ResponseEntity<>(funcAtualizado, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarFuncionario(@PathVariable("id") Long id) {

        funcionarioService.deletarFuncionario(id);

        return new ResponseEntity<>("Funcionário deletado com sucesso", HttpStatus.OK);
    }


}
