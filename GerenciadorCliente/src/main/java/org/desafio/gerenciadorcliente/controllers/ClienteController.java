package org.desafio.gerenciadorcliente.controllers;

import org.desafio.gerenciadorcliente.DTO.ClienteDTO;
import org.desafio.gerenciadorcliente.model.Cliente;
import org.desafio.gerenciadorcliente.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO novoCliente = clienteService.criarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Integer id) {
        ClienteDTO cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(
            @PathVariable Integer id,
            @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteAtualizado = clienteService.atualizarCliente(id, clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @GetMapping("/bloqueados")
    public ResponseEntity<List<Cliente>> listarBloqueados() {
        List<Cliente> clientesBloqueados = clienteService.listarBloqueados();
        return ResponseEntity.ok(clientesBloqueados);
    }

}
