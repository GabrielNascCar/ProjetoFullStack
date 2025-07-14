package org.desafio.gerenciadorcliente.services;

import org.desafio.gerenciadorcliente.DTO.ClienteDTO;
import org.desafio.gerenciadorcliente.exception.ClienteNaoEncontradoException;
import org.desafio.gerenciadorcliente.model.Cliente;
import org.desafio.gerenciadorcliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setStatusBloqueio(dto.getStatusBloqueio());
        cliente.setLimiteCredito(dto.getLimiteCredito());
        return cliente;
    }

    private ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                cliente.getStatusBloqueio(),
                cliente.getLimiteCredito()
        );
    }

    public ClienteDTO atualizarCliente(Integer id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setCpf(clienteDTO.getCpf());
        clienteExistente.setDataNascimento(clienteDTO.getDataNascimento());
        clienteExistente.setStatusBloqueio(clienteDTO.getStatusBloqueio());
        clienteExistente.setLimiteCredito(clienteDTO.getLimiteCredito());

        Cliente clienteAtualizado = clienteRepository.save(clienteExistente);
        return toDTO(clienteAtualizado);
    }

    @Transactional
    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = toEntity(clienteDTO);
        cliente.setStatusBloqueio("ATIVO");
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toDTO(clienteSalvo);
    }

    public ClienteDTO buscarClientePorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        return toDTO(cliente);
    }

    @Transactional
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public List<Cliente> listarBloqueados() {
        return clienteRepository.findByStatusBloqueio("BLOQUEADO");
    }

}
