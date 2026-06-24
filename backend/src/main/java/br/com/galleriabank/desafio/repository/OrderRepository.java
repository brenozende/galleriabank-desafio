package br.com.galleriabank.desafio.repository;

import br.com.galleriabank.desafio.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByClientId(Long id);

    @Query("""
        SELECT o
        FROM Order o
        LEFT JOIN FETCH o.items i
        LEFT JOIN FETCH i.product
        WHERE o.id = :id
    """)
    Optional<Order> findByIdWithItems(Long id);
}
