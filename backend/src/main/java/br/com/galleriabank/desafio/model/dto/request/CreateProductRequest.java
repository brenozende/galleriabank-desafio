package br.com.galleriabank.desafio.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank
        String description,

        @DecimalMin("0.01")
        @NotNull
        BigDecimal value
) {}
