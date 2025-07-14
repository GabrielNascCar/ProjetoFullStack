package org.desafio.gerenciadorcliente.Services;

import org.desafio.gerenciadorcliente.DTO.ClienteDTO;
import org.desafio.gerenciadorcliente.exception.ClienteNaoEncontradoException;
import org.desafio.gerenciadorcliente.model.Cliente;
import org.desafio.gerenciadorcliente.repositories.ClienteRepository;
import org.desafio.gerenciadorcliente.services.ClienteService;
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
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private final ClienteDTO clienteDTO = new ClienteDTO(
            1, "João Silva", "12345678901",
            LocalDate.of(1990, 1, 1), "ATIVO", 1000.0
    );

    @Test
    void criarCliente_DeveRetornarClienteDTOComStatusAtivo() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setStatusBloqueio("ATIVO");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO resultado = clienteService.criarCliente(clienteDTO);

        assertEquals("ATIVO", resultado.getStatusBloqueio());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void buscarClientePorId_QuandoExistir_DeveRetornarClienteDTO() {
        Cliente cliente = new Cliente();
        cliente.setId(1);

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        ClienteDTO resultado = clienteService.buscarClientePorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarClientePorId_QuandoNaoExistir_DeveLancarExcecao() {
        when(clienteRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.buscarClientePorId(999);
        });
    }

    @Test
    void atualizarCliente_DeveAtualizarDadosCorretamente() {
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1);

        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteExistente);

        ClienteDTO resultado = clienteService.atualizarCliente(1, clienteDTO);

        assertEquals("João Silva", resultado.getNome());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void listarClientes_DeveRetornarListaDeClientesDTO() {
        Cliente cliente = new Cliente();
        cliente.setId(1);

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteDTO> resultado = clienteService.listarClientes();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.get(0).getId());
    }

    @Test
    void listarBloqueados_DeveRetornarApenasClientesBloqueados() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setStatusBloqueio("BLOQUEADO");

        when(clienteRepository.findByStatusBloqueio("BLOQUEADO")).thenReturn(List.of(cliente));

        List<ClienteDTO> resultado = clienteService.listarBloqueados();

        assertFalse(resultado.isEmpty());
        assertEquals("BLOQUEADO", resultado.get(0).getStatusBloqueio());
    }
}
