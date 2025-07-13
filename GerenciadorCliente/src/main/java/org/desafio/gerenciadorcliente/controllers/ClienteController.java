package org.desafio.gerenciadorcliente.controllers;

import org.desafio.gerenciadorcliente.model.Cliente;
import org.desafio.gerenciadorcliente.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Cliente obj){
        clienteService.salvarCliente(obj);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
