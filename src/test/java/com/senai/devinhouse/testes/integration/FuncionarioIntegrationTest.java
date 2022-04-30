package com.senai.devinhouse.testes.integration;

import com.senai.devinhouse.testes.model.Funcionario;
import com.senai.devinhouse.testes.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FuncionarioIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private String url = "http://localhost:";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @BeforeEach
    public void initDB() {
        funcionarioRepository.save(criarFuncionarioFake());
        funcionarioRepository.save(criarFuncionarioFake());
        funcionarioRepository.save(criarFuncionarioFake());
    }

    private Funcionario criarFuncionarioFake() {
        return Funcionario.builder()
                .nome("Teste " + new Random().nextInt(100))
                .sobreNome("Sobrenome")
                .email("email@email.com" + new Random().nextInt(100)).build();
    }

    @Test
    public void testarSalvarFuncionario() {

        Funcionario funcionario = Funcionario.builder()
                .nome("Danilo")
                .sobreNome("Sales")
                .email("danilo.cybernet@gmail.com").build();

        ResponseEntity<Funcionario> retornoPost =
                this.restTemplate.postForEntity(url + port + "/funcionarios",
                        funcionario, Funcionario.class);


        assertEquals(HttpStatus.CREATED, retornoPost.getStatusCode());
        assertNotNull(retornoPost.getBody().getId());
        assertEquals(LocalDate.now(), retornoPost.getBody().getDataCriacao());
    }

    @Test
    public void buscarTodos() {
        Funcionario funcionario = Funcionario.builder()
                .nome("Danilo")
                .sobreNome("Sales")
                .email("danilo.cybernet@gmail.com").build();

        this.restTemplate.postForEntity(url + port + "/funcionarios",
                        funcionario, Funcionario.class);

        ResponseEntity<Funcionario[]> retorno = this.restTemplate.getForEntity(url + port + "/funcionarios", Funcionario[].class);

        assertEquals(4, retorno.getBody().length);
        assertEquals(true,
                Arrays.asList(retorno.getBody()).stream()
                .filter(f -> f.getNome().equals("Danilo") )
                .findAny().isPresent());

    }

}
