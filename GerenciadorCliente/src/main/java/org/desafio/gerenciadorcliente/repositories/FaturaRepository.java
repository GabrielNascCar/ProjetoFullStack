package org.desafio.gerenciadorcliente.repositories;

import org.desafio.gerenciadorcliente.model.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Integer> {
    List<Fatura> findByClienteId(Long clienteId);
    List<Fatura> findByStatusNot(String status);
}
