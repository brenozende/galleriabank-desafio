package br.com.galleriabank.desafio.model.dto.response;

import br.com.galleriabank.desafio.model.entity.Order;
import br.com.galleriabank.desafio.model.entity.OrderItem;
import br.com.galleriabank.desafio.model.entity.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record OrderResponse(
        Long id,
        LocalDateTime emissionDate,
        String description,
        Long clientId,
        List<OrderItemResponse> items,
        Double total
) {
    public static OrderResponse from(Order order) {
        Double total = order.getItems().stream()
                .map(OrderItem::getProduct)
                .mapToDouble(Product::getValue)
                .sum();

        List<OrderItemResponse> items = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderItemResponse itemResponse = new OrderItemResponse(item.getProduct());
            items.add(itemResponse);
        }

        return new OrderResponse(
                order.getId(),
                order.getEmissionDate(),
                order.getDescription(),
                order.getClient().getId(),
                items,
                total
        );
    }
}
