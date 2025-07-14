package org.desafio.gerenciadorcliente.controllers;

import org.desafio.gerenciadorcliente.DTO.FaturaDTO;
import org.desafio.gerenciadorcliente.services.FaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FaturaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FaturaService faturaService;

    @InjectMocks
    private FaturaController faturaController;

    private final FaturaDTO faturaDTO = new FaturaDTO(
            1L, 1, "Cliente Teste",
            LocalDate.now().plusDays(10), null, 100.0, "ABERTA"
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(faturaController).build();
    }

    @Test
    void criarFatura_DeveRetornar201() throws Exception {
        when(faturaService.salvarFatura(any(FaturaDTO.class))).thenReturn(faturaDTO);

        mockMvc.perform(post("/faturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "clienteId": 1,
                        "dataVencimento": "2023-12-31",
                        "valor": 100.0
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void buscarPorId_DeveRetornarFatura() throws Exception {
        when(faturaService.buscarPorId(1)).thenReturn(faturaDTO);

        mockMvc.perform(get("/faturas/fatura/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void registrarPagamento_DeveRetornarFaturaAtualizada() throws Exception {
        FaturaDTO paga = new FaturaDTO(1L, 1, "Cliente Teste",
                LocalDate.now(), LocalDate.now(), 100.0, "PAGO");

        when(faturaService.registrarPagamento(1)).thenReturn(paga);

        mockMvc.perform(put("/faturas/1/pagamento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAGO"));
    }
}
