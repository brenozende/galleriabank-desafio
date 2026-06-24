package br.com.galleriabank.desafio.service;

import br.com.galleriabank.desafio.exception.ResourceNotFoundException;
import br.com.galleriabank.desafio.model.dto.request.CreateOrderRequest;
import br.com.galleriabank.desafio.model.entity.Client;
import br.com.galleriabank.desafio.model.entity.Order;
import br.com.galleriabank.desafio.model.entity.OrderItem;
import br.com.galleriabank.desafio.model.entity.Product;
import br.com.galleriabank.desafio.repository.ClientRepository;
import br.com.galleriabank.desafio.repository.OrderRepository;
import br.com.galleriabank.desafio.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public Order createOrder(CreateOrderRequest request) {
        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));

        Order order = new Order();
        order.setDescription(request.description());

        List<OrderItem> items = new ArrayList<>();

        for (Long productId : request.products()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            items.add(orderItem);
        }
        
        order.setItems(items);
        order.setClient(client);
        order.setEmissionDate(LocalDateTime.now());
        
        orderRepository.save(order);
        return order;
    }

    public Order findOrderById(Long id) {
        return orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }
}
