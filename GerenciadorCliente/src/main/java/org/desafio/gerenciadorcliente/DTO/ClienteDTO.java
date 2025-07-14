package org.desafio.gerenciadorcliente.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String statusBloqueio;
    private double limiteCredito;

}
