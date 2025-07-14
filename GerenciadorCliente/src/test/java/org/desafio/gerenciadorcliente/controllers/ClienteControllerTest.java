package org.desafio.gerenciadorcliente.controllers;

import org.desafio.gerenciadorcliente.DTO.ClienteDTO;
import org.desafio.gerenciadorcliente.exception.ClienteNaoEncontradoException;
import org.desafio.gerenciadorcliente.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private final ClienteDTO clienteDTO = new ClienteDTO(
            1, "João Silva", "12345678901",
            LocalDate.of(1990, 1, 1), "ATIVO", 1000.0
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void criarCliente_DeveRetornarStatus201() throws Exception {
        when(clienteService.criarCliente(any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "nome": "João Silva",
                        "cpf": "12345678901",
                        "dataNascimento": "1990-01-01",
                        "limiteCredito": 1000.0
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornarCliente() throws Exception {
        when(clienteService.buscarClientePorId(1)).thenReturn(clienteDTO);

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }

    @Test
    void listarTodos_DeveRetornarListaDeClientes() throws Exception {
        when(clienteService.listarClientes()).thenReturn(Collections.singletonList(clienteDTO));

        mockMvc.perform(get("/clientes/listar-todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].statusBloqueio").value("ATIVO"));
    }

    @Test
    void atualizarCliente_DeveRetornarClienteAtualizado() throws Exception {
        when(clienteService.atualizarCliente(anyInt(), any(ClienteDTO.class))).thenReturn(clienteDTO);

        mockMvc.perform(put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "nome": "João Silva Atualizado",
                        "cpf": "12345678901",
                        "statusBloqueio": "ATIVO",
                        "limiteCredito": 1500.0
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    void listarBloqueados_DeveRetornarClientesBloqueados() throws Exception {
        ClienteDTO bloqueado = new ClienteDTO(
                2, "Maria Souza", "98765432100",
                LocalDate.of(1985, 5, 15), "BLOQUEADO", 0.0
        );

        when(clienteService.listarBloqueados()).thenReturn(Collections.singletonList(bloqueado));

        mockMvc.perform(get("/clientes/bloqueados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].statusBloqueio").value("BLOQUEADO"))
                .andExpect(jsonPath("$[0].limiteCredito").value(0.0));
    }
}
