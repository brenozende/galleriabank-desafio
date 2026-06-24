package br.com.galleriabank.desafio.model.dto.response;

import br.com.galleriabank.desafio.model.entity.Product;

public record OrderItemResponse (
        Product product
) {

}
