package org.desafio.gerenciadorcliente.services;

import org.desafio.gerenciadorcliente.model.Fatura;
import org.desafio.gerenciadorcliente.repositories.FaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaturaService {


    @Autowired
    private FaturaRepository faturaRepository;

    @Transactional
    public Fatura salvarFatura(Fatura fatura) {
        fatura.setStatus("ABERTA");
        faturaRepository.save(fatura);
        return fatura;
    }


}
