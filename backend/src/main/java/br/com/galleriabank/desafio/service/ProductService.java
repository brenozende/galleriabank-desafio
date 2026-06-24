package br.com.galleriabank.desafio.service;

import br.com.galleriabank.desafio.exception.BusinessException;
import br.com.galleriabank.desafio.exception.ResourceNotFoundException;
import br.com.galleriabank.desafio.model.dto.request.CreateProductRequest;
import br.com.galleriabank.desafio.model.entity.Product;
import br.com.galleriabank.desafio.repository.OrderItemRepository;
import br.com.galleriabank.desafio.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public Product createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setDescription(request.description());
        product.setValue(request.value().doubleValue());

        return productRepository.save(product);
    }

    public Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long id, CreateProductRequest request) {
        Product product = findProduct(id);
        product.setValue(request.value().doubleValue());
        product.setDescription(request.description());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (orderItemRepository.existsByProductId(id))
            throw new BusinessException("Produto possui pedidos vinculados e não pode ser removido.");

        Product product = findProduct(id);
        productRepository.delete(product);
    }
}
