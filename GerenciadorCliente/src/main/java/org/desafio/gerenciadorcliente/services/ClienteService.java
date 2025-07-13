package org.desafio.gerenciadorcliente.services;

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

    @Transactional
    public void salvarCliente(Cliente cliente) {
        cliente.setStatusBloqueio("ATIVO");
        clienteRepository.save(cliente);
    }

    public Cliente buscarClientePorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return cliente;
    }

    @Transactional
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

}
