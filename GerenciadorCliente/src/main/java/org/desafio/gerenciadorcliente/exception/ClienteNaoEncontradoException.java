package org.desafio.gerenciadorcliente.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(Integer id) {
        super("Cliente com ID " + id + " não encontrado.");
    }
}
