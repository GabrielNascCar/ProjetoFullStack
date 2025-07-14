package org.desafio.gerenciadorcliente.Services;

import org.desafio.gerenciadorcliente.DTO.FaturaDTO;
import org.desafio.gerenciadorcliente.model.Cliente;
import org.desafio.gerenciadorcliente.model.Fatura;
import org.desafio.gerenciadorcliente.repositories.ClienteRepository;
import org.desafio.gerenciadorcliente.repositories.FaturaRepository;
import org.desafio.gerenciadorcliente.services.FaturaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaturaServiceTest {

    @Mock
    private FaturaRepository faturaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private FaturaService faturaService;

    private final LocalDate hoje = LocalDate.now();
    private final Cliente cliente = new Cliente(1, "Cliente Teste", "12345678901", LocalDate.now(), "ATIVO", 1000.0);

    @Test
    void salvarFatura_DeveRetornarFaturaDTO() {

        Integer clienteId = 1;
        Long faturaId = 1L;

        FaturaDTO faturaDTO = new FaturaDTO(
                null,
                clienteId,
                "Cliente Teste",
                LocalDate.now().plusDays(10),
                null,
                100.0,
                null
        );

        Cliente cliente = new Cliente(
                clienteId,
                "Cliente Teste",
                "12345678901",
                LocalDate.now(),
                "ATIVO",
                1000.0
        );

        Fatura faturaSalva = new Fatura(
                faturaId,
                cliente,
                faturaDTO.getDataVencimento(),
                null,
                faturaDTO.getValor(),
                "ABERTA"
        );

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        when(faturaRepository.save(any(Fatura.class))).thenAnswer(invocation -> {
            Fatura fatura = invocation.getArgument(0);
            fatura.setId(faturaId);
            return fatura;
        });

        FaturaDTO resultado = faturaService.salvarFatura(faturaDTO);

        assertNotNull(resultado);
        assertEquals(faturaId, resultado.getId());
        assertEquals(clienteId, resultado.getClienteId());
        assertEquals("ABERTA", resultado.getStatus());
        verify(faturaRepository, times(1)).save(any(Fatura.class));
    }

    @Test
    void buscarPorId_QuandoExistir_DeveRetornarFaturaDTO() {

        Fatura fatura = new Fatura(1L, cliente, hoje, null, 100.0, "ABERTA");
        when(faturaRepository.findById(1)).thenReturn(Optional.of(fatura));

        FaturaDTO resultado = faturaService.buscarPorId(1);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void registrarPagamento_DeveAtualizarStatus() {

        Fatura fatura = new Fatura(1L, cliente, hoje, null, 100.0, "ABERTA");
        when(faturaRepository.findById(1)).thenReturn(Optional.of(fatura));
        when(faturaRepository.save(any(Fatura.class))).thenReturn(fatura);

        FaturaDTO resultado = faturaService.registrarPagamento(1);

        assertEquals("PAGO", resultado.getStatus());
        assertEquals(hoje, resultado.getDataPagamento());
    }

    @Test
    void listarPorCliente_DeveRetornarLista() {

        Fatura fatura = new Fatura(1L, cliente, hoje, null, 100.0, "ABERTA");
        when(faturaRepository.findByClienteId(1L)).thenReturn(List.of(fatura));

        List<FaturaDTO> resultado = faturaService.listarPorCliente(1L);

        assertFalse(resultado.isEmpty());
        assertEquals(1L, resultado.get(0).getId());
    }
}