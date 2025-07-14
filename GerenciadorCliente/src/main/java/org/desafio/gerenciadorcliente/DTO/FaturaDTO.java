package org.desafio.gerenciadorcliente.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaturaDTO {

    private Long id;
    private Integer clienteId;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private Double valor;
    private String status;

}
