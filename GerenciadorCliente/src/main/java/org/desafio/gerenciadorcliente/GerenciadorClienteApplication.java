package org.desafio.gerenciadorcliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GerenciadorClienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorClienteApplication.class, args);
    }

}
