package org.desafio.gerenciadorcliente.repositories;

import org.desafio.gerenciadorcliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByStatusBloqueio(String b);
}
