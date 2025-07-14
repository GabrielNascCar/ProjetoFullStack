package org.desafio.gerenciadorcliente.controllers;

import org.desafio.gerenciadorcliente.model.Fatura;
import org.desafio.gerenciadorcliente.services.FaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faturas")
public class FaturaController {

    @Autowired
    private FaturaService faturaService;

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody Fatura fatura) {
        faturaService.salvarFatura(fatura);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{clienteId}")
    public List<Fatura> listarPorCliente(@PathVariable Long clienteId) {
        return faturaService.listarPorCliente(clienteId);
    }

    @GetMapping("/atrasadas")
    public List<Fatura> listarAtrasadas() {
        return faturaService.listarAtrasadas();
    }

}
