package br.com.galleriabank.desafio.controller;

import br.com.galleriabank.desafio.model.dto.request.CreateOrderRequest;
import br.com.galleriabank.desafio.model.dto.response.OrderResponse;
import br.com.galleriabank.desafio.model.entity.Order;
import br.com.galleriabank.desafio.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@CrossOrigin
public class OrdersController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.created(URI.create("/orders/" + order.getId())).body(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrder(@PathVariable Long id) {
        Order order = orderService.findOrderById(id);
        return ResponseEntity.ok(OrderResponse.from(order));
    }
}
