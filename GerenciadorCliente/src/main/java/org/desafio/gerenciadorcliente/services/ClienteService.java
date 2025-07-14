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

    @Transactional
    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = toEntity(clienteDTO);
        cliente.setStatusBloqueio("ATIVO");
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toDTO(clienteSalvo);
    }

    public Cliente buscarClientePorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        return cliente;
    }

    @Transactional
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente atualizarCliente(Integer id, Cliente clienteAtualizado) {

        Cliente cliente = buscarClientePorId(id);
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setDataNascimento(clienteAtualizado.getDataNascimento());
        cliente.setStatusBloqueio(clienteAtualizado.getStatusBloqueio());
        cliente.setLimiteCredito(clienteAtualizado.getLimiteCredito());
        return clienteRepository.save(cliente);

    }

    public List<Cliente> listarBloqueados() {
        return clienteRepository.findByStatusBloqueio("BLOQUEADO");
    }

}
