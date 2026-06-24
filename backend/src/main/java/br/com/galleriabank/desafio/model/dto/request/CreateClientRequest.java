package br.com.galleriabank.desafio.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CreateClientRequest (
        @NotBlank
        @Size(min = 3)
        String name,

        @CPF
        String cpf,

        String phoneNumber
){}
