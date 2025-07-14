package org.desafio.gerenciadorcliente.controllers;

import org.desafio.gerenciadorcliente.model.Fatura;
import org.desafio.gerenciadorcliente.services.FaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
