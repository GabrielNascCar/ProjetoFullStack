package org.desafio.gerenciadorcliente.config;

import org.desafio.gerenciadorcliente.model.Cliente;
import org.desafio.gerenciadorcliente.model.Fatura;
import org.desafio.gerenciadorcliente.repositories.ClienteRepository;
import org.desafio.gerenciadorcliente.repositories.FaturaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final FaturaRepository faturaRepository;

    public DatabaseInitializer(ClienteRepository clienteRepository, FaturaRepository faturaRepository) {
        this.clienteRepository = clienteRepository;
        this.faturaRepository = faturaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        faturaRepository.deleteAllInBatch();
        clienteRepository.deleteAllInBatch();

        Cliente c1 = criarCliente("João Silva", "12345678901", LocalDate.of(1990, 5, 15), "ATIVO", 5000.00);
        Cliente c2 = criarCliente("Maria Oliveira", "98765432109", LocalDate.of(1985, 8, 20), "ATIVO", 8000.00);
        Cliente c3 = criarCliente("Carlos Souza", "45612378905", LocalDate.of(1978, 11, 30), "BLOQUEADO", 0.00);
        Cliente c4 = criarCliente("Ana Santos", "78945612307", LocalDate.of(1995, 3, 25), "ATIVO", 3000.00);
        Cliente c5 = criarCliente("Pedro Costa", "32165498702", LocalDate.of(1982, 7, 10), "BLOQUEADO", 0.00);
        Cliente c6 = criarCliente("Juliana Pereira", "65498732106", LocalDate.of(1992, 9, 18), "ATIVO", 6000.00);
        Cliente c7 = criarCliente("Lucas Martins", "14725836908", LocalDate.of(1988, 12, 5), "ATIVO", 4000.00);
        Cliente c8 = criarCliente("Fernanda Lima", "25836914704", LocalDate.of(1975, 4, 22), "BLOQUEADO", 0.00);
        Cliente c9 = criarCliente("Ricardo Alves", "36925814703", LocalDate.of(1998, 1, 15), "ATIVO", 7000.00);
        Cliente c10 = criarCliente("Amanda Rocha", "98732165409", LocalDate.of(1980, 6, 28), "ATIVO", 5500.00);

        List<Cliente> clientes = clienteRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10));

        criarFatura(c1, LocalDate.of(2025, 7, 20), LocalDate.of(2025, 7, 5), 1500.00, "PAGA");
        criarFatura(c1, LocalDate.of(2025, 7, 25), null, 2000.00, "ATRASADA");
        criarFatura(c2, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 9), 3000.00, "PAGA");
        criarFatura(c3, LocalDate.of(2024, 9, 1), null, 1200.00, "ATRASADA");
        criarFatura(c4, LocalDate.of(2025, 10, 15), null, 800.00, "ABERTA");
        criarFatura(c5, LocalDate.of(2024, 8, 20), null, 2500.00, "ATRASADA");
        criarFatura(c6, LocalDate.of(2025, 10, 25), null, 1800.00, "ABERTA");
        criarFatura(c7, LocalDate.of(2025, 10, 8), LocalDate.of(2025, 10, 8), 2200.00, "PAGA");
        criarFatura(c8, LocalDate.of(2024, 9, 15), null, 3500.00, "ATRASADA");
        criarFatura(c9, LocalDate.of(2025, 10, 30), null, 2800.00, "ABERTA");
        criarFatura(c10, LocalDate.of(2025, 10, 12), LocalDate.of(2025, 10, 12), 1600.00, "PAGA");
    }

    private Cliente criarCliente(String nome, String cpf, LocalDate dataNascimento, String status, double limite) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setDataNascimento(dataNascimento);
        cliente.setStatusBloqueio(status);
        cliente.setLimiteCredito(limite);
        return cliente;
    }

    private void criarFatura(Cliente cliente, LocalDate vencimento, LocalDate pagamento, double valor, String status) {
        Fatura fatura = new Fatura();
        fatura.setCliente(cliente);
        fatura.setDataVencimento(vencimento);
        fatura.setDataPagamento(pagamento);
        fatura.setValor(valor);
        fatura.setStatus(status);
        faturaRepository.save(fatura);
    }
}
