package com.senai.devinhouse.testes.controller;

import com.senai.devinhouse.testes.model.Funcionario;
import com.senai.devinhouse.testes.service.FuncionarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class FuncionarioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @Test
    public void givenFuncionarioObject_whenCriarFuncionario_thenReturnFuncionarioSalvo() throws Exception {
        var funcionarioReturned = Funcionario.builder()
                .id(1L)
                .email("danilo.cybernet@gmail.com")
                .nome("Danilo")
                .sobreNome("Sales")
                .dataCriacao(LocalDate.of(2022, 04, 29))
                .build();

        when(funcionarioService.salvarFuncionario(any())).thenReturn(funcionarioReturned);

        var funcionarioJson = """
                  {
                    "nome": "Danilo",
                    "sobreNome": "Sales",
                    "email": "danilo.cybernet@gmail.com"
                  }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/funcionarios/1")
                        .header("Content-Type", "application/json")
                        .content(funcionarioJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.dataCriacao").value("2022-04-29"));

    }

    @Test
    public void givenFuncionarioWithoutSobrenome_whenCreateFuncionario_thenReturnsStatus400() throws Exception {

        var funcionarioJson = """
                  {
                    "nome": "Danilo",
                    "email": "danilo.cybernet@gmail.com"
                  }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/funcionarios")
                        .header("Content-Type", "application/json")
                        .content(funcionarioJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Informe o sobrenome"));
    }

    @Test
    public void givenFuncionarioObject_whenAtualizarFuncionario_thenReturnFuncionarioAtualizado() throws Exception {
        var funcionarioReturned = Funcionario.builder()
                .id(1L)
                .email("danilo.cybernet@gmail.com")
                .nome("Danilo")
                .sobreNome("Sales")
                .dataCriacao(LocalDate.of(2022, 04, 29))
                .build();

        var funcionarioAlterado = Funcionario.builder()
                .id(1L)
                .email("camila.amaral@gmail.com")
                .nome("Camila")
                .sobreNome("Amaral")
                .dataCriacao(LocalDate.of(2022, 04, 29))
                .build();

        when(funcionarioService.buscarFuncionarioPorId(1L)).thenReturn(Optional.of(funcionarioReturned));
        when(funcionarioService.atualizarFuncionario(any())).thenReturn(funcionarioAlterado);

        var funcionarioJson = """
                  {
                    "nome": "Camila",
                    "sobreNome": "Amaral",
                    "email": "camila.amaral@gmail.com"
                  }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/funcionarios/1")
                        .header("Content-Type", "application/json")
                        .content(funcionarioJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Camila"))
                .andExpect(jsonPath("$.sobreNome").value("Amaral"));

    }
}
