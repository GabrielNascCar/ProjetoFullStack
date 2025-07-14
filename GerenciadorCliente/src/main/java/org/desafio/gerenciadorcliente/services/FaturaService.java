package org.desafio.gerenciadorcliente.services;

import org.desafio.gerenciadorcliente.DTO.FaturaDTO;
import org.desafio.gerenciadorcliente.exception.ClienteNaoEncontradoException;
import org.desafio.gerenciadorcliente.exception.FaturaNaoEncontradaException;
import org.desafio.gerenciadorcliente.model.Cliente;
import org.desafio.gerenciadorcliente.model.Fatura;
import org.desafio.gerenciadorcliente.repositories.ClienteRepository;
import org.desafio.gerenciadorcliente.repositories.FaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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

    private FaturaDTO toDTO(Fatura fatura) {
        return new FaturaDTO(
                fatura.getId(),
                fatura.getCliente().getId(),
                fatura.getDataVencimento(),
                fatura.getDataPagamento(),
                fatura.getValor(),
                fatura.getStatus()
        );
    }

    private Fatura toEntity(FaturaDTO faturaDTO) {
        Fatura fatura = new Fatura();
        fatura.setId(faturaDTO.getId());
        fatura.setCliente(clienteRepository.findById(faturaDTO.getClienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException(faturaDTO.getClienteId())));
        fatura.setDataVencimento(faturaDTO.getDataVencimento());
        fatura.setDataPagamento(faturaDTO.getDataPagamento());
        fatura.setValor(faturaDTO.getValor());
        fatura.setStatus(faturaDTO.getStatus());
        return fatura;
    }

    @Transactional
    public FaturaDTO salvarFatura(FaturaDTO faturaDTO) {
        Fatura fatura = toEntity(faturaDTO);
        atualizarStatusFatura(fatura);
        faturaRepository.save(fatura);
        return toDTO(fatura);
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
        Cliente cliente1 = clienteRepository.findById(cliente.getId()).orElseThrow(() -> new ClienteNaoEncontradoException(cliente.getId()));
        cliente1.setStatusBloqueio("BLOQUEADO");
        cliente1.setLimiteCredito(0.0);
        clienteRepository.save(cliente1);
    }

    public List<FaturaDTO> listarPorCliente(Long clienteId) {
        return faturaRepository.findByClienteId(clienteId).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<Fatura> listarAtrasadas() {
        List<Fatura> faturas = faturaRepository.findByStatusNot("PAGO");
        faturas.forEach(this::atualizarStatusFatura);
        return faturas.stream()
                .filter(f -> "ATRASADA".equals(f.getStatus()))
                .toList();
    }

    public Fatura buscarPorId(Integer id) {
        Fatura fatura = faturaRepository.findById(id).orElseThrow(() -> new FaturaNaoEncontradaException(id));
        atualizarStatusFatura(fatura);
        return fatura;
    }

    @Transactional
    public Fatura registrarPagamento(Integer id) {
        Fatura fatura = buscarPorId(id);
        fatura.setStatus("PAGO");
        fatura.setDataPagamento(LocalDate.now());
        faturaRepository.save(fatura);
        return fatura;
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void verificarFaturasAtrasadas() {
        List<Fatura> faturas = faturaRepository.findByStatusNot("PAGO");
        faturas.forEach(fatura -> {
            atualizarStatusFatura(fatura);
            faturaRepository.save(fatura);
        });

        System.out.println("Verificação concluída. Faturas processadas: " + faturas.size());

    }

}
