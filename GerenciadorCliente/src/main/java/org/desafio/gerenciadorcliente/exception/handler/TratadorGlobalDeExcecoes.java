package org.desafio.gerenciadorcliente.exception.handler;

import org.desafio.gerenciadorcliente.exception.ClienteNaoEncontradoException;
import org.desafio.gerenciadorcliente.exception.FaturaNaoEncontradaException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorGlobalDeExcecoes {

    private Map<String, Object> montarResposta(String erro, String mensagem, HttpStatus status) {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("timestamp", LocalDateTime.now());
        corpo.put("status", status.value());
        corpo.put("erro", erro);
        corpo.put("mensagem", mensagem);
        return corpo;
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<Object> tratarClienteNaoEncontrado(ClienteNaoEncontradoException ex) {
        return new ResponseEntity<>(
                montarResposta("Cliente não encontrado", ex.getMessage(), HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(FaturaNaoEncontradaException.class)
    public ResponseEntity<Object> tratarFaturaNaoEncontrada(FaturaNaoEncontradaException ex) {
        return new ResponseEntity<>(
                montarResposta("Fatura não encontrada", ex.getMessage(), HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> tratarRuntimeGenerica(RuntimeException ex) {
        return new ResponseEntity<>(
                montarResposta("Erro interno", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
