package br.com.galleriabank.desafio.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderRequest (
        @NotNull(message = "Pedido deve ser vinculado a um cliente.") Long clientId,
        @NotNull @Size(min = 1, message = "Deve haver ao menos um produto no pedido.") List<Long> products,
        String description
) {}
