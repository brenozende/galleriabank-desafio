package br.com.galleriabank.desafio.service;

import br.com.galleriabank.desafio.exception.BusinessException;
import br.com.galleriabank.desafio.exception.ResourceNotFoundException;
import br.com.galleriabank.desafio.model.dto.request.CreateClientRequest;
import br.com.galleriabank.desafio.model.entity.Client;
import br.com.galleriabank.desafio.repository.ClientRepository;
import br.com.galleriabank.desafio.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public Client createClient(CreateClientRequest request) {
        if (clientRepository.existsByCpf(request.cpf()))
            throw new BusinessException("CPF already exists");

        Client client = new Client();
        client.setCpf(request.cpf());
        client.setName(request.name());
        client.setPhoneNumber(request.phoneNumber());

        return clientRepository.save(client);
    }

    public Client findClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    public Client updateClient(Long id, @Valid CreateClientRequest request) {
        Client client = findClient(id);
        client.setPhoneNumber(request.phoneNumber());
        client.setCpf(request.cpf());
        client.setName(request.name());
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        Client client = findClient(id);

        if (orderRepository.existsByClientId(id)) {
            throw new BusinessException("Client has orders and cannot be deleted.");
        }

        clientRepository.delete(client);
    }

    public List<Client> listClients() {
        return clientRepository.findAll();
    }
}
