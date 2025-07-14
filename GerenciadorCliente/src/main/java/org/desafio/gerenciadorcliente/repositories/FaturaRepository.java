package org.desafio.gerenciadorcliente.repositories;

import org.desafio.gerenciadorcliente.model.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Integer> {
}
