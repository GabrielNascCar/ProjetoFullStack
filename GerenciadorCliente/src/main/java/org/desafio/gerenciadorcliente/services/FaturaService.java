package org.desafio.gerenciadorcliente.services;

import org.desafio.gerenciadorcliente.model.Cliente;
import org.desafio.gerenciadorcliente.model.Fatura;
import org.desafio.gerenciadorcliente.repositories.ClienteRepository;
import org.desafio.gerenciadorcliente.repositories.FaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FaturaService {


    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Fatura salvarFatura(Fatura fatura) {
        atualizarStatusFatura(fatura);
        faturaRepository.save(fatura);
        return fatura;
    }

    private void atualizarStatusFatura(Fatura fatura) {
        if ("PAGO".equals(fatura.getStatus())) {
            return;
        }

        if (LocalDate.now().isAfter(fatura.getDataVencimento())) {
            long diasAtraso = ChronoUnit.DAYS.between(fatura.getDataVencimento(), LocalDate.now());

            if (diasAtraso > 0) {
                fatura.setStatus("ATRASADA");

                if (diasAtraso > 3) {
                    bloquearCliente(fatura.getCliente());
                }
            }
        } else {
            fatura.setStatus("ABERTA");
        }
    }

    public void bloquearCliente(Cliente cliente) {
        Cliente cliente1 = clienteRepository.findById(cliente.getId()).orElseThrow();
        cliente1.setStatusBloqueio("BLOQUEADO");
        cliente1.setLimiteCredito(0.0);
        clienteRepository.save(cliente1);
    }

    public List<Fatura> listarPorCliente(Long clienteId) {
        List<Fatura> faturas = faturaRepository.findByClienteId(clienteId);
        faturas.forEach(this::atualizarStatusFatura);
        return faturas;
    }


}
