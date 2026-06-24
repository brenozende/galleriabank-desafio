package br.com.galleriabank.desafio.controller;

import br.com.galleriabank.desafio.model.dto.request.CreateClientRequest;
import br.com.galleriabank.desafio.model.entity.Client;
import br.com.galleriabank.desafio.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@CrossOrigin
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody CreateClientRequest request) {
        Client client = clientService.createClient(request);
        return ResponseEntity
                .created(URI.create("/clientes/" + client.getId()))
                .body(client);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findClient(@PathVariable Long id) {
        Client client = clientService.findClient(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<?> listClients() {
        List<Client> clients = clientService.listClients();
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable Long id, @Valid @RequestBody CreateClientRequest request) {
        Client client = clientService.updateClient(id, request);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
