package org.desafio.gerenciadorcliente.exception;

public class FaturaNaoEncontradaException extends RuntimeException {
    public FaturaNaoEncontradaException(Integer id) {
        super("Fatura com ID " + id + " não encontrada.");
    }
}
