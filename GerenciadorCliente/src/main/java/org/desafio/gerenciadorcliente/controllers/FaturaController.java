package org.desafio.gerenciadorcliente.controllers;

import org.desafio.gerenciadorcliente.DTO.FaturaDTO;
import org.desafio.gerenciadorcliente.model.Fatura;
import org.desafio.gerenciadorcliente.services.FaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faturas")
public class FaturaController {

    @Autowired
    private FaturaService faturaService;

    @PostMapping
    public ResponseEntity<FaturaDTO> criar(@RequestBody FaturaDTO faturaDTO) {
        FaturaDTO faturaSalva = faturaService.salvarFatura(faturaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(faturaSalva);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<FaturaDTO>> listarPorCliente(@PathVariable Long clienteId) {
        List<FaturaDTO> faturas = faturaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(faturas);
    }

    @GetMapping("/atrasadas")
    public ResponseEntity<List<FaturaDTO>> listarAtrasadas() {
        List<FaturaDTO> faturasAtrasadas = faturaService.listarAtrasadas();
        return ResponseEntity.ok(faturasAtrasadas);
    }

    @GetMapping("/fatura/{id}")
    public ResponseEntity<FaturaDTO> buscarPorId(@PathVariable Integer id) {
        FaturaDTO fatura = faturaService.buscarPorId(id);
        return ResponseEntity.ok(fatura);
    }

    @PutMapping("/{id}/pagamento")
    public ResponseEntity<FaturaDTO> registrarPagamento(@PathVariable Integer id) {
        FaturaDTO fatura = faturaService.registrarPagamento(id);
        return ResponseEntity.ok(fatura);
    }

}
